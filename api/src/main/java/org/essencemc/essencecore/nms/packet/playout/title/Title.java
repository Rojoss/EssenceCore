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

import org.bukkit.entity.Player;
import org.essencemc.essencecore.plugin.INMS_Fetcher;
import org.essencemc.essencecore.nms.packet.playout.title.builder.Builder;

import java.util.Collection;

/**
 * Interface for handling Titles and Subtitles
 */
public interface Title {

    /**
     * Send the player only the title message
     *
     * @param titleMessage The message to be sent to the player.
     *                     It has to be a string in raw JSON format.
     *                     You can use TextParser to build one if you want.
     * @param fadeIn Fade in time for the title message in ticks.
     * @param stay Time in ticks the message stays floating on the screen
     * @param fadeOut Fade in time for the title message in ticks.
     * @param player The player the message has to be sent to.
     *               Note that the player has to be a {@link Player} object or else it wont work.
     *
     * @return Title instance
     */
    Title sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players only the title message
     *
     * @param titleMessage The message to be sent to the player.
     *                     It has to be a string in raw JSON format.
     *                     You can use TextParser to build one if you want.
     * @param fadeIn Fade in time for the title message in ticks.
     * @param stay Time in ticks the message stays floating on the screen
     * @param fadeOut Fade in time for the title message in ticks.
     * @param players The players the message has to be sent to.
     *                Note that the players have to be an array of {@link Player} object or else it wont work
     *
     * @return Title instance
     */
    Title sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player[] players);

    /**
     * Send the players only the title message
     *
     * @param titleMessage The message to be sent to the player.
     *                     It has to be a string in raw JSON format.
     *                     You can use TextParser to build one if you want.
     * @param fadeIn Fade in time for the title message in ticks.
     * @param stay Time in ticks the message stays floating on the screen
     * @param fadeOut Fade in time for the title message in ticks.
     * @param players The players the message has to be sent to.
     *                Note that the players have to be a collection of {@link Player} object or else it wont work
     *
     * @return Title instance
     */
    Title sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Collection<? extends Player> players);


    /**
     * Send the player only the subtitle message
     *
     * @param subtitleMessage The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param fadeIn Fade in time for the title message in ticks.
     * @param stay Time in ticks the message stays floating on the screen
     * @param fadeOut Fade in time for the title message in ticks.
     * @param player The player the message has to be sent to.
     *               Note that the player has to be a {@link Player} object or else it wont work.
     *
     * @return Title instance
     */
    Title sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players only subtitle message
     *
     * @param subtitleMessage The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param fadeIn Fade in time for the title message in ticks.
     * @param stay Time in ticks the message stays floating on the screen
     * @param fadeOut Fade in time for the title message in ticks.
     * @param players The players the message has to be sent to.
     *                Note that the players have to be an array of {@link Player} object or else it wont work
     *
     * @return Title instance
     */
    Title sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] players);

    /**
     * Send the players only subtitle message
     *
     * @param subtitleMessage The message to be sent to the player.
     *                        It has to be a string in raw JSON format.
     *                        You can use TextParser to build one if you want.
     * @param fadeIn Fade in time for the title message in ticks.
     * @param stay Time in ticks the message stays floating on the screen
     * @param fadeOut Fade in time for the title message in ticks.
     * @param players The players the message has to be sent to.
     *                Note that the players have to be a collection of {@link Player} object or else it wont work
     *
     * @return Title instance
     */
    Title sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Collection<? extends Player> players);

    Builder builder(INMS_Fetcher inmsFetcher);
}
