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

package org.essencemc.essencecore.nms.packet.playout.respawn;

import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.nms.v1_8R3.util.PacketUtil;

import java.util.Collection;

/**
 * Handles the player respawning for v1_8R3
 */
public class Respawn_1_8_R3 implements Respawn {

    public Respawn_1_8_R3() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Respawn respawnPlayer(Player player) {

        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
        PacketUtil.sendPacket(player, respawnPacket);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Respawn respawnPlayers(Player[] players) {

        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
            PacketUtil.sendPacket(player, respawnPacket);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Respawn respawnPlayers(Collection<? extends Player> players) {
        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
            PacketUtil.sendPacket(player, respawnPacket);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Respawn changePlayerSky(World.Environment environment, Player player) {
        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
        PacketUtil.sendPacket(player, respawnPacket);

        PacketUtil.refreshPlayerChunks(10, player);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public Respawn changePlayerSky(World.Environment environment, int chunkRadius, Player player) {
        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
        PacketUtil.sendPacket(player, respawnPacket);

        PacketUtil.refreshPlayerChunks(chunkRadius, player);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Respawn changePlayerSky(World.Environment environment, Player[] players) {
        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
            PacketUtil.sendPacket(player, respawnPacket);

            PacketUtil.refreshPlayerChunks(10, player);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Respawn changePlayerSky(World.Environment environment, Collection<? extends Player> players) {
        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
            PacketUtil.sendPacket(player, respawnPacket);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public Respawn changePlayerSky(World.Environment environment, int chunkRadius, Player[] players) {
        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
            PacketUtil.sendPacket(player, respawnPacket);

            PacketUtil.refreshPlayerChunks(chunkRadius, player);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Respawn changePlayerSky(World.Environment environment, int chunkRadius, Collection<? extends Player> players) {
        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(PacketUtil.resolveEnvironment(player), PacketUtil.resolveDifficulty(player), PacketUtil.resolveWorldType(player), PacketUtil.resolveGameMode(player));
            PacketUtil.sendPacket(player, respawnPacket);
        }
        return this;
    }
}
