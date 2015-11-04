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

package org.essencemc.essencecore.nms.packet.playout.tabheaderfooter;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.nms.v1_8R3.util.PacketUtil;

import java.util.Collection;

/**
 *
 */
public class TabHeaderFooter_1_8_R3 implements TabHeaderFooter {

    public TabHeaderFooter_1_8_R3() {}


    @Override
    public TabHeaderFooter sendTabHeader(String tabHeaderMessage, Player player) {
        IChatBaseComponent icbc = PacketUtil.serializeChat(tabHeaderMessage);

        PacketUtil.sendPacket(player,new PacketPlayOutPlayerListHeaderFooter(icbc));
        return this;
    }

    @Override
    public TabHeaderFooter sendTabHeader(String tabHeaderMessage, Player[] players) {
        IChatBaseComponent icbc = PacketUtil.serializeChat(tabHeaderMessage);

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(icbc);

        for (Player player: players) {
            PacketUtil.sendPacket(player, packet);
        }

        return this;
    }

    @Override
    public TabHeaderFooter sendTabHeader(String tabHeaderMessage, Collection<? extends Player> players) {
        IChatBaseComponent icbc = PacketUtil.serializeChat(tabHeaderMessage);

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(icbc);

        for (Player player: players) {
            PacketUtil.sendPacket(player, packet);
        }

        return this;
    }

    @Override
    public TabHeaderFooter sendTabFooter(String tabFooterMessage, Player player) {
        IChatBaseComponent icbc = PacketUtil.serializeChat(tabFooterMessage);

        PacketUtil.sendPacket(player,new PacketPlayOutPlayerListHeaderFooter(icbc));
        return this;
    }

    @Override
    public TabHeaderFooter sendTabFooter(String tabFooterMessage, Player[] players) {
        IChatBaseComponent icbc = PacketUtil.serializeChat(tabFooterMessage);

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(icbc);

        for (Player player: players) {
            PacketUtil.sendPacket(player, packet);
        }

        return this;
    }

    @Override
    public TabHeaderFooter sendTabFooter(String tabFooterMessage, Collection<? extends Player> players) {
        IChatBaseComponent icbc = PacketUtil.serializeChat(tabFooterMessage);

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(icbc);

        for (Player player: players) {
            PacketUtil.sendPacket(player, packet);
        }

        return this;
    }

    @Override
    public Builder builder(TabHeaderFooter tabHeaderFooter) {
        return new Builder(this);
    }
}
