package net.milkbowl.vault.hooks;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PluginHooks {
    @NotNull
    private static final List<@NotNull PluginHook> hooks = new CopyOnWriteArrayList<>();

    @NotNull
    public static List<@NotNull PluginHook> getHooks() { return hooks; }

    private PluginHooks() { }
}
