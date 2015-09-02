package com.clashwars.essence;

public enum Message {
    //Main messages
    PREFIX(MsgCat.GENERAL, "&8[&4Essence&8] &6"),
    NO_PERM(MsgCat.GENERAL, "&cInsuficcient permissions! &8'&7{0}&8'"),
    DEAD_PLAYER(MsgCat.GENERAL, "&4{0} &cis dead."),

    //Argument parsing messages
    INVALID_OPTIONAL_ARGUMENT(MsgCat.VALIDATION, "&cThe argument &4{0} &cneeds to be a &4{1}&c! &7You specified &8'&c{2}&8'"),
    INVALID_PLAYER(MsgCat.VALIDATION, "&4{0} &cis not a valid player name."),
    NUMBER_TOO_LOW(MsgCat.VALIDATION, "&4{0} &cis too low! &7Can't be less than &c{1}&7."),
    NUMBER_TOO_HIGH(MsgCat.VALIDATION, "&4{0} &cis too high! &7Can't be more than &c{1}&7."),
    INVALID_LIST_ARGUMENT(MsgCat.VALIDATION, "&4{0} &cis not a valid argument! &7Arguments: &8{1}"),
    INVALID_LOCATION(MsgCat.VALIDATION, "&4{0} &cis not a valid location!"),
    NOT_A_NUMBER(MsgCat.VALIDATION, "&4{0} &cis not a number!"),
    CANT_USE_RELATIVE_COORDS(MsgCat.VALIDATION, "&cYou can't use relative coordinates in the command line!"),
    NO_STRING_MATCH(MsgCat.VALIDATION, "&4{0} &cdoesn't match with &4{1}&c."),
    DOESNT_START_WITH(MsgCat.VALIDATION, "&4{0} &cdoesn't start with &4{1}&c."),
    DOESNT_END_WITH(MsgCat.VALIDATION, "&4{0} &cdoesn't end with &4{1}&c."),
    TOO_FEW_CHARACTERS(MsgCat.VALIDATION, "&4{0} &cis too short! &7Need at least &8{1} &7characters."),
    TOO_MUCH_CHARACTERS(MsgCat.VALIDATION, "&4{1} &cis too long! &7Can't have more than &8{1} &7characters."),

    //Main command messages
    CMD_PLAYER_ONLY(MsgCat.COMMAND_OTHER, "&cThis command can only be executed by players."),
    CMD_INVALID_USAGE(MsgCat.COMMAND_OTHER, "&cInvalid usage! &7Command syntax: &8{0}"),

    //Command messages
    CMD_ESSENCE_INFO(MsgCat.COMMAND, "&8===== &4&lEssence plugin &8=====\n&8&o{0}\n&6Version&8: &7{1}\n&6Website&8: &9{2}\n&6Authors&8: &7{3}"),
    CMD_ESSENCE_RELOAD(MsgCat.COMMAND, "Configs and commands reloaded."),
    CMD_HEAL_HEALED(MsgCat.COMMAND, "You have been healed!"),
    CMD_HEAL_OTHER(MsgCat.COMMAND, "You have healed &a{0}&6."),
    CMD_FEED_FEEDED(MsgCat.COMMAND, "You have been feeded!"),
    CMD_FEED_OTHER(MsgCat.COMMAND, "You have fed &a{0}&6."),
    CMD_GAMEMODE_CHANGED(MsgCat.COMMAND, "Gamemode changed to &a{0}&6."),
    CMD_GAMEMODE_OTHER(MsgCat.COMMAND, "You have changed &a{0}'s &6gamemode to &a{1}&6."),
    CMD_GAMEMODE_INVALID(MsgCat.COMMAND, "&4{0} &cis not a valid gamemode!"),
    CMD_LIGHTNING(MsgCat.COMMAND, "Lightning has struck!"),

    //Command modifiers
    MOD_HELP(MsgCat.COMMAND_MODIFIERS, "Show detailed command information."),
    MOD_SILENT(MsgCat.COMMAND_MODIFIERS, "Don't send any messages."),
    MOD_HEAL_ONLY(MsgCat.COMMAND_MODIFIERS, "Only modify the health limited by max health."),
    MOD_HEAL_MAX_ONLY(MsgCat.COMMAND_MODIFIERS, "Only modify the max health."),

    //Command options/optional arguments
    OPT_HEAL_FEED(MsgCat.COMMAND_OPTIONS, "Restore hunger"),
    OPT_HEAL_CLEAR_EFFECTS(MsgCat.COMMAND_OPTIONS, "Remove all active potion effects"),
    OPT_HEAL_EXTINGUISH(MsgCat.COMMAND_OPTIONS, "Remove remaining fire ticks"),
    OPT_FEED_SATURATION(MsgCat.COMMAND_OPTIONS, "The amount of saturation given"),
    OPT_FEED_EXHAUSTION(MsgCat.COMMAND_OPTIONS, "Reset exhaustion"),
    ;

    private String defaultMessage;
    private MsgCat cat;

    Message(MsgCat cat, String defaultMessage) {
        this.defaultMessage = defaultMessage;
        this.cat = cat;
    }

    public String getDefault() {
        return defaultMessage;
    }

    public MsgCat getCat() {
        return cat;
    }

    public static Message fromString(String name) {
        name = name.toLowerCase().replace("_", "");
        name = name.toLowerCase().replace("-", "");
        for (Message msg : values()) {
            if (msg.toString().toLowerCase().replace("_", "").equals(name)) {
                return msg;
            }
        }
        return null;
    }

    public enum MsgCat {
        GENERAL,
        COMMAND,
        COMMAND_MODIFIERS,
        COMMAND_OPTIONS,
        COMMAND_OTHER,
        VALIDATION,
        OTHER,
        ;

        public static MsgCat fromString(String name) {
            name = name.toLowerCase().replace("_", "");
            name = name.toLowerCase().replace("-", "");
            for (MsgCat cat : values()) {
                if (cat.toString().toLowerCase().replace("_", "").equals(name)) {
                    return cat;
                }
            }
            return null;
        }
    }
}
