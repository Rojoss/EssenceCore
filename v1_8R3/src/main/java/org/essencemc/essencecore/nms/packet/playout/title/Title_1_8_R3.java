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

package org.essencemc.essencecore.nms.packet.playout.title;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.nms.packet.playout.title.builder.Builder;
import org.essencemc.essencecore.nms.v1_8R3.util.Util;
import org.essencemc.essencecore.plugin.INMS_Fetcher;

import java.util.Collection;

/**
 * Handles the titles and subtitles for v1_8R3
 */
public class Title_1_8_R3 implements ITitle {


    private INMS_Fetcher inmsFetcher;

    public Title_1_8_R3(INMS_Fetcher inmsFetcher) {
        this.inmsFetcher = inmsFetcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITitle sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(titleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);
        Util.sendPacket(player, titlePacket);
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ITitle sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(titleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p: players) {
            Util.sendPacket(p, titlePacket);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITitle sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Collection<? extends Player> players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(titleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p: players) {
            Util.sendPacket(p, titlePacket);
        }
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ITitle sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);
        Util.sendPacket(player, subtitlePacket);
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ITitle sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p: players) {
            Util.sendPacket(p, subtitlePacket);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITitle sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Collection<? extends Player> players) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p: players) {
            Util.sendPacket(p, subtitlePacket);
        }
        return this;
    }

    @Override
    public Builder builder(INMS_Fetcher inmsFetcher) {
        return new Builder(inmsFetcher);
    }
}
