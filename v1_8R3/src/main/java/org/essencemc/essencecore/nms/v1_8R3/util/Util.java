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

package org.essencemc.essencecore.nms.v1_8R3.util;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Any utility methods
 */
public class Util {

    public static void sendPacket(Player player, Packet packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendPacket(Player player, Packet packet1, Packet packet2) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
    }

    public static IChatBaseComponent serializeChat(String message) {
        return IChatBaseComponent.ChatSerializer.a(message);
    }

    public static EnumDifficulty resolveDifficulty(Player player) {
        return EnumDifficulty.getById(player.getWorld().getDifficulty().ordinal());
    }

    public static WorldType resolveWorldType(Player player) {
        return WorldType.getType(player.getWorld().getWorldType().toString());
    }

    public static WorldSettings.EnumGamemode resolveGameMode(Player player) {
        return WorldSettings.EnumGamemode.getById(player.getGameMode().ordinal());
    }

    public static int resolveEnvironment(Player player) {
        return player.getWorld().getEnvironment().getId();
    }

    public static void refreshPlayerChunks(int radius, Player player) {
        int r = Math.abs(radius);
        org.bukkit.Chunk chunk = player.getWorld().getChunkAt(player.getLocation());

        for(int x = -r; x < r; ++x) {
            for(int z = -r; z < r; ++z) {
                player.getWorld().refreshChunk(chunk.getX() + x, chunk.getZ() + z);
            }
        }
    }
}
