package net.milkbowl.vault;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * Largely taken from LuckPerms @ https://github.com/lucko/LuckPerms/blob/f0e1a8f0923b6784457253740bd81122d2f4f8d9/common/src/main/java/me/lucko/luckperms/common/api/ApiRegistrationUtil.java
 */
public class APIRegistrationUtil {
    @NotNull
    private static final Method REGISTER;

    @NotNull
    private static final Method UNREGISTER;

    static {
        try {
            REGISTER = VaultProvider.class.getDeclaredMethod("register", VaultAPI.class);
            REGISTER.setAccessible(true);

            UNREGISTER = VaultProvider.class.getDeclaredMethod("unregister");
            UNREGISTER.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void registerProvider(@NotNull VaultAPI vaultApi) {
        try {
            REGISTER.invoke(null, vaultApi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregisterProvider() {
        try {
            UNREGISTER.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
