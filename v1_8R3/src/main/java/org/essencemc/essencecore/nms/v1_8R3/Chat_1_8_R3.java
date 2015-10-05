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

package org.essencemc.essencecore.nms.v1_8R3;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.nms.IChat;
import org.essencemc.essencecore.nms.v1_8R3.util.Util;

/**
 * Handles the chat and actionbars for v1_8R3
 */
public class Chat_1_8_R3 implements IChat {

    /**
     * Send actionbar to the player
     *
     * @param message
     * @param player
     */
    @Override
    public IChat sendActionbar(String message, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat actionbarPacket = new PacketPlayOutChat(icbc, (byte) 2);
        Util.sendPacket(player, actionbarPacket);

        return this;
    }

    /**
     * Send actionbar to the players
     *
     * @param message
     * @param players
     */
    @Override
    public IChat sendActionbar(String message, Player[] players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat actionbarPacket = new PacketPlayOutChat(icbc, (byte) 2);

        for (Player player : players) {
            Util.sendPacket(player, actionbarPacket);
        }

        return this;
    }

    /**
     * Send custom chat to the player
     *
     * @param message
     * @param player
     */
    @Override
    public IChat sendChat(String message, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat chatPacket = new PacketPlayOutChat(icbc, (byte) 0);
        Util.sendPacket(player, chatPacket);

        return this;
    }

    /**
     * Send chat to the players
     *
     * @param message
     * @param players
     */
    @Override
    public IChat sendChat(String message, Player[] players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat chatPacket = new PacketPlayOutChat(icbc, (byte) 0);

        for (Player p: players) {
            Util.sendPacket(p, chatPacket);
        }

        return this;
    }

}
