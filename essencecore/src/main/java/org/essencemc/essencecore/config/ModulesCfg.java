/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://essencemc.org>
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

import java.util.HashMap;
import java.util.Map;

public class ModulesCfg extends EasyConfig {

    public HashMap<String, Map<String, Boolean>> modules = new HashMap<String, Map<String, Boolean>>();

    public ModulesCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    /**
     * Register a new module.
     * If the parent is null or empty it will set the parent to the module itself.
     * The state is the initial module state so if it's enabled or not.
     * It's recommended to keep all modules disabled on first load so that there aren't any configurations and such created.
     * @return true if it registered and false if there is a module registered with this name already.
     */
    public boolean registerModule(String parent, String module, boolean state) {
        if (parent == null || parent.isEmpty()) {
            parent = module;
        }
        parent = parent.toUpperCase();
        module = module.toLowerCase();
        Map<String, Boolean> moduleList = new HashMap<String, Boolean>();
        if (modules.containsKey(parent)) {
            moduleList = modules.get(parent);
        }

        if (!moduleList.containsKey(module)) {
            moduleList.put(module, state);
            modules.put(parent, moduleList);
            save();
            return true;
        }
        return false;
    }

    /**
     * Check if the specified module is enabled or not.
     * If the parent is null or empty it will set the parent to the module itself.
     * If the module isn't registered it will return false.
     */
    public boolean isEnabled(String parent, String module) {
        if (parent == null || parent.isEmpty()) {
            parent = module;
        }
        parent = parent.toUpperCase();
        module = module.toLowerCase();
        if (!modules.containsKey(parent)) {
            return false;
        }
        module = module.toLowerCase();
        if (modules.get(parent).containsKey(module)) {
            return modules.get(parent).get(module);
        }
        return false;
    }

    /**
     * Enable/disable modules through code.
     * If the parent is null or empty it will set the parent to the module itself.
     * Users can still re-enable modules in the config so don't use this to remove/disable things.
     */
    public void setEnabled(String parent, String module, boolean state) {
        if (parent == null || parent.isEmpty()) {
            parent = module;
        }
        parent = parent.toUpperCase();
        module = module.toLowerCase();
        Map<String, Boolean> moduleList = new HashMap<String, Boolean>();
        if (modules.containsKey(parent)) {
            moduleList = modules.get(parent);
        }

        if (moduleList.containsKey(module)) {
            moduleList.put(module, state);
            modules.put(parent, moduleList);
            save();
        }
    }
}
