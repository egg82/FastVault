package net.milkbowl.vault;

import org.jetbrains.annotations.NotNull;

/**
 * The Vault API.
 *
 * <p>The API allows other plugins on the server to read and modify Vault
 * data, change behaviour of the plugin, listen to certain events, and integrate
 * Vault into other plugins and systems.</p>
 *
 * <p>This interface represents the base of the API package. All modern functions are
 * accessed via this interface.</p>
 *
 * <p>To start using the API, you need to obtain an instance of this interface.
 * These are registered by the LuckPerms plugin to the platforms Services
 * Manager. This is the preferred method for obtaining an instance.</p>
 *
 * <p>For ease of use, and for platforms without a Service Manager, an instance
 * can also be obtained from the static singleton accessor in
 * {@link VaultProvider}.</p>
 *
 * <p>Largely taken from LuckPerms @ https://github.com/lucko/LuckPerms/blob/c533446ab0fec46a1a6299bf11d8642ade26a9dd/api/src/main/java/net/luckperms/api/LuckPerms.java</p>
 */
public interface VaultAPI {
    /**
     * Gets the {@link PluginMetadata}, responsible for providing metadata about
     * the Vault plugin currently running.
     *
     * @return the plugin metadata
     */
    @NotNull
    PluginMetadata getPluginMetadata();
}
