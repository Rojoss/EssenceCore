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

package org.essencemc.essencecore.nms.packet.playout.chat;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.nms.v1_8R3.util.PacketUtil;
import org.essencemc.essencecore.plugin.NMSFetcher;

import java.util.Collection;

/**
 * Handles the chat and actionbars for v1_8R3
 */
public class Chat_1_8_R3 implements Chat {

    private final NMSFetcher nmsFetcher;

    public Chat_1_8_R3(NMSFetcher nmsFetcher) {
        this.nmsFetcher = nmsFetcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chat sendActionbar(String message, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat actionbarPacket = new PacketPlayOutChat(icbc, (byte) 2);
        PacketUtil.sendPacket(player, actionbarPacket);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chat sendActionbar(String message, Player[] players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat actionbarPacket = new PacketPlayOutChat(icbc, (byte) 2);

        for (Player player : players) {
            PacketUtil.sendPacket(player, actionbarPacket);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chat sendActionbar(String message, Collection<? extends Player> players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat actionbarPacket = new PacketPlayOutChat(icbc, (byte) 2);

        for (Player player : players) {
            PacketUtil.sendPacket(player, actionbarPacket);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chat sendChat(String message, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat chatPacket = new PacketPlayOutChat(icbc, (byte) 0);
        PacketUtil.sendPacket(player, chatPacket);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chat sendChat(String message, Player[] players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat chatPacket = new PacketPlayOutChat(icbc, (byte) 0);

        for (Player p: players) {
            PacketUtil.sendPacket(p, chatPacket);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Chat sendChat(String message, Collection<? extends Player> players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat chatPacket = new PacketPlayOutChat(icbc, (byte) 0);

        for (Player p: players) {
            PacketUtil.sendPacket(p, chatPacket);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Builder builder(Chat chat) {
        return new Builder(this);
    }

}
