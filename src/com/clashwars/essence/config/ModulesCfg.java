package com.clashwars.essence.config;

import com.clashwars.essence.ModuleCategory;

import java.util.*;

public class ModulesCfg extends EasyConfig {

    public HashMap<String, Map<String, Boolean>> modules = new HashMap<String, Map<String, Boolean>>();

    public ModulesCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    /**
     * Register a new module an enable it by default.
     * If there is a module registered already in the same category with the same name it will return false.
     */
    public boolean registerModule(ModuleCategory category, String module) {
        String cat = category.toString().toLowerCase().replace("_","-");
        module = module.toLowerCase();
        Map<String, Boolean> moduleList = new HashMap<String, Boolean>();
        if (modules.containsKey(cat)) {
            moduleList = modules.get(cat);
        }

        if (!moduleList.containsKey(module)) {
            moduleList.put(module, true);
            modules.put(cat, moduleList);
            save();
            return true;
        }
        return false;
    }

    /**
     * Check if the specified module is enabled or not.
     * If the module isn't registered it will return false.
     */
    public boolean isEnabled(ModuleCategory category, String module) {
        String cat = category.toString().toLowerCase().replace("_","-");
        if (!modules.containsKey(cat)) {
            return false;
        }
        module = module.toLowerCase();
        if (modules.get(cat).containsKey(module)) {
            return modules.get(cat).get(module);
        }
        return false;
    }

    /**
     * Enable/disable modules through code.
     * Users can still re-enable modules in the config so don't use this to remove/disable things.
     */
    public void setEnabled(ModuleCategory category, String module, boolean state) {
        String cat = category.toString().toLowerCase().replace("_","-");
        module = module.toLowerCase();
        Map<String, Boolean> moduleList = new HashMap<String, Boolean>();
        if (modules.containsKey(cat)) {
            moduleList = modules.get(cat);
        }

        if (moduleList.containsKey(module)) {
            moduleList.put(module, state);
            modules.put(cat, moduleList);
            save();
        }
    }
}
