package info.mcessence.essence.aliases;

import info.mcessence.essence.util.Util;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class ItemAlias {

    private String name;
    private Material type;
    private int id;
    private short data;
    private List<String> aliases;

    public ItemAlias(String name, Material type, short data, String[] aliases) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.aliases = Arrays.asList(aliases);
        id = type.getId();
        type.name();
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return type.toString().toLowerCase() + "-" + data;
    }

    public Material getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public short getData() {
        return data;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getAliasesStr() {
        return Util.implode(aliases,",");
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void addAlias(String alias) {
        aliases.add(alias);
    }

    public boolean hasAlias(String alias) {
        return aliases.contains(alias);
    }
}
