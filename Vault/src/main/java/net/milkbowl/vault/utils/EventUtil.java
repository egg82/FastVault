package net.milkbowl.vault.utils;

import net.kyori.event.EventBus;
import net.kyori.event.EventSubscriber;
import net.kyori.event.PostResult;
import net.milkbowl.vault.event.VaultEvent;
import net.milkbowl.vault.logging.GELFLogger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EventUtil {
    @NotNull
    private static final Logger logger = new GELFLogger(LoggerFactory.getLogger(EventUtil.class));

    private EventUtil() { }

    public static void post(@NotNull VaultEvent event, @NotNull EventBus<VaultEvent> eventBus) {
        PostResult result = eventBus.post(event);
        if (!result.wasSuccessful()) {
            for (Map.Entry<EventSubscriber<?>, Throwable> kvp : result.exceptions().entrySet()) {
                logger.error(
                        "[Vault API] " + kvp.getValue().getClass().getName() + ": " + kvp.getValue().getMessage(),
                        kvp.getValue()
                );
            }
            logger.warn("[Vault API] The above errors are from other plugins. PLEASE DO NOT REPORT THIS TO ANTI-VPN.");
        }
    }
}
