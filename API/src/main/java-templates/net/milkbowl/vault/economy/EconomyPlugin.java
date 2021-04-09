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
    DIGI_COIN("DigiCoin", "${digicoin.version}", "DigiCoin"),
    DOSH("Dosh", "${dosh.version}", "Dosh"),
    ECON_XP("EconXP", "${econxp.version}", "EconXP"),
    ESSENTIALS("Essentials", "${essentials.version}", "Essentials"),
    ESSENTIALS_X("EssentialsX", "${essentialsx.version}", "Essentials"),
    E_WALLET("eWallet", "${ewallet.version}", "eWallet"),
    GOLDEN_CHEST_ECONOMY("GoldenChestEconomy", "${goldenchesteconomy.version}", "GoldenChestEconomy"),
    GOLD_IS_MONEY("GoldIsMoney", "${goldismoney.version}", "GoldIsMoney"),
    GRINGOTTS("Gringotts", "${gringotts.version}", "Gringotts"),
    I_CONOMY("iConomy ", "${iconomy.version}", "iConomy"),
    MC_MONEY("McMoney", "${mcmoney.version}", "McMoney"),
    MI_CONOMY("MiConomy", "${miconomy.version}", "MiConomy"),
    MINE_CONOMY("MineConomy", "${mineconomy.version}", "MineConomy"),
    MINEFACONOMY("Minefaconomy", "${minefaconomy.version}", "Minefaconomy"),
    MULTI_CURRENCY("MultiCurrency", "${multicurrency.version}", "MultiCurrency"),
    SDF_ECONOMY("SDFEconomy", "${sdfeconomy.version}", "SDFEconomy"),
    TA_ECON("TAEcon", "${taecon.version}", "TAEcon"),
    XP_BANK("XPBank", "${xpbank.version}", "XPBank");

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
     * @return the internal name of the economy plugin
     */
    @NotNull
    public String getName() { return name; }

    /**
     * Gets the economy plugin's version.
     *
     * @return the version of the economy plugin
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
