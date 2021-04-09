package net.milkbowl.vault.hooks;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.milkbowl.vault.config.ConfigUtil;
import net.milkbowl.vault.core.Pair;
import net.milkbowl.vault.logging.GELFLogger;
import net.milkbowl.vault.update.BukkitUpdater;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdaterHook implements PluginHook {
    @NotNull
    private final Logger logger = new GELFLogger(LoggerFactory.getLogger(getClass()));

    public static void create(@NotNull Plugin plugin, int id) {
        hook = new UpdaterHook(new BukkitUpdater(plugin, id));
    }

    @Nullable
    private static UpdaterHook hook = null;

    @Nullable
    public static UpdaterHook get() { return hook; }

    @NotNull
    private final ScheduledExecutorService workPool = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("FastVault_Updater_%d").build());

    @NotNull
    private final BukkitUpdater updater;

    private UpdaterHook(@NotNull BukkitUpdater updater) {
        this.updater = updater;

        workPool.scheduleWithFixedDelay(this::checkUpdate, 1L, 61L, TimeUnit.MINUTES);

        PluginHooks.getHooks().add(this);
    }

    @Override
    public void cancel() {
        workPool.shutdown();
        try {
            if (!workPool.awaitTermination(2L, TimeUnit.SECONDS)) {
                workPool.shutdownNow();
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public void checkUpdate(@NotNull CommandSender commandSender) {
        ConfigurationNode config = ConfigUtil.getConfig();

        if (!config.node("update", "check").getBoolean(true)) {
            return;
        }

        updater.isUpdateAvailable()
                .thenCombineAsync(updater.getLatestVersion(), Pair::new)
                .whenCompleteAsync((val, ex) -> {
                    if (ex != null) {
                        logger.error(ex.getClass().getName() + ": " + ex.getMessage(), ex);
                        return;
                    }

                    if (!Boolean.TRUE.equals(val.getT1())) {
                        return;
                    }

                    if (commandSender instanceof ConsoleCommandSender || config.node("update", "notify").getBoolean(true)) {
                        commandSender.sendMessage("FastVault has an update available! New version: " + val.getT2());
                    }
                });
    }

    private void checkUpdate() {
        checkUpdate(Bukkit.getConsoleSender());
    }
}
