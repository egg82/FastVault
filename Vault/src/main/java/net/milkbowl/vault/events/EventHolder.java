package net.milkbowl.vault.events;

import net.milkbowl.vault.logging.GELFLogger;
import ninja.egg82.events.BukkitEventSubscriber;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class EventHolder {
    @NotNull
    protected final Logger logger = new GELFLogger(LoggerFactory.getLogger(getClass()));

    @NotNull
    protected final List<@NotNull BukkitEventSubscriber<?>> events = new ArrayList<>();

    public final int numEvents() { return events.size(); }

    public final void cancel() {
        for (BukkitEventSubscriber<?> event : events) {
            event.cancel();
        }
    }
}
