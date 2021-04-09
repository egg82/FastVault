package net.milkbowl.vault.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

public class ConfigUtil {
    @Nullable
    private static ConfigurationNode config = null;

    @Nullable
    private static CachedConfig cachedConfig = null;

    private ConfigUtil() { }

    public static void setConfiguration(@Nullable ConfigurationNode config, @Nullable CachedConfig cachedConfig) {
        ConfigUtil.config = config;
        ConfigUtil.cachedConfig = cachedConfig;
    }

    @NotNull
    public static ConfigurationNode getConfig() {
        ConfigurationNode c = config; // Thread-safe reference
        if (c == null) {
            throw new IllegalStateException("Config could not be fetched.");
        }
        return c;
    }

    @NotNull
    public static CachedConfig getCachedConfig() {
        CachedConfig c = cachedConfig; // Thread-safe reference
        if (c == null) {
            throw new IllegalStateException("Cached config could not be fetched.");
        }
        return c;
    }

    public static boolean getDebugOrFalse() {
        CachedConfig c = cachedConfig; // Thread-safe reference
        return c != null && c.getDebug();
    }
}
