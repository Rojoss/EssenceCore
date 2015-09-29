/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
 * Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.essencemc.essencecore.config;

import org.essencemc.essencecore.ModuleCategory;

import java.util.HashMap;
import java.util.Map;

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
