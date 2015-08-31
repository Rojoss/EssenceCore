package com.clashwars.essence;

public enum Message {
    //Main messages
    PREFIX("&8[&4Essence&8] &6"),
    NO_PERM("&cInsuficcient permissions! &8'&7{0}&8'"),
    INVALID_PLAYER("&4{0} &cis not a valid player name."),
    DEAD_PLAYER("&4{0} &cis dead."),

    //Main command messages
    CMD_PLAYER_ONLY("&cThis command can only be executed by players."),
    CMD_INVALID_USAGE("&cInvalid usage! &7Command syntax: &8{0}"),

    //Command messages
    CMD_ESSENCE_INFO("&8===== &4&lEssence plugin &8=====\n&8&o{0}\n&6Version&8: &7{1}\n&6Website&8: &9{2}\n&6Authors&8: &7{3}"),
    CMD_ESSENCE_RELOAD("Configs and commands reloaded."),
    CMD_HEAL_HEALED("You have been healed!"),
    CMD_HEAL_OTHER("You have healed &a{0}&6."),
    CMD_FEED_FEEDED("You have been feeded!"),
    CMD_FEED_OTHER("You have fed &a{0}&6."),

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
