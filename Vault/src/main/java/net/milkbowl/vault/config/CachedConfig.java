package net.milkbowl.vault.config;

import org.jetbrains.annotations.NotNull;

public class CachedConfig {
    private CachedConfig() { }

    private boolean debug = false;

    public boolean getDebug() { return debug; }

    @NotNull
    public static CachedConfig.Builder builder() { return new CachedConfig.Builder(); }

    public static class Builder {
        @NotNull
        private final CachedConfig values = new CachedConfig();

        private Builder() { }

        @NotNull
        public CachedConfig.Builder debug(boolean value) {
            values.debug = value;
            return this;
        }

        @NotNull
        public CachedConfig build() { return values; }
    }
}
