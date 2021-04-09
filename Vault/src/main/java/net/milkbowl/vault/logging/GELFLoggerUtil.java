package net.milkbowl.vault.logging;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import flexjson.JSONSerializer;
import net.milkbowl.vault.compress.GZIPCompressionStream;
import net.milkbowl.vault.core.DoubleBuffer;
import net.milkbowl.vault.json.transformers.InstantTransformer;
import net.milkbowl.vault.logging.models.GELFSubmissionModel;
import net.milkbowl.vault.utils.TimeUtil;
import net.milkbowl.vault.web.WebRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GELFLoggerUtil {
    @NotNull
    private static final Logger logger = LoggerFactory.getLogger(GELFLoggerUtil.class);

    @NotNull
    private static final ScheduledExecutorService workPool = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("Vault_GELFLoggerUtil_%d").build());

    static {
        workPool.scheduleWithFixedDelay(GELFLoggerUtil::sendModels, 1L, 2L, TimeUnit.SECONDS);
    }

    private GELFLoggerUtil() { }

    @NotNull
    private static final GZIPCompressionStream GZIP_COMPRESSION = new GZIPCompressionStream();

    @NotNull
    private static final String GELF_ADDRESS = "https://logs.egg82.me:2087/gelf";

    @NotNull
    private static final String SESSION_ID = UUID.randomUUID().toString();

    @Nullable
    private static String serverId = null;

    @Nullable
    private static String pluginVersion = null;

    @Nullable
    private static String platform = null;

    @Nullable
    private static String platformVersion = null;

    private static volatile boolean initialized = false;
    private static volatile boolean sendErrors = false;

    @NotNull
    private static final DoubleBuffer<GELFSubmissionModel> modelQueue = new DoubleBuffer<>();

    public static void setData(@NotNull UUID serverId, @NotNull String pluginVersion, @NotNull String platformVersion) {
        GELFLoggerUtil.serverId = serverId.toString();
        GELFLoggerUtil.pluginVersion = pluginVersion;
        GELFLoggerUtil.platform = "Bukkit";
        GELFLoggerUtil.platformVersion = platformVersion;
    }

    public static void doSendErrors(boolean sendErrors) {
        GELFLoggerUtil.initialized = true;
        GELFLoggerUtil.sendErrors = sendErrors;
        if (!sendErrors) {
            modelQueue.getReadBuffer().clear();
            modelQueue.getWriteBuffer().clear();
        }
    }

    public static void close() {
        workPool.shutdown();
        try {
            if (!workPool.awaitTermination(4L, TimeUnit.SECONDS)) {
                workPool.shutdownNow();
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public static void send(int level, @Nullable String message) {
        sendModel(getModel(level, message));
    }

    public static void queue(int level, @Nullable String message) {
        if (!initialized || sendErrors) {
            modelQueue.getWriteBuffer().add(getModel(level, message));
        }
    }

    public static void send(int level, @Nullable String message, @NotNull Throwable ex) {
        sendModel(getModel(level, message, ex));
    }

    public static void queue(int level, @Nullable String message, @NotNull Throwable ex) {
        if (!initialized || sendErrors) {
            modelQueue.getWriteBuffer().add(getModel(level, message, ex));
        }
    }

    @NotNull
    private static GELFSubmissionModel getModel(int level, @Nullable String message) {
        GELFSubmissionModel retVal = new GELFSubmissionModel();
        retVal.setHost(serverId);
        retVal.setShortMessage(message != null ? message : "null");
        retVal.setLevel(level);
        retVal.setSession(SESSION_ID);
        retVal.setPluginVersion(pluginVersion);
        retVal.setPlatform(platform);
        retVal.setPlatformVersion(platformVersion);
        return retVal;
    }

    @NotNull
    private static GELFSubmissionModel getModel(int level, @Nullable String message, @NotNull Throwable ex) {
        GELFSubmissionModel retVal = new GELFSubmissionModel();
        retVal.setHost(serverId);
        retVal.setShortMessage(message != null ? message : "null");
        try (StringWriter builder = new StringWriter(); PrintWriter writer = new PrintWriter(builder)) {
            ex.printStackTrace(writer);
            String str = builder.toString();
            retVal.setFullMessage(str.substring(0, str.length() - System.lineSeparator().length()));
        } catch (IOException ex2) {
            logger.error(ex2.getClass().getName() + ": " + ex2.getMessage(), ex2);
        }
        retVal.setLevel(level);
        retVal.setSession(SESSION_ID);
        retVal.setPluginVersion(pluginVersion);
        retVal.setPlatform(platform);
        retVal.setPlatformVersion(platformVersion);
        return retVal;
    }

    private static void sendModels() {
        if (!initialized || !sendErrors) {
            return;
        }

        modelQueue.swapBuffers();

        GELFSubmissionModel model;
        while ((model = modelQueue.getReadBuffer().poll()) != null) {
            sendModel(model);
        }
    }

    private static void sendModel(@NotNull GELFSubmissionModel model) {
        JSONSerializer modelSerializer = new JSONSerializer();
        modelSerializer.prettyPrint(false);
        modelSerializer.transform(new InstantTransformer(), Instant.class);

        try {
            WebRequest request = WebRequest.builder(new URL(GELF_ADDRESS))
                    .method(WebRequest.RequestMethod.POST)
                    .timeout(new TimeUtil.Time(5000L, TimeUnit.MILLISECONDS))
                    .userAgent("egg82/GELFLogger")
                    .header("Content-Type", "application/json")
                    .header("Content-Encoding", "gzip")
                    .outputData(GZIP_COMPRESSION.compress(modelSerializer.exclude("*.class").deepSerialize(model).getBytes(StandardCharsets.UTF_8)))
                    .build();
            HttpURLConnection conn = request.getConnection();
            if (conn.getResponseCode() != 202) {
                throw new IOException("Did not get valid response from server (response code " + conn.getResponseCode() + "): \"" + WebRequest.getString(conn) + "\"");
            }
        } catch (IOException ex) {
            logger.error(ex.getClass().getName() + ": " + ex.getMessage(), ex);
        }
    }
}
