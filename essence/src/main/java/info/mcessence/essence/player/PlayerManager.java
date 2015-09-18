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

package info.mcessence.essence.player;

import info.mcessence.essence.Essence;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private Essence ess;
    Map<UUID, EPlayer> players = new HashMap<UUID, EPlayer>();

    public PlayerManager(Essence ess) {
        this.ess = ess;
        populate();
    }

    private void populate() {
        //TODO: Fetch all users and data from database.
    }

    public EPlayer getPlayer(String name) {
        return getPlayer(ess.getServer().getOfflinePlayer(name));
    }

    public EPlayer getPlayer(OfflinePlayer player) {
        if (player == null) {
            return null;
        }
        return getPlayer(player.getUniqueId());
    }

    public EPlayer getPlayer(UUID uuid) {
        if (!players.containsKey(uuid)) {
            players.put(uuid, new EPlayer(uuid));
        }
        return players.get(uuid);
    }


    public boolean hasData(String name) {
        return hasData(ess.getServer().getOfflinePlayer(name));
    }

    public boolean hasData(OfflinePlayer player) {
        return hasData(player.getUniqueId());
    }

    public boolean hasData(UUID uuid) {
        return players.containsKey(uuid);
    }


    public List<EPlayer> getPlayers() {
        return new ArrayList<EPlayer>(players.values());
    }

    public List<EPlayer> getOnlinePlayers() {
        List<EPlayer> onlinePlayers = new ArrayList<EPlayer>();
        for (EPlayer player : players.values()) {
            if (player.isOnline()) {
                onlinePlayers.add(player);
            }
        }
        return onlinePlayers;
    }
}
