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

package org.essencemc.essencecore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.modules.Module;
import org.essencemc.essencecore.modules.Modules;
import org.essencemc.essencecore.modules.PlayerStorageModule;

public class ModuleListener implements Listener {

    @EventHandler
     private void playerPreLogin(AsyncPlayerPreLoginEvent event) {
        Modules modules = EssenceCore.inst().getModules();
        for (Module module : modules.modules) {
            if (module instanceof PlayerStorageModule) {
                ((PlayerStorageModule)module).onLoadPlayer(event.getUniqueId());
            }
        }
    }

    @EventHandler
    private void playerQuit(PlayerQuitEvent event) {
        Modules modules = EssenceCore.inst().getModules();
        for (Module module : modules.modules) {
            if (module instanceof PlayerStorageModule) {
                ((PlayerStorageModule)module).onSavePlayer(event.getPlayer().getUniqueId());
            }
        }
    }
}
