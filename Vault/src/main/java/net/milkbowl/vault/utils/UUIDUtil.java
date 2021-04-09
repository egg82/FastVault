package net.milkbowl.vault.utils;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UUIDUtil {
    private UUIDUtil() { }

    @NotNull
    public static final UUID EMPTY_UUID = new UUID(0L, 0L);
}
