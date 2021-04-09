package net.milkbowl.vault.economy;

import org.jetbrains.annotations.NotNull;

/**
 * Economy plugins available to Vault.
 */
public enum EconomyPlugin {
    BOS_ECONOMY("BOSEconomy", "${boseconomy.version}", "BOSEconomy"),
    COMMANDS_EX("CommandsEX Economy", "${commandsex.version}", "CommandsEX"),
    CRAFTCONOMY3("Craftconomy3", "${craftconomy.version}", "Craftconomy3"),
    CURRENCY_CORE("CurrencyCore", "${currency.version}", "CurrencyCore"),
    DIGI_COIN("DigiCoin", "${digicoin.version}", "DigiCoin");

    @NotNull
    private final String name;

    @NotNull
    private final String version;

    @NotNull
    private final String pluginName;

    EconomyPlugin(@NotNull String name, @NotNull String version, @NotNull String pluginName) {
        this.name = name;
        this.version = version;
        this.pluginName = pluginName;
    }

    /**
     * Gets Vault's internal name of the economy plugin.
     *
     * @return the internal name of the economy plugin.
     */
    @NotNull
    public String getName() { return name; }

    /**
     * Gets the economy plugin's version.
     *
     * @return the version of the economy plugin.
     */
    @NotNull
    public String getVersion() { return version; }

    /**
     * Gets the economy plugin's name as is provided to/by Bukkit.
     *
     * @return the economy plugin's name
     */
    @NotNull
    public String getPluginName() { return pluginName; }
}
