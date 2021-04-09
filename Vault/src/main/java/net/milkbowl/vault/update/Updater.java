package net.milkbowl.vault.update;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface Updater {
    @NotNull
    String getCurrentVersion();

    @NotNull
    CompletableFuture<@NotNull Boolean> isUpdateAvailable();

    @NotNull
    CompletableFuture<@NotNull String> getLatestVersion();

    @NotNull
    String getDownloadLink();
}
