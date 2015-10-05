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

package org.essencemc.essencecore.nms;

import org.bukkit.entity.Player;

/**
 * Interface for handling actionbars and chat
 */
public interface IChat {

    /**
     * Send actionbar to the player.
     *
     * @param message The message to be sent to the player. It has to be a string in raw JSON format. You can use TextParser to build one if you want.
     * @param player The player the message has to be sent to. Note that the player has to be a {@link Player} object or else it wont work.
     *
     * @return IChat instance
     */
    IChat sendActionbar(String message, Player player);

    /**
     * Send actionbar to the players
     *
     * @param message The message to be sent to the player. It has to be a string in raw JSON format. You can use TextParser to build one if you want.
     * @param players The players the message has to be sent to. Note that the players have to be an array of {@link Player} object or else it wont work
     *
     * @return IChat instance
     */
    IChat sendActionbar(String message, Player[] players);

    /**
     * Send raw chat message to the player
     *
     * @param message The message to be sent to the player. It has to be a string in raw JSON format. You can use TextParser to build one if you want.
     * @param player The player the message has to be sent to. Note that the player has to be a {@link Player} object or else it wont work.
     *
     * @return IChat instance
     */
    IChat sendChat(String message, Player player);

    /**
     * Send raw chat message to the players
     *
     * @param message The message to be sent to the player. It has to be a string in raw JSON format. You can use TextParser to build one if you want.
     * @param players The players the message has to be sent to. Note that the players have to be an array of {@link Player} object or else it wont work
     *
     * @return IChat instance
     */
    IChat sendChat(String message, Player[] players);
}
