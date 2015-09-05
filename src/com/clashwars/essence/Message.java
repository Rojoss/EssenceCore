package com.clashwars.essence;

public enum Message {
    //Main messages
    PREFIX(MsgCat.GENERAL, "&8[&4Essence&8] &6"),
    NO_PERM(MsgCat.GENERAL, "&cInsuficcient permissions! &8'&7{0}&8'"),
    DEAD_PLAYER(MsgCat.GENERAL, "&4{0} &cis dead."),

    //Argument parsing messages
    INVALID_OPTIONAL_ARGUMENT(MsgCat.VALIDATION, "&cThe argument &4{0} &cneeds to be a &4{1}&c! &7You specified &8'&c{2}&8'"),
    INVALID_PLAYER(MsgCat.VALIDATION, "&4{0} &cis not a valid player name or uuid."),
    INVALID_WORLD(MsgCat.VALIDATION, "&4{0} &cis not a valid world name, id or uuid."),
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
    CMD_LIGHTNING(MsgCat.COMMAND, "Lightning has struck!"),
    CMD_WARP_SET(MsgCat.COMMAND, "Warp &a{0} &6set!"),
    CMD_WARP_DELETED(MsgCat.COMMAND, "Warp &a{0} &6deleted!"),
    CMD_WARP_DELETED_AlL(MsgCat.COMMAND, "All warps have been deleted!"),
    CMD_WARP_INVALID(MsgCat.COMMAND, "&cNo warp found with the name &4{0}&c!"),
    CMD_WARPS(MsgCat.COMMAND, "&6&lWarps&8&l: &7{0}"),
    CMD_WARPS_NONE(MsgCat.COMMAND, "No warps set yet!"),
    CMD_WARP_USE(MsgCat.COMMAND, "Warping to &a{0}&6..."),
    CMD_WARP_OTHER(MsgCat.COMMAND, "You have send &a{0} &6to the warp &a{1}&6."),
    CMD_TP(MsgCat.COMMAND, "&6Teleported to &a{0}&6."),
    CMD_TP_OTHER(MsgCat.COMMAND, "&6You have teleported &a{1} &6to &a{0}&6."),
    CMD_NICK_CHANGED(MsgCat.COMMAND, "&6Nickname changed to &r{0}"),
    CMD_NICK_OTHER(MsgCat.COMMAND, "&6You have changed &a{1}'s &6nickname to &r{0}&6."),
    CMD_REMOVEEFFECT(MsgCat.COMMAND, "&6Removed &a{0} &6potion effect."),
    CMD_REMOVEEFFECT_ALL(MsgCat.COMMAND, "&6All potion effects removed."),
    CMD_REMOVEEFFECT_OTHER(MsgCat.COMMAND, "&6Removed &a{0}&6's &a{1} &6potion effect."),
    CMD_REMOVEEFFECT_OTHER_ALL(MsgCat.COMMAND, "&6All of &a{0}'s potion effects have been removed."),
    CMD_BURN(MsgCat.COMMAND, "&6You will burn for &a{0} &6seconds."),
    CMD_BURN_OTHER(MsgCat.COMMAND, "&a{0} &6will burn for &a{1} &6seconds."),

    //Command modifiers
    MOD_HELP(MsgCat.COMMAND_MODIFIERS, "Show detailed command information"),
    MOD_SILENT(MsgCat.COMMAND_MODIFIERS, "Don't send any messages"),
    MOD_HEAL_ONLY(MsgCat.COMMAND_MODIFIERS, "Only modify the health limited by max health"),
    MOD_HEAL_MAX_ONLY(MsgCat.COMMAND_MODIFIERS, "Only modify the max health"),
    MOD_DELWARP_ALL(MsgCat.COMMAND_MODIFIERS, "Delete all warps"),
    MOD_NICK_REMOVE(MsgCat.COMMAND_MODIFIERS, "Remove your nickname."),
    MOD_REMOVEEFFECT_NEGATIVE(MsgCat.COMMAND_MODIFIERS, "Will ignore all positive potion effects and only remove the negative ones"),
    MOD_REMOVEEFFECT_POSITIVE(MsgCat.COMMAND_MODIFIERS, "Will ignore all negative potion effects and only remove the positive ones"),
    MOD_BURN_INCREMENT(MsgCat.COMMAND_MODIFIERS, "Increment the duration if the player is already burning"),

    //Command options/optional arguments
    OPT_HEAL_FEED(MsgCat.COMMAND_OPTIONS, "Restore hunger?"),
    OPT_HEAL_CLEAR_EFFECTS(MsgCat.COMMAND_OPTIONS, "Remove all active potion effects?"),
    OPT_HEAL_EXTINGUISH(MsgCat.COMMAND_OPTIONS, "Remove remaining fire ticks?"),
    OPT_FEED_SATURATION(MsgCat.COMMAND_OPTIONS, "The amount of saturation given."),
    OPT_FEED_EXHAUSTION(MsgCat.COMMAND_OPTIONS, "Reset exhaustion?"),
    OPT_WARP_PERM_BASED(MsgCat.COMMAND_OPTIONS, "Should warps be permissions based? Like essence.warp.spawn to use /warp spawn"),
    OPT_NICK_PREFIX(MsgCat.COMMAND_OPTIONS, "Prefix added in front of all nicknames."),
    OPT_NICK_MIN_CHARS(MsgCat.COMMAND_OPTIONS, "Minimum amount of characters required. (exclusive prefix)"),
    OPT_NICK_MAX_CHARS(MsgCat.COMMAND_OPTIONS, "Maximum amount of characters allowed. (exclusive prefix)"),
    OPT_BURN_TICKS(MsgCat.COMMAND_OPTIONS, "Change the time from seconds to ticks for more precision. (20 ticks per second)")
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
