package net.milkbowl.vault.event.api;

import net.kyori.event.EventBus;
import net.milkbowl.vault.event.VaultEvent;

/**
 * Called when the API is about to be disabled, and
 * the current {@link EventBus} will stop sending events.
 *
 * <p>This should only be fired if the Vault plugin is externally disabled.
 * Vault plugin reloads from the plugin's command will not trigger this,
 * as the event bus will not be destroyed.</p>
 */
public interface APIDisableEvent extends VaultEvent { }
