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
import org.essencemc.essencecore.nms.v1_8R3.util.Util;

/**
 * Handles the player respawning for v1_8R3
 */
public class Respawn_1_8_R3 implements IRespawn {
    /**
     * Respawn a player
     *
     * @param player The player that has to be respawned.
     *               Note that the player has to be a {@link Player} object or else it wont work.
     * @return IRespawn instance
     */
    @Override
    public IRespawn respawnPlayer(Player player) {

        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(Util.resolveEnvironment(player), Util.resolveDifficulty(player), Util.resolveWorldType(player), Util.resolveGameMode(player));
        Util.sendPacket(player, respawnPacket);
        return this;
    }

    /**
     * Respawn multiple players
     *
     * @param players The players that have to be respawned.
     *                Note that the players have to be an array of {@link Player} object or else it wont work.
     * @return IRespawn instance
     */
    @Override
    public IRespawn respawnPlayers(Player[] players) {

        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(Util.resolveEnvironment(player), Util.resolveDifficulty(player), Util.resolveWorldType(player), Util.resolveGameMode(player));
            Util.sendPacket(player, respawnPacket);
        }
        return this;
    }

    /**
     * Change player sky type for a player
     *
     * @param environment The dimension for the sky type
     * @param player The player that is going to have the sky type changed.
     *               Note that the player has to be a {@link Player} object or else it wont work.
     * @return IRespawn instance
     */
    @Override
    public IRespawn changePlayerSky(World.Environment environment, Player player) {
        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(environment.ordinal(), Util.resolveDifficulty(player), Util.resolveWorldType(player), Util.resolveGameMode(player));
        Util.sendPacket(player, respawnPacket);

        Util.refreshPlayerChunks(10, player);
        return this;
    }

    /**
     * Change player sky type for a player
     *
     * @param environment The dimension for the sky type
     * @param chunkRadius The number of chunks that will be refreshed to apply the sky.
     * @param player The player that is going to have the sky type changed.
     *               Note that the player has to be a {@link Player} object or else it wont work.
     * @return IRespawn instance
     */
    public IRespawn changePlayerSky(World.Environment environment, int chunkRadius, Player player) {
        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(environment.ordinal(), Util.resolveDifficulty(player), Util.resolveWorldType(player), Util.resolveGameMode(player));
        Util.sendPacket(player, respawnPacket);

        Util.refreshPlayerChunks(chunkRadius, player);
        return this;
    }

    /**
     * Change skies for multiple players
     *
     * @param environment The dimension for the sky type
     * @param players The players that are going to have the sky type changed.
     *                Note that the players have to be an array of {@link Player} object or else it wont work.
     * @return IRespawn instance
     */
    @Override
    public IRespawn changePlayerSky(World.Environment environment, Player[] players) {
        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(environment.ordinal(), Util.resolveDifficulty(player), Util.resolveWorldType(player), Util.resolveGameMode(player));
            Util.sendPacket(player, respawnPacket);

            Util.refreshPlayerChunks(10, player);
        }
        return this;
    }

    /**
     * Change skies for multiple players
     *
     * @param environment The dimension for the sky type
     * @param chunkRadius The number of chunks that will be refreshed to apply the sky.
     * @param players The players that are going to have the sky type changed.
     *                Note that the players have to be an array of {@link Player} object or else it wont work.
     * @return IRespawn instance
     */
    public IRespawn changePlayerSky(World.Environment environment, int chunkRadius, Player[] players) {
        for (Player player: players) {
            PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(environment.ordinal(), Util.resolveDifficulty(player), Util.resolveWorldType(player), Util.resolveGameMode(player));
            Util.sendPacket(player, respawnPacket);

            Util.refreshPlayerChunks(chunkRadius, player);
        }
        return this;
    }
}