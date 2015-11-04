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

import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Builder for chat and actionbars
 */
public class Builder {

    private Chat chat;

    private String message = "";
    private byte chatLocation = ChatLocation.CHAT.location;

    public Builder(Chat chat) {
        this.chat = chat;
    }

    /**
     * Sets the location of the chat message ie in the chat or actionbar
     *
     * @param chatLocation Chat Location
     *
     * @return Builder instance
     */
    public Builder setChatLocation(ChatLocation chatLocation) {
        this.chatLocation = chatLocation.location;
        return this;
    }

    /**
     * @param message The message to be sent to the player.
     *                It has to be a string in raw JSON format.
     *                You can use TextParser to build one if you want.
     *
     * @return Builder instance
     */
    public Builder setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Send the chat message to the player
     *
     * @param player The player the message has to be sent to.
     *               Note that the player has to be a {@link Player} object or else it wont work.
     */
    public void send(Player player) {
        if (chatLocation == 2) {
            chat.sendActionbar(message, player);
        } else {
            chat.sendChat(message, player);
        }
    }

    /**
     * Send the chat message to the player
     *
     * @param players The player the message has to be sent to.
     *               Note that the player has to be an array of {@link Player} object or else it wont work.
     */
    public void send(Player[] players) {
        if (chatLocation == 2) {
            chat.sendActionbar(message, players);
        } else {
            chat.sendChat(message, players);
        }
    }

    /**
     * Send the chat message to the player
     *
     * @param players The player the message has to be sent to.
     *               Note that the player has to be a collection of {@link Player} object or else it wont work.
     */
    public void send(Collection<? extends Player> players) {
        if (chatLocation == 2) {
            chat.sendActionbar(message, players);
        } else {
            chat.sendChat(message, players);
        }
    }

    /**
     * Enum to define where the chat message will be located
     */
    public enum ChatLocation{
        CHAT((byte)0),
        ACTIONBAR((byte)2),;

        private final byte location;

        ChatLocation(byte location) {
            this.location = location;
        }
    }
}
