package net.milkbowl.vault.chat.plugins;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.chat.ChatPlugin;
import net.milkbowl.vault.logging.GELFLogger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractChat extends Chat {
    @NotNull
    protected final Logger logger = new GELFLogger(LoggerFactory.getLogger(getClass()));

    @NotNull
    protected final java.util.logging.Logger logRef;

    @NotNull
    protected final ChatPlugin pluginType;

    @NotNull
    protected final Plugin pluginRef;

    protected AbstractChat(@NotNull ChatPlugin pluginType, @NotNull Plugin plugin, @NotNull Permission perms) {
        super(perms);
        this.logRef = plugin.getLogger();
        this.pluginType = pluginType;
        this.pluginRef = plugin;
    }

    @NotNull
    @Override
    public String getName() { return pluginType.getName(); }
}
