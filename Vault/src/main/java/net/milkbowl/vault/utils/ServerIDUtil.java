package net.milkbowl.vault.utils;

import net.milkbowl.vault.logging.GELFLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

public class ServerIDUtil {
    @NotNull
    private static final Logger logger = new GELFLogger(LoggerFactory.getLogger(ServerIDUtil.class));

    private ServerIDUtil() { }

    @NotNull
    public static UUID getId(@NotNull File idFile) {
        UUID retVal;

        try {
            retVal = readId(idFile);
        } catch (IOException ex) {
            logger.error(ex.getClass().getName() + ": " + ex.getMessage(), ex);
            retVal = null;
        }

        if (retVal == null) {
            retVal = UUID.randomUUID();
            try {
                writeId(idFile, retVal);
            } catch (IOException ex) {
                logger.error(ex.getClass().getName() + ": " + ex.getMessage(), ex);
            }
        }

        return retVal;
    }

    @Nullable
    private static UUID readId(@NotNull File idFile) throws IOException {
        if (!idFile.exists() || (idFile.exists() && idFile.isDirectory())) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        try (FileReader reader = new FileReader(idFile); BufferedReader in = new BufferedReader(reader)) {
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        String retVal = builder.toString().trim();

        try {
            return UUID.fromString(retVal);
        } catch (IllegalArgumentException ignored) {
        }

        return null;
    }

    private static void writeId(@NotNull File idFile, @NotNull UUID id) throws IOException {
        File parent = idFile.getParentFile();
        if (parent.exists() && !parent.isDirectory()) {
            Files.delete(parent.toPath());
        }
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IOException("Could not create parent directory structure.");
        }

        if (idFile.exists() && idFile.isDirectory()) {
            Files.delete(idFile.toPath());
        }
        if (!idFile.exists() && !idFile.createNewFile()) {
            throw new IOException("Stats file could not be created.");
        }

        try (FileWriter out = new FileWriter(idFile)) {
            out.write(id.toString() + System.lineSeparator());
        }
    }
}
