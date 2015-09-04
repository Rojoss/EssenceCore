package com.clashwars.essence.config.aliases;

import com.clashwars.essence.aliases.EItem;
import com.clashwars.essence.aliases.Items;
import com.clashwars.essence.config.EasyConfig;
import com.clashwars.essence.util.Debug;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemAliases extends EasyConfig {

    public HashMap<String, HashMap<String, String>> aliases = new HashMap<String, HashMap<String, String>>();

    public ItemAliases(String fileName) {
        this.setFile(fileName);
        load();
    }

    public void load() {
        super.load();
        HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
        List<EItem> items = Items.getItems();
        for (EItem item : items) {
            HashMap<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("name", item.getName());
            dataMap.put("aliases", item.getAliasesStr());
            data.put(item.getKey(), dataMap);
        }

        int changes = 0;

        //Remove old keys
        List<String> keys = new ArrayList<String>(aliases.keySet());
        for (String key : keys) {
            if (!data.containsKey(key)) {
                aliases.remove(key);
                changes++;
                break;
            }
        }

        //Add new keys
        for (String key : data.keySet()) {
            if (!aliases.containsKey(key)) {
                aliases.put(key, data.get(key));
                changes++;
                continue;
            }
        }

        if (changes > 0) {
            save();
        }

        for (EItem item : items) {
            item.setAliases(getAliases(item.getKey(), true));
        }
    }

    public List<String> getKeys() {
        return new ArrayList<String>(aliases.keySet());
    }

    public List<String> getAliases(String key, boolean includeName) {
        if (!aliases.containsKey(key)) {
            return new ArrayList<String>();
        }
        if (!aliases.get(key).containsKey("aliases")) {
            return new ArrayList<String>();
        }

        List<String> aliasesList = new ArrayList<String>();
        if (!aliases.get(key).get("aliases").isEmpty()) {
            aliasesList.addAll(Arrays.asList(aliases.get(key).get("aliases").toLowerCase().replaceAll(" ", "").split(",")));
        }
        if (includeName && aliases.get(key).containsKey("name")) {
            aliasesList.add(aliases.get(key).get("name").toLowerCase().replaceAll(" ", ""));
        }
        return aliasesList;
    }

}
