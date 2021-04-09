package net.milkbowl.vault.permission;

import org.jetbrains.annotations.NotNull;

/**
 * Permission plugins available to Vault.
 */
public enum PermissionPlugin {
    B_PERMISSIONS("bPermissions", "${bpermissions.version}", "bPermissions"),
    B_PERMISSIONS2("bPermissions2", "${bpermissions.version}", "bPermissions"),
    DROX_PERMS("DroxPerms", "${droxperms.version}", "DroxPerms"),
    GROUP_MANAGER("GroupManager", "${groupmanager.version}", "GroupManager"),
    K_PERMS("KPerms", "${kperms.version}", "KPerms"),
    LUCK_PERMS("LuckPerms", "${luckperms.version}", "LuckPerms"),
    OVER_PERMISSIONS("OverPermissions", "${overpermissions.version}", "OverPermissions"),
    PERMISSIONS3("Permissions3", "${permissions.version}", "Permissions"),
    PERMISSIONS_BUKKIT("PermissionsBukkit", "${permissionsbukkit.version}", "PermissionsBukkit"),
    PERMISSIONS_EX("PermissionsEx", "${permissionsex.version}", "PermissionsEx"),
    PRIVILEGES("Privileges", "${privileges.version}", "Privileges"),
    RSC_PERMISSIONS("rscPermissions", "${rscpermissions.version}", "rscPermissions"),
    SIMPLY_PERMS("SimplyPerms", "${simplyperms.version}", "SimplyPerms"),
    STARBURST("Starburst", "${starburst.version}", "Starburst"),
    TOTAL_PERMISSIONS("TotalPermissions", "${totalpermissions.version}", "TotalPermissions"),
    V_PERMS("vPerms", "${permissions.version}", "vPerms"),
    X_PERMS("Xperms", "${xperms.version}", "Xperms");

    @NotNull
    private final String name;

    @NotNull
    private final String version;

    @NotNull
    private final String pluginName;

    PermissionPlugin(@NotNull String name, @NotNull String version, @NotNull String pluginName) {
        this.name = name;
        this.version = version;
        this.pluginName = pluginName;
    }

    /**
     * Gets Vault's internal name of the permission plugin.
     *
     * @return the internal name of the permission plugin
     */
    @NotNull
    public String getName() { return name; }

    /**
     * Gets the permission plugin's version.
     *
     * @return the version of the permission plugin
     */
    @NotNull
    public String getVersion() { return version; }

    /**
     * Gets the permission plugin's name as is provided to/by Bukkit.
     *
     * @return the permission plugin's name
     */
    @NotNull
    public String getPluginName() { return pluginName; }

    @Override
    public String toString() {
        return "PermissionPlugin{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", pluginName='" + pluginName + '\'' +
                '}';
    }
}
