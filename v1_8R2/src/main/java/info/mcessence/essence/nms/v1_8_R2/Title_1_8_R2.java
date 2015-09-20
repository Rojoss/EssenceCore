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

package info.mcessence.essence.nms.v1_8_R2;

import info.mcessence.essence.nms.ITitle;
import info.mcessence.essence.nms.v1_8_R2.util.PacketHandler;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Handles the titles and subtitles for v1_8R2
 */
public class Title_1_8_R2 implements ITitle {
    // TODO Make a ChatBuilder to allow for more flexible building

    // TODO Implement the ChatBuilder

    /**
     * Send the player only the title message
     *
     * @param titleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param player
     */
    @Override
    public void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent icbc = ChatSerializer.a(titleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);
        PacketHandler.sendPacket(player, titlePacket);
    }

    /**
     * Send the players only the title message
     *
     * @param titleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param players
     */
    @Override
    public void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        IChatBaseComponent icbc = ChatSerializer.a(titleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p: players) {
            PacketHandler.sendPacket(p, titlePacket);
        }
    }

    /**
     * Send all players only the title message
     *
     * @param titleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     */
    @Override
    public void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut) {
        IChatBaseComponent icbc = ChatSerializer.a(titleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            PacketHandler.sendPacket(p, titlePacket);
        }
    }

    /**
     * Send the player only the subtitle message
     *
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param player
     */
    @Override
    public void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent icbc = ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);
        PacketHandler.sendPacket(player, subtitlePacket);
    }

    /**
     * Send the players only the subtitle message
     *
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param players
     */
    @Override
    public void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        IChatBaseComponent icbc = ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p: players) {
            PacketHandler.sendPacket(p, subtitlePacket);
        }
    }

    /**
     * Send all players only the subtitle message
     *
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     */
    @Override
    public void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut) {
        IChatBaseComponent icbc = ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            PacketHandler.sendPacket(p, subtitlePacket);
        }
    }


    /**
     * Send the player the title and subtitle messages
     *
     * @param titleMessage
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param player
     */
    @Override
    public void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent titleIcbc = ChatSerializer.a(titleMessage);
        IChatBaseComponent subtitleIcbc = ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleIcbc, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleIcbc, fadeIn, stay, fadeOut);
        PacketHandler.sendPacket(player, titlePacket, subtitlePacket);
    }

    /**
     * Send the players the title and subtitle messages
     *
     * @param titleMessage
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param players
     */
    @Override
    public void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        IChatBaseComponent titleIcbc = ChatSerializer.a(titleMessage);
        IChatBaseComponent subtitleIcbc = ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleIcbc, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleIcbc, fadeIn, stay, fadeOut);

        for (Player p : players) {
            PacketHandler.sendPacket(p, titlePacket, subtitlePacket);
            PacketHandler.sendPacket(p, titlePacket, subtitlePacket);
        }
    }

    /**
     * Send all players the title and subtitle messages
     *
     * @param titleMessage
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     */
    @Override
    public void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut) {
        IChatBaseComponent titleIcbc = ChatSerializer.a(titleMessage);
        IChatBaseComponent subtitleIcbc = ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleIcbc, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleIcbc, fadeIn, stay, fadeOut);

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            PacketHandler.sendPacket(p, titlePacket, subtitlePacket);
        }
    }
}
