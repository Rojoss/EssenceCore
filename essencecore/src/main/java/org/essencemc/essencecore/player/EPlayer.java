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

package org.essencemc.essencecore.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.EssenceCore;

import java.util.UUID;

public class EPlayer {

    private EssenceCore ess;
    private UUID uuid;

    public EPlayer(UUID uuid) {
        ess = EssenceCore.inst();
        this.uuid = uuid;
    }


    // ##################################################
    // ###################### DATA ######################
    // ##################################################



    // ##################################################
    // ##################### BUKKIT #####################
    // ##################################################

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return getOfflinePlayer().getName();
    }

    public String getDisplayName() {
        Player player = getPlayer();
        return player == null ? null : player.getDisplayName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public boolean isOnline() {
        Player player = getPlayer();
        if (player == null || !player.isOnline()) {
            return false;
        }
        return true;
    }

    public void sendMessage(String msg) {
        Player player = getPlayer();
        if (player != null) {
            player.sendMessage(msg);
        }
    }

    public Location getLocation() {
        Player player = getPlayer();
        return player == null ? null : player.getLocation();
    }

    public void teleport(Location location) {
        Player player = getPlayer();
        if (player != null) {
            player.teleport(location);
        }
    }

    public World getWorld() {
        Player player = getPlayer();
        return player == null ? null : player.getWorld();
    }


    // ##################################################
    // ###################### MISC ######################
    // ##################################################

    public static EPlayer get(UUID uuid) {
        return EssenceCore.inst().getPM().getPlayer(uuid);
    }

    public static EPlayer get(String name) {
        return EssenceCore.inst().getPM().getPlayer(name);
    }

    public static EPlayer get(OfflinePlayer player) {
        return EssenceCore.inst().getPM().getPlayer(player);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EPlayer) {
            return ((EPlayer)obj).getUUID() == getUUID();
        }
        return false;
    }
}
