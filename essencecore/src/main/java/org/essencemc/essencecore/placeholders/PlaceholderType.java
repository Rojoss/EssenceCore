package org.essencemc.essencecore.placeholders;

public enum PlaceholderType {
    INTEGER("integer", "int", "in"),
    DOUBLE("double", "doub", "dou", "do"),
    FLOAT("float", "flo", "fl"),
    LONG("long", "lon", "lo"),
    STRING("string", "str", "st"),
    BOOLEAN("boolean", "bool", "boo", "bo"),

    LOCATION("location", "loc", "lo"),
    VECTOR("vector", "vec", "ve"),
    WORLD("world", "wor", "wo"),
    PLAYER("player", "pla", "pl"),
    ENTITY("entity", "ent", "en"),
    ITEM("item", "ite", "it"),
    INVENTORY("inventory", "inv", "in"),

    CUSTOM("string", "str", "st"),
    SERVER("server", "ser", "se")
    ;
    
    private String[] names;

    PlaceholderType(String... names) {
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }

    public static PlaceholderType fromString(String name) {
        name = name.toLowerCase().replace("_","");
        for (PlaceholderType pt : values()) {
            for (String n : pt.names) {
                if (name.startsWith(n)) {
                    return pt;
                }
            }
        }
        return null;
    }
}
