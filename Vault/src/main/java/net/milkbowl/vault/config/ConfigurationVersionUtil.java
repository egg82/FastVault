package net.milkbowl.vault.config;

import com.google.common.io.Files;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;

public class ConfigurationVersionUtil {
    private ConfigurationVersionUtil() { }

    public static void conformVersion(
            @NotNull ConfigurationLoader<CommentedConfigurationNode> loader,
            @NotNull CommentedConfigurationNode config,
            @NotNull File fileOnDisk
    ) throws IOException {
        double oldVersion = config.node("version").getDouble(1.0d);

        if (config.node("version").getDouble(1.0d) == 1.0d) {
            to20(config);
        }
        /*if (config.node("version").getDouble() == 2.0d) {
            to21(config);
        }*/

        if (config.node("version").getDouble() != oldVersion) {
            File backupFile = new File(fileOnDisk.getParent(), fileOnDisk.getName() + ".bak");
            if (backupFile.exists()) {
                java.nio.file.Files.delete(backupFile.toPath());
            }

            Files.copy(fileOnDisk, backupFile);
            loader.save(config);
        }
    }

    private static void to20(@NotNull CommentedConfigurationNode config) throws SerializationException {
        // Add debug
        config.node("debug").set(false);

        // Add stats
        config.node("stats", "usage").set(true);
        config.node("stats", "errors").set(true);

        // update-check -> update->check
        boolean updateCheck = config.node("update-check").getBoolean(true);
        config.node("update", "check").set(updateCheck);
        config.removeChild("update-check");

        // Add update->notify
        config.node("update", "notify").set(true);

        // Version
        config.node("version").set(2.0d);
    }

    /*private static void to21(@NotNull CommentedConfigurationNode config) throws SerializationException {
        // Add consensus
        config.node("consensus").set(-1.0d);

        // Version
        config.node("version").set(2.1d);
    }*/
}
