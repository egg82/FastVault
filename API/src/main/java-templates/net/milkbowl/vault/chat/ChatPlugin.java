package net.milkbowl.vault.chat;

import org.jetbrains.annotations.NotNull;

/**
 * Chat plugins available to Vault.
 */
public enum ChatPlugin {
    B_PERMISSIONS("bInfo", "${bpermissions.version}", "bPermissions"),
    B_PERMISSIONS2("bPermissions2", "${bpermissions.version}", "bPermissions"),
    DROX_PERMS("DroxPerms", "${droxperms.version}", "DroxPerms"),
    ESSENTIALS_X_CHAT("EssentialsChat", "${essentialsx.version}", "EssentialsChat"),
    GROUP_MANAGER("GroupManager", "${groupmanager.version}", "GroupManager"),
    I_CHAT("iChat", "${ichat.version}", "iChat"),
    M_CHAT("mChat", "${mchat.version}", "mChat"),
    M_CHAT_SUITE("mChatSuite", "${mchatsuite.version}", "mChatSuite"),
    OVER_PERMISSIONS("OverPermissions", "${overpermissions.version}", "OverPermissions"),
    PERMISSIONS3("Permissions3", "${permissions.version}", "Permissions"),
    PERMISSIONS_EX("PermissionsEx", "${permissionsex.version}", "PermissionsEx"),
    PRIVILEGES("Privileges", "${privileges.version}", "Privileges"),
    RSC_PERMISSIONS("rscPermissions", "${rscpermissions.version}", "rscPermissions"),
    TOTAL_PERMISSIONS("TotalPermissions", "${totalpermissions.version}", "TotalPermissions");

    @NotNull
    private final String name;

    @NotNull
    private final String version;

    @NotNull
    private final String pluginName;

    ChatPlugin(@NotNull String name, @NotNull String version, @NotNull String pluginName) {
        this.name = name;
        this.version = version;
        this.pluginName = pluginName;
    }

    /**
     * Gets Vault's internal name of the chat plugin.
     *
     * @return the internal name of the chat plugin
     */
    @NotNull
    public String getName() { return name; }

    /**
     * Gets the chat plugin's version.
     *
     * @return the version of the chat plugin
     */
    @NotNull
    public String getVersion() { return version; }

    /**
     * Gets the chat plugin's name as is provided to/by Bukkit.
     *
     * @return the chat plugin's name
     */
    @NotNull
    public String getPluginName() { return pluginName; }

    @Override
    public String toString() {
        return "ChatPlugin{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", pluginName='" + pluginName + '\'' +
                '}';
    }
}
