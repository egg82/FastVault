package net.milkbowl.vault.event.api;

import net.milkbowl.vault.event.VaultEvent;
import net.milkbowl.vault.model.chat.ChatManager;
import net.milkbowl.vault.model.economy.EconomyManager;
import net.milkbowl.vault.model.permission.PermissionManager;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the API is about to be reloaded.
 */
public interface APIReloadEvent extends VaultEvent {
    /**
     * Gets the new {@link EconomyManager} instance
     *
     * @return the new economy manager instance
     */
    @NotNull
    EconomyManager getNewEconomyManager();

    /**
     * Gets the new {@link PermissionManager} instance
     *
     * @return the new permission manager instance
     */
    @NotNull
    PermissionManager getNewPermissionManager();

    /**
     * Gets the new {@link ChatManager} instance
     *
     * @return the new chat manager instance
     */
    @NotNull
    ChatManager getNewChatManager();
}
