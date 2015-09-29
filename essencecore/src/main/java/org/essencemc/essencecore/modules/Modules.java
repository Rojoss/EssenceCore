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

package org.essencemc.essencecore.modules;

import org.bukkit.event.HandlerList;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.ModuleCategory;
import org.essencemc.essencecore.modules.ban.BanModule;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Modules {

    private EssenceCore ess;
    public List<Module> modules = new ArrayList<Module>();

    public Modules(EssenceCore ess) {
        this.ess = ess;
        registerModules();
    }

    /** Register all EssenceCore modules */
    public void registerModules() {
        registerModule(BanModule.class, ModuleCategory.COMMAND, "ban");
    }

    public Module getModule(Class<? extends Module> clazz) {
        for (Module module : modules) {
            if (module.getClass().equals(clazz)) {
                return module;
            }
        }
        return null;
    }

    public void registerModule(Class<? extends Module> clazz, ModuleCategory category, String moduleName) {
        if (!moduleName.isEmpty()) {
            ess.getModuleCfg().registerModule(category, moduleName);
        }
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(moduleName)) {
                if (!moduleName.isEmpty() && !ess.getModuleCfg().isEnabled(category, moduleName)) {
                    if (module instanceof StorageModule) {
                        ((SqlStorageModule)module).onSave();
                    }
                    module.onDisable();
                    HandlerList.unregisterAll(module);
                    modules.remove(module);
                } else {
                    module.onReload();
                }
                return;
            }
        }

        if (!moduleName.isEmpty() && !ess.getModuleCfg().isEnabled(category, moduleName)) {
            return;
        }

        if (clazz == null) {
            ess.logError("Failed to register the module " + moduleName);
            ess.logError("No class found for this command or it doesn't extend Module.");
            return;
        }

        try {
            Module module = clazz.getConstructor(String.class).newInstance(moduleName);
            modules.add(module);
            if (module instanceof SqlStorageModule) {
                ((SqlStorageModule)module).createTable();
            }
            module.onEnable();
            if (module instanceof StorageModule) {
                ((SqlStorageModule)module).onLoad();
            }
            ess.getServer().getPluginManager().registerEvents(module, ess);
        } catch (InstantiationException e) {
            ess.logError("Failed to register the module " + moduleName);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            ess.logError("Failed to register the module " + moduleName);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            ess.logError("Failed to register the module " + moduleName);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            ess.logError("Failed to register the module " + moduleName);
            e.printStackTrace();
        }
    }

    public void unregisterModule(String moduleName) {
        if (moduleName.isEmpty()) {
            return;
        }
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(moduleName)) {
                if (module instanceof StorageModule) {
                    ((SqlStorageModule)module).onSave();
                }
                module.onDisable();
                HandlerList.unregisterAll(module);
                modules.remove(module);
                return;
            }
        }
    }

}
