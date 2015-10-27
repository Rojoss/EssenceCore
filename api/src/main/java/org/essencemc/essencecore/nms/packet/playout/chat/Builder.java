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
import org.essencemc.essencecore.plugin.INMS_Fetcher;

import java.util.Collection;

/**
 * Chat Builder
 */
public class Builder {

    private final INMS_Fetcher inmsFetcher;
    private byte location = ChatLocation.CHAT.getLocationId();
    private String message = "";

    private Builder() {
        inmsFetcher = null;
    }

    public Builder(INMS_Fetcher inmsFetcher) {
        this.inmsFetcher = inmsFetcher;
    }

    public Builder setChatLocation(ChatLocation location) {
        this.location = location.getLocationId();
        return this;
    }

    public Builder setMessage(String message) {
        this.message = message;
        return this;
    }

    public void send(Player player) {
        switch (location) {
            case 2:
                inmsFetcher.getChat().sendActionbar(message, player);
                break;
            default:
                inmsFetcher.getChat().sendChat(message, player);
        }
    }

    public void send(Player[] players) {
        switch (location) {
            case 2:
                inmsFetcher.getChat().sendActionbar(message, players);
                break;
            default:
                inmsFetcher.getChat().sendChat(message, players);
        }
    }

    public void send(Collection<Player> players) {
        switch (location) {
            case 2:
                inmsFetcher.getChat().sendActionbar(message, players);
                break;
            default:
                inmsFetcher.getChat().sendChat(message, players);
        }
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
