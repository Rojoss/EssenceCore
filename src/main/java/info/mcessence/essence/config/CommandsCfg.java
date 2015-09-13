package info.mcessence.essence.config;

import info.mcessence.essence.util.Util;

import java.util.*;

public class CommandsCfg extends EasyConfig {

    public Map<String, Map<String, String>> COMMANDS = new HashMap<String, Map<String, String>>();

    public CommandsCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    public void registerCommand(String label, String description, String permission, String[] aliases) {
        if (!COMMANDS.containsKey(label)) {
            Map<String, String> cmdData = new HashMap<String, String>();
            cmdData.put("description", description);
            cmdData.put("permission", permission);
            cmdData.put("aliases", Util.implode(aliases, ","));
            COMMANDS.put(label, cmdData);
            save();
        }
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
}
