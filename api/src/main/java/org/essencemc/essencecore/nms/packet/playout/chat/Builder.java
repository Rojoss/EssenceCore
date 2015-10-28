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
import org.essencemc.essencecore.plugin.NMSFetcher;

import java.util.Collection;

/**
 * Chat Builder
 */
public class Builder {

    private final NMSFetcher nmsFetcher;
    private byte location = ChatLocation.CHAT.getLocationId();
    private String message = "";

     // We need a ref to EssenceCore class so cannot use no parameter contructor
    private Builder() {
        nmsFetcher = null;
    }

    public Builder(NMSFetcher nmsFetcher) {
        this.nmsFetcher = nmsFetcher;
    }

    /**
     * Set the location of chat message
     *
     * @param location Location of the chat message.
     *
     * @return Builder instance
     */
    public Builder setChatLocation(ChatLocation location) {
        this.location = location.getLocationId();
        return this;
    }

    /**
     * Set the chat message
     *
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
     * Send the chat message to a single player
     *
     * @param player The player the message has to be sent to.
     *               Note that the player has to be a {@link Player} object or else it wont work.
     *
     * @return Builder instance
     */
    public Builder send(Player player) {
        switch (location) {
            case 2:
                nmsFetcher.getChat().sendActionbar(message, player);
                break;
            default:
                nmsFetcher.getChat().sendChat(message, player);
        }
        return this;
    }

    /**
     * Send the chat message to players.
     *
     * @param players The players the message has to be sent to.
     *                Note that the players have to be an array of {@link Player} object or else it wont work
     *
     * @return Builder instance
     */
    public Builder send(Player[] players) {
        switch (location) {
            case 2:
                nmsFetcher.getChat().sendActionbar(message, players);
                break;
            default:
                nmsFetcher.getChat().sendChat(message, players);
        }
        return this;
    }

    /**
     * Send the chat message to players.
     *
     * @param players The players the message has to be sent to.
     *                Note that the players have to be a collection of {@link Player} object or else it wont work
     *
     * @return Builder instance
     */
    public Builder send(Collection<Player> players) {
        switch (location) {
            case 2:
                nmsFetcher.getChat().sendActionbar(message, players);
                break;
            default:
                nmsFetcher.getChat().sendChat(message, players);
        }
        return this;
    }

    /**
     * Defines the location a chat message can be sent to
     */
    public enum ChatLocation {
        CHAT((byte) 0),
        ACTIONBAR((byte) 2),;

        private final byte locationId;

        ChatLocation(byte locationId) {
            this.locationId = locationId;
        }

        public byte getLocationId() {
            return locationId;
        }
    }
}
