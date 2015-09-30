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

package org.essencemc.essencecore.api;

import org.bukkit.entity.Player;

/**
 * Interface for handling Titles and Subtitles
 */
public interface ITitle {

    /**
     * Send the player only the title message
     */
    void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players only the title message
     */
    void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player[] players);

    /**
     * Send all players only the title message
     */
    void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut);

    /**
     * Send the player only the subtitle message
     */
    void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players only the subtitle message
     */
    void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] player);

    /**
     * Send all players only the subtitle message
     */
    void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut);

    /**
     * Send the player the title and subtitle messages
     */
    void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players the title and subtitle messages
     */
    void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] player);

    /**
     * Send all players the title and subtitle messages
     */
    void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut);
}
