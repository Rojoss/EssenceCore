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
import net.minecraft.server.v1_8_R3.WorldType;
import org.bukkit.*;
import org.bukkit.World;
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

    public static EnumDifficulty resolveDifficulty(Difficulty difficulty) {
        EnumDifficulty enumDifficulty;

        switch (difficulty) {
            case PEACEFUL:
                enumDifficulty = EnumDifficulty.PEACEFUL;
                break;
            case EASY:
                enumDifficulty = EnumDifficulty.EASY;
                break;
            case NORMAL:
                enumDifficulty = EnumDifficulty.NORMAL;
                break;
            case HARD:
                enumDifficulty = EnumDifficulty.HARD;
                break;
            default:
                enumDifficulty = EnumDifficulty.NORMAL;
        }
        return enumDifficulty;
    }

    public static WorldType resolveWorldType(org.bukkit.WorldType worldType) {
        WorldType enumWorldType;

        switch (worldType) {
            case NORMAL:
                enumWorldType = WorldType.NORMAL;
                break;
            case FLAT:
                enumWorldType = WorldType.FLAT;
                break;
            case VERSION_1_1:
                enumWorldType = WorldType.NORMAL_1_1;
                break;
            case LARGE_BIOMES:
                enumWorldType = WorldType.LARGE_BIOMES;
                break;
            case AMPLIFIED:
                enumWorldType = WorldType.AMPLIFIED;
                break;
            case CUSTOMIZED:
                enumWorldType = WorldType.CUSTOMIZED;
                break;
            default:
                enumWorldType = WorldType.NORMAL;
                break;
        }
        return enumWorldType;
    }

    public static WorldSettings.EnumGamemode resolveGameMode(GameMode gameMode) {
        WorldSettings.EnumGamemode enumGamemode;

        switch (gameMode) {
            case SURVIVAL:
                enumGamemode = WorldSettings.EnumGamemode.SURVIVAL;
                break;
            case CREATIVE:
                enumGamemode = WorldSettings.EnumGamemode.CREATIVE;
                break;
            case ADVENTURE:
                enumGamemode = WorldSettings.EnumGamemode.ADVENTURE;
                break;
            case SPECTATOR:
                enumGamemode = WorldSettings.EnumGamemode.SPECTATOR;
                break;
            default:
                enumGamemode = WorldSettings.EnumGamemode.SURVIVAL;
        }

        return enumGamemode;
    }

    public static int resolveEnvironment(World.Environment environment) {
        int value;

        switch (environment) {
            case NETHER:
                value = -1;
                break;
            case NORMAL:
                value = 0;
                break;
            case THE_END:
                value = 1;
                break;
            default:
                value = 0;
        }
        return value;
    }
}
