package net.milkbowl.vault.config;

import net.milkbowl.vault.logging.GELFLogger;
import net.milkbowl.vault.logging.GELFLoggerUtil;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigurationFileUtil {
    @NotNull
    private static final Logger logger = new GELFLogger(LoggerFactory.getLogger(ConfigurationFileUtil.class));

    private ConfigurationFileUtil() { }

    public static boolean getAllowErrorStats(@NotNull File dataDirectory) {
        ConfigurationNode config;
        try {
            config = getConfigSimple("config.yml", new File(dataDirectory, "config.yml"), null);
        } catch (IOException ex) {
            logger.error(ex.getClass().getName() + ": " + ex.getMessage(), ex);
            return false;
        }

        return config.node("stats", "errors").getBoolean(true);
    }

    public static void reloadConfig(@NotNull File dataDirectory, @NotNull ConsoleCommandSender console) {
        ConfigurationNode config;
        try {
            config = getConfig("config.yml", new File(dataDirectory, "config.yml"), console);
        } catch (IOException ex) {
            logger.error(ex.getClass().getName() + ": " + ex.getMessage(), ex);
            return;
        }

        GELFLoggerUtil.doSendErrors(config.node("stats", "errors").getBoolean(true));

        boolean debug = config.node("debug").getBoolean(false);
        if (debug) {
            console.sendMessage("Debug enabled");
        }

        CachedConfig cachedConfig = CachedConfig.builder()
                .debug(debug)
                .build();

        ConfigUtil.setConfiguration(config, cachedConfig);
    }

    @NotNull
    private static CommentedConfigurationNode getConfigSimple(
            @NotNull String resourcePath,
            @NotNull File fileOnDisk,
            @Nullable ConsoleCommandSender console
    ) throws IOException {
        File parentDir = fileOnDisk.getParentFile();
        if (parentDir.exists() && !parentDir.isDirectory()) {
            Files.delete(parentDir.toPath());
        }
        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Could not create parent directory structure.");
            }
        }
        if (fileOnDisk.exists() && fileOnDisk.isDirectory()) {
            Files.delete(fileOnDisk.toPath());
        }

        if (!fileOnDisk.exists()) {
            try (InputStream inStream = ConfigurationFileUtil.class.getResourceAsStream("/" + resourcePath)) {
                if (inStream != null) {
                    try (FileOutputStream outStream = new FileOutputStream(fileOnDisk)) {
                        int read;
                        byte[] buffer = new byte[4096];
                        while ((read = inStream.read(buffer, 0, buffer.length)) > 0) {
                            outStream.write(buffer, 0, read);
                        }
                    }
                }
            }
        }

        ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).indent(2).file(fileOnDisk).build();
        return loader.load(ConfigurationOptions.defaults().header("Comments are gone because update :(\nClick here for new config + comments: "));
    }

    @NotNull
    private static CommentedConfigurationNode getConfig(
            @NotNull String resourcePath,
            @NotNull File fileOnDisk,
            @NotNull ConsoleCommandSender console
    ) throws IOException {
        ConfigurationLoader<CommentedConfigurationNode> loader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).indent(2).file(fileOnDisk).build();
        CommentedConfigurationNode config = getConfigSimple(resourcePath, fileOnDisk, console);
        ConfigurationVersionUtil.conformVersion(loader, config, fileOnDisk);
        return config;
    }
}
