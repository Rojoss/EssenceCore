package com.clashwars.essence;

public enum Message {
    //Main messages
    PREFIX("&8[&4Essence&8] &6"),
    NO_PERM("&cInsuficcient permissions! &8'&7{0}&8'"),
    DEAD_PLAYER("&4{0} &cis dead."),

    //Main command messages
    CMD_PLAYER_ONLY("&cThis command can only be executed by players."),
    CMD_INVALID_USAGE("&cInvalid usage! &7Command syntax: &8{0}"),

    //Argument parsing messages
    INVALID_OPTIONAL_ARGUMENT("&cThe argument &4{0} &cneeds to be a &4{1}&c! &7You specified &8'&c{2}&8'"),
    INVALID_PLAYER("&4{0} &cis not a valid player name."),
    NUMBER_TOO_LOW("&4{0} &cis too low! &7Can't be less than &c{1}&7."),
    NUMBER_TOO_HIGH("&4{0} &cis too high! &7Can't be more than &c{1}&7."),
    INVALID_LIST_ARGUMENT("&4{0} &cis not a valid argument! &7Arguments: &8{1}"),
    NO_STRING_MATCH("&4{0} &cdoesn't match with &4{1}&c."),
    DOESNT_START_WITH("&4{0} &cdoesn't start with &4{1}&c."),
    DOESNT_END_WITH("&4{0} &cdoesn't end with &4{1}&c."),
    TOO_FEW_CHARACTERS("&4{0} &cis too short! &7Need at least &8{1} &7characters."),
    TOO_MUCH_CHARACTERS("&4{1} &cis too long! &7Can't have more than &8{1} &7characters."),

    //Command messages
    CMD_ESSENCE_INFO("&8===== &4&lEssence plugin &8=====\n&8&o{0}\n&6Version&8: &7{1}\n&6Website&8: &9{2}\n&6Authors&8: &7{3}"),
    CMD_ESSENCE_RELOAD("Configs and commands reloaded."),
    CMD_HEAL_HEALED("You have been healed!"),
    CMD_HEAL_OTHER("You have healed &a{0}&6."),
    CMD_FEED_FEEDED("You have been feeded!"),
    CMD_FEED_OTHER("You have fed &a{0}&6."),

    //Command modifiers
    MOD_HELP("Show detailed command information."),
    MOD_SILENT("Don't send any messages."),
    MOD_HEAL_ONLY("Only modify the health limited by max health."),
    MOD_HEAL_MAX_ONLY("Only modify the max health."),

    //Command options/optional arguments
    OPT_HEAL_FEED("Restore hunger"),
    OPT_HEAL_CLEAR_EFFECTS("Remove all active potion effects"),
    OPT_HEAL_EXTINGUISH("Remove remaining fire ticks"),
    ;

    private String defaultMessage;

    Message(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefault() {
        return defaultMessage;
    }

    public static Message fromString(String name) {
        name = name.toLowerCase().replace("_", "");
        for (Message msg : values()) {
            if (msg.toString().toLowerCase().replace("_", "").equals(name)) {
                return msg;
            }
        }
        return null;
    }
}
