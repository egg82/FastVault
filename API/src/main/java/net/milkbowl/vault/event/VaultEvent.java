package net.milkbowl.vault.event;

import net.milkbowl.vault.VaultAPI;
import org.jetbrains.annotations.NotNull;

/**
 * A superinterface for all Vault events.
 */
public interface VaultEvent {
    /**
     * Get the API instance this event was dispatched from
     *
     * @return the api instance
     */
    @NotNull
    VaultAPI getApi();

    /**
     * Gets the type of the event.
     *
     * @return the type of the event
     */
    @NotNull
    Class<? super VaultEvent> getEventType();
}
