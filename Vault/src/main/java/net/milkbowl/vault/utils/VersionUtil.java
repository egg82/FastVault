package net.milkbowl.vault.utils;

import com.google.common.primitives.Ints;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VersionUtil {
    private VersionUtil() { }

    public static boolean isAtLeast(@NotNull String atLeastVersion, char separator1, @NotNull String versionToTest, char separator2) {
        int[] v1 = parseVersion(atLeastVersion, separator1);
        int[] v2 = parseVersion(versionToTest, separator2);

        boolean equalOrGreater = true;
        for (int i = 0; i < v1.length; i++) {
            if (i > v2.length) {
                // We're looking for a version deeper than what we have
                // eg. 1.12.2 -> 1.12
                equalOrGreater = false;
                break;
            }
            if (v2[i] > v1[i]) {
                // The version we're at now is greater than the one we want
                // eg. 1.11 -> 1.13
                break;
            }
            if (v2[i] < v1[i]) {
                // The version we're at now is less than the one we want
                // eg. 1.13 -> 1.11
                equalOrGreater = false;
                break;
            }
        }

        return equalOrGreater;
    }

    public static int @NotNull [] parseVersion(@NotNull String version, char separator) {
        List<Integer> ints = new ArrayList<>();

        int lastIndex = 0;
        int currentIndex = version.indexOf(separator);

        while (currentIndex > -1) {
            int current = tryParseInt(version.substring(lastIndex, currentIndex));
            if (current > -1) {
                ints.add(current);
            }

            lastIndex = currentIndex + 1;
            currentIndex = version.indexOf(separator, currentIndex + 1);
        }
        int current = tryParseInt(version.substring(lastIndex));
        if (current > -1) {
            ints.add(current);
        }

        return Ints.toArray(ints);
    }

    public static int tryParseInt(@NotNull String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return -1;
        }
    }
}
