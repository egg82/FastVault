package net.milkbowl.vault.update;

import net.milkbowl.vault.utils.TimeUtil;
import net.milkbowl.vault.web.WebRequest;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BukkitUpdater extends AbstractUpdater {
    private final int resourceId;

    public BukkitUpdater(@NotNull Plugin plugin, int resourceId) {
        this(plugin, resourceId, new TimeUtil.Time(1L, TimeUnit.HOURS));
    }

    public BukkitUpdater(@NotNull Plugin plugin, int resourceId, @NotNull TimeUtil.Time checkDelay) {
        super(plugin.getDescription().getVersion(), checkDelay);
        if (resourceId <= 0) {
            throw new IllegalArgumentException("id cannot be <= 0.");
        }
        this.resourceId = resourceId;
    }

    @Override
    protected @NotNull String getNewVersion() throws IOException {
        return WebRequest.builder(new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId))
                .timeout(new TimeUtil.Time(5000L, TimeUnit.MILLISECONDS))
                .userAgent("egg82/Updater")
                .header("Accept", "text/plain")
                .build()
                .getString();
    }

    @Override
    public @NotNull String getDownloadLink() { return "https://api.spiget.org/v2/resources/" + resourceId + "/versions/latest/download"; }
}
