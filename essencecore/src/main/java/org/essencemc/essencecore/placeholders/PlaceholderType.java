package org.essencemc.essencecore.placeholders;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;
import org.essencemc.essencecore.entity.EEntity;
import org.essencemc.essencecore.entity.EItem;

public enum PlaceholderType {
    INTEGER(Integer.class, "integer", "int", "in"),
    DOUBLE(Double.class, "double", "doub", "dou", "do"),
    FLOAT(Float.class, "float", "flo", "fl"),
    LONG(Long.class, "long", "lon"),
    STRING(String.class, "string", "str", "st"),
    BOOLEAN(Boolean.class, "boolean", "bool", "boo", "bo"),

    LOCATION(Location.class, "location", "loc"),
    VECTOR(Vector.class, "vector", "vec", "ve"),
    WORLD(World.class, "world", "wor", "wo"),
    PLAYER(Player.class, "player", "pla", "pl"),
    ENTITY(EEntity.class, "entity", "ent", "en"),
    ITEM(EItem.class, "item", "ite", "it"),
    INVENTORY(Inventory.class, "inventory", "inv", "in"),

    CUSTOM(String.class, "custom", "cus", "cu"),
    SERVER(Server.class, "server", "ser", "se")
    ;

    private Class clazz;
    private String[] names;

    PlaceholderType(Class clazz, String... names) {
        this.clazz = clazz;
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }

    public Class getClazz() {
        return clazz;
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

    public static String trimType(PlaceholderType type, String placeholder) {
        for (String prefix : type.getNames()) {
            if (placeholder.startsWith(prefix)) {
                return placeholder.replaceFirst("\\Q" + prefix + "\\E([^a-zA-Z0-9])?", "");
            }
        }
        return placeholder;
    }
}
