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

import java.util.Collection;

/**
 * Builder for titles and subtitles
 */
public class Builder {

    private Title title;

    private int titleFadeIn = 20;
    private int titleStay = 30;
    private int titleFadeOut = 20;

    private int subtitleFadeIn = 20;
    private int subtitleStay = 30;
    private int subtitleFadeOut = 20;

    private String titleMessage = "";
    private String subtitleMessage = "";

    private Builder() {
        this.title = null;
    }

    public Builder(Title title) {
        this.title = title;
    }

    public Builder setTimings(int fadeIn, int stay, int fadeOut) {
        titleFadeIn = fadeIn;
        titleStay = stay;
        titleFadeOut = fadeOut;

        subtitleFadeIn = fadeIn;
        subtitleStay = stay;
        subtitleFadeOut = fadeOut;

        return this;
    }

    public Builder setTitleTimings(int fadeIn, int stay, int fadeOut) {
        titleFadeIn = fadeIn;
        titleStay = stay;
        titleFadeOut = fadeOut;

        return this;
    }

    public Builder setSubtitleTimings(int fadeIn, int stay, int fadeOut) {

        subtitleFadeIn = fadeIn;
        subtitleStay = stay;
        subtitleFadeOut = fadeOut;

        return this;
    }

    public Builder setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
        return this;
    }

    public Builder setSubtitleMessage(String subtitleMessage) {
        this.subtitleMessage = subtitleMessage;
        return this;
    }

    public Builder send(Player player) {
        if (!titleMessage.equals("")) {
            title.sendTitle(titleMessage, titleFadeIn, titleStay, titleFadeOut, player);
        }

        if (!subtitleMessage.equals("")) {
            title.sendSubtitle(subtitleMessage, subtitleFadeIn, subtitleStay, subtitleFadeOut, player);
        }

        return this;
    }

    public Builder send(Player[] players) {
        if (!titleMessage.equals("")) {
            title.sendTitle(titleMessage, titleFadeIn, titleStay, titleFadeOut, players);
        }

        if (!subtitleMessage.equals("")) {
            title.sendSubtitle(subtitleMessage, subtitleFadeIn, subtitleStay, subtitleFadeOut, players);
        }

        return this;
    }

    public Builder send(Collection<? extends Player> players) {
        if (!titleMessage.equals("")) {
            title.sendTitle(titleMessage, titleFadeIn, titleStay, titleFadeOut, players);
        }

        if (!subtitleMessage.equals("")) {
            title.sendSubtitle(subtitleMessage, subtitleFadeIn, subtitleStay, subtitleFadeOut, players);
        }

        return this;
    }
}
