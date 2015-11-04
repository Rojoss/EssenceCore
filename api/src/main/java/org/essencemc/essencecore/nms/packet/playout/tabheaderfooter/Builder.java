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

package org.essencemc.essencecore.nms.packet.playout.tabheaderfooter;

import org.bukkit.entity.Player;

import java.util.Collection;

/**
 *
 */
public class Builder {

    private final TabHeaderFooter tabHeaderFooter;

    private String tabHeaderMessage = "";
    private String tabFooterMessage = "";

    private Builder(){
        this.tabHeaderFooter = null;
    }

    public Builder(TabHeaderFooter tabHeaderFooter) {
        this.tabHeaderFooter = tabHeaderFooter;
    }

    public Builder setTabHeaderMessage(String tabHeaderMessage) {
        this.tabHeaderMessage = tabHeaderMessage;
        return this;
    }

    public Builder setTabFooterMessage(String tabFooterMessage) {
        this.tabFooterMessage = tabFooterMessage;
        return this;
    }

    public void send(Player player) {
        if (!(tabHeaderMessage == "")) {
            tabHeaderFooter.sendTabHeader(tabHeaderMessage, player);
        }
        if (!(tabFooterMessage == "")) {
            tabHeaderFooter.sendTabFooter(tabFooterMessage, player);
        }
    }

    public void send(Player[] players) {
        if (!(tabHeaderMessage == "")) {
            tabHeaderFooter.sendTabHeader(tabHeaderMessage, players);
        }
        if (!(tabFooterMessage == "")) {
            tabHeaderFooter.sendTabFooter(tabFooterMessage, players);
        }
    }

    public void send(Collection<? extends Player> players) {
        if (!(tabHeaderMessage == "")) {
            tabHeaderFooter.sendTabHeader(tabHeaderMessage, players);
        }
        if (!(tabFooterMessage == "")) {
            tabHeaderFooter.sendTabFooter(tabFooterMessage, players);
        }
    }
}
