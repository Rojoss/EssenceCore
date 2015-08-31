package com.clashwars.essence.config;

import com.clashwars.essence.util.Util;

import java.util.*;

public class CommandsCfg extends EasyConfig {

    public Map<String, Map<String, String>> COMMANDS = new HashMap<String, Map<String, String>>();

    public CommandsCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    public void registerCommand(String label, String usage, String description, String permission, String[] aliases) {
        if (!COMMANDS.containsKey(label)) {
            Map<String, String> cmdData = new HashMap<String, String>();
            cmdData.put("description", description);
            cmdData.put("usage", usage);
            cmdData.put("permission", permission);
            cmdData.put("aliases", Util.implode(aliases, ","));
            cmdData.put("enabled", Boolean.toString(true));
            COMMANDS.put(label, cmdData);
            save();
        }
    }


    public String getUsage(String label) {
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("usage")) {
            return COMMANDS.get(label).get("usage").trim();
        }
        return "";
    }

    public String getDescription(String label) {
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("description")) {
            return COMMANDS.get(label).get("description").trim();
        }
        return "";
    }

    public String getPermission(String label) {
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("permission")) {
            return COMMANDS.get(label).get("permission").trim();
        }
        return "";
    }

    public List<String> getAliases(String label) {
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("aliases")) {
            return Arrays.asList(COMMANDS.get(label).get("aliases").trim().replace(" ", "").split(","));
        }
        return new ArrayList<String>();
    }

    public boolean isEnabled(String label) {
        if (label.equalsIgnoreCase("essence")) {
            return true;
        }
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("enabled")) {
            return Boolean.valueOf(COMMANDS.get(label).get("enabled").trim());
        }
        return false;
    }
}
