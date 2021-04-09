package net.milkbowl.vault;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides static access to the {@link VaultAPI}.
 *
 * <p>Ideally, the ServiceManager for the platform should be used to obtain an
 * instance, however, this provider can be used if this is not viable.</p>
 *
 * <p>Largely taken from LuckPerms @ https://github.com/lucko/LuckPerms/blob/c533446ab0fec46a1a6299bf11d8642ade26a9dd/api/src/main/java/net/luckperms/api/LuckPermsProvider.java</p>
 */
public final class VaultProvider {
    @Nullable
    private static VaultAPI instance = null;

    /**
     * Gets an instance of the {@link VaultAPI},
     * throwing {@link IllegalStateException} if the API is not loaded yet.
     *
     * <p>This method will never return null.</p>
     *
     * @return an instance of the LuckPerms API
     * @throws IllegalStateException if the API is not loaded yet
     */
    @NotNull
    public static VaultAPI get() {
        VaultAPI i = instance; // local variable, for thread-safe returns
        if (i == null) {
            throw new NotLoadedException();
        }
        return i;
    }

    @ApiStatus.Internal
    static void register(@NotNull VaultAPI instance) {
        VaultProvider.instance = instance;
    }

    @ApiStatus.Internal
    static void unregister() {
        VaultProvider.instance = null;
    }

    private VaultProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = "The Vault API isn't loaded yet!\n" +
                "This could be because:\n" +
                "  a) the Vault plugin is not installed or it failed to enable\n" +
                "  b) your plugin does not declare a dependency on Vault\n" +
                "  c) you are attempting to use the API before plugins reach the 'enable' phase\n" +
                "     (call the #get method in onEnable, not in your plugin constructor!)\n";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}
