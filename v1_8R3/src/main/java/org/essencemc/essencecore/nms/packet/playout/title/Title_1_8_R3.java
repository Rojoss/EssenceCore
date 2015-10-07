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
import org.essencemc.essencecore.nms.packet.playout.title.ITitle;
import org.essencemc.essencecore.nms.v1_8R3.util.Util;

/**
 * Handles the titles and subtitles for v1_8R3
 */
public class Title_1_8_R3 implements ITitle {


    /**
     * @param titleMessage The message to be sent to the player.
     *                     It has to be a string in raw JSON format.
     *                     You can use TextParser to build one if you want.
     * @param fadeIn       Fade in time for the title message in ticks.
     * @param stay         Time in ticks the message stays floating on the screen
     * @param fadeOut      Fade in time for the title message in ticks.
     * @param player       The player the message has to be sent to.
     *                     Note that the player has to be a {@link Player} object or else it wont work.
     * @return ITitle instance
     */
    @Override
    public ITitle sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(titleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);
        Util.sendPacket(player, titlePacket);
        return this;
    }


    /**
     * @param titleMessage The message to be sent to the player.
     *                     It has to be a string in raw JSON format.
     *                     You can use TextParser to build one if you want.
     * @param fadeIn       Fade in time for the title message in ticks.
     * @param stay         Time in ticks the message stays floating on the screen
     * @param fadeOut      Fade in time for the title message in ticks.
     * @param players      The players the message has to be sent to.
     *                     Note that the players have to be an array of {@link Player} object or else it wont work
     * @return ITitle instance
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
     * @param subtitleMessage The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param fadeIn          Fade in time for the title message in ticks.
     * @param stay            Time in ticks the message stays floating on the screen
     * @param fadeOut         Fade in time for the title message in ticks.
     * @param player          The player the message has to be sent to.
     *                        Note that the player has to be a {@link Player} object or else it wont work.
     * @return ITitle instance
     */
    @Override
    public ITitle sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);
        Util.sendPacket(player, subtitlePacket);
        return this;
    }


    /**
     * @param subtitleMessage The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param fadeIn          Fade in time for the title message in ticks.
     * @param stay            Time in ticks the message stays floating on the screen
     * @param fadeOut         Fade in time for the title message in ticks.
     * @param players         The players the message has to be sent to.
     *                        Note that the players have to be an array of {@link Player} object or else it wont work
     * @return ITitle instance
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
     * @param titleMessage    The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param subtitleMessage The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param fadeIn          Fade in time for the title message in ticks.
     * @param stay            Time in ticks the message stays floating on the screen
     * @param fadeOut         Fade in time for the title message in ticks.
     * @param player          The player the message has to be sent to.
     *                        Note that the player has to be a {@link Player} object or else it wont work.
     * @return ITitle instance
     */
    @Override
    public ITitle sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent titleIcbc = IChatBaseComponent.ChatSerializer.a(titleMessage);
        IChatBaseComponent subtitleIcbc = IChatBaseComponent.ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleIcbc, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleIcbc, fadeIn, stay, fadeOut);
        Util.sendPacket(player, titlePacket, subtitlePacket);
        return this;
    }

    /**
     * @param titleMessage    The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param subtitleMessage The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param fadeIn          Fade in time for the title message in ticks.
     * @param stay            Time in ticks the message stays floating on the screen
     * @param fadeOut         Fade in time for the title message in ticks.
     * @param players         The players the message has to be sent to.
     *                        Note that the players have to be an array of {@link Player} object or else it wont work
     * @return ITitle instance
     */
    @Override
    public ITitle sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        IChatBaseComponent titleIcbc = IChatBaseComponent.ChatSerializer.a(titleMessage);
        IChatBaseComponent subtitleIcbc = IChatBaseComponent.ChatSerializer.a(subtitleMessage);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleIcbc, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleIcbc, fadeIn, stay, fadeOut);

        for (Player p : players) {
            Util.sendPacket(p, titlePacket, subtitlePacket);
            Util.sendPacket(p, titlePacket, subtitlePacket);
        }
        return this;
    }

}
