package org.essencemc.essencecore.arguments;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.entity.EEntity;

public enum ArgumentType {
    INTEGER(new IntArg(), "integer", "int", "in"),
    DOUBLE(new DoubleArg(), "double", "doub", "dou", "do"),
    FLOAT(new FloatArg(), "float", "flo", "fl"),
    LONG(new StringArg(), "long", "lon", "lo"), //TODO: Long arg
    STRING(new StringArg(), "string", "str", "st"),
    BOOLEAN(new BoolArg(), "boolean", "bool", "boo", "bo"),

    LOCATION(new LocationArg(), "location", "loc", "lo"),
    VECTOR(new VectorArg(), "vector", "vec", "ve"),
    WORLD(new WorldArg(), "world", "wor", "wo"),
    PLAYER(new PlayerArg(), "player", "pla", "pl"),
    ENTITY(new StringArg(), "entity", "ent", "en"), //TODO: Entity arg
    ITEM(new ItemArg(), "item", "ite", "it"),
    MATERIAL(new MaterialArg(), "material", "materialdata", "mat", "ma"),
    INVENTORY(new StringArg(), "inventory", "inv", "in"), //TODO: Inventory arg

    UNKNOWN(new StringArg(), "unknown"),
    NULL(null, "null", "undefined")
    ;

    private String[] names;
    private Argument arg;

    ArgumentType(Argument arg, String... names) {
        this.arg = arg;
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }

    public Argument getNewArg() {
        return arg.clone();
    }

    public static ArgumentType fromString(String name) {
        name = name.toLowerCase().replace("_","");
        for (ArgumentType arg : values()) {
            for (String n : arg.names) {
                if (name.equals(n)) {
                    return arg;
                }
            }
        }
        return null;
    }

    public static ArgumentType fromObject(Object obj) {
        if (obj == null) {
            return ArgumentType.NULL;
        }
        if (obj instanceof Boolean) {
            return ArgumentType.BOOLEAN;
        } else if (obj instanceof  Integer) {
            return ArgumentType.INTEGER;
        } else if (obj instanceof Long) {
            return ArgumentType.LONG;
        } else if (obj instanceof Float) {
            return ArgumentType.FLOAT;
        } else if (obj instanceof Double) {
            return ArgumentType.DOUBLE;
        } else if (obj instanceof Location) {
            return ArgumentType.LOCATION;
        } else if (obj instanceof Vector) {
            return ArgumentType.VECTOR;
        } else if (obj instanceof World) {
            return ArgumentType.WORLD;
        } else if (obj instanceof Player) {
            return ArgumentType.PLAYER;
        } else if (obj instanceof Entity || obj instanceof EEntity) {
            return ArgumentType.ENTITY;
        } else if (obj instanceof ItemStack) {
            return ArgumentType.ITEM;
        } else if (obj instanceof Inventory) {
            return ArgumentType.INVENTORY;
        } else if (obj instanceof MaterialData) {
            return ArgumentType.MATERIAL;
        } else if (obj instanceof String) {
            return ArgumentType.STRING;
        }
        return ArgumentType.UNKNOWN;
    }

    public static ArgumentType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return ArgumentType.NULL;
        }
        if (parseSuccessful(ArgumentType.BOOLEAN, value)) {
            return ArgumentType.BOOLEAN;
        } else if (!value.endsWith("l") && !value.endsWith("L") && parseSuccessful(ArgumentType.INTEGER, value)) {
            return ArgumentType.INTEGER;
        } else if (parseSuccessful(ArgumentType.LONG, value)) {
            return ArgumentType.LONG;
        } else if (!value.endsWith("d") && !value.endsWith("D") && parseSuccessful(ArgumentType.FLOAT, value)) {
            return ArgumentType.FLOAT;
        } else if (!value.endsWith("f") && !value.endsWith("F") && parseSuccessful(ArgumentType.DOUBLE, value)) {
            return ArgumentType.DOUBLE;
        } else if (parseSuccessful(ArgumentType.PLAYER, value)) {
            return ArgumentType.PLAYER;
        } else if (parseSuccessful(ArgumentType.WORLD, value)) {
            return ArgumentType.WORLD;
        } else if (parseSuccessful(ArgumentType.VECTOR, value)) {
            return ArgumentType.VECTOR;
        } else if (parseSuccessful(ArgumentType.WORLD, value)) {
            return ArgumentType.WORLD;
        } else if (parseSuccessful(ArgumentType.MATERIAL, value)) {
            return ArgumentType.MATERIAL;
        } else if (parseSuccessful(ArgumentType.ITEM, value)) {
            return ArgumentType.ITEM;
        } else if (parseSuccessful(ArgumentType.ENTITY, value)) {
            return ArgumentType.ENTITY;
        } else if (parseSuccessful(ArgumentType.INVENTORY, value)) {
            return ArgumentType.INVENTORY;
        } else if (parseSuccessful(ArgumentType.STRING, value)) {
            return ArgumentType.STRING;
        }
        return ArgumentType.UNKNOWN;
    }

    private static boolean parseSuccessful(ArgumentType type, String value) {
        if (type.getNewArg().parse(value)) {
            return true;
        } else {
            return false;
        }
    }
}
