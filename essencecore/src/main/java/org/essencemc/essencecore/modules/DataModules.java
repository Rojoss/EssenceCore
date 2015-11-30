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

package org.essencemc.essencecore.modules;

public enum DataModules {
    USER("userlookup", 30),
    DATA_SYNC("datasync", 10),
    INV_SYNC("invsync", 10),
    ENDER_SYNC("endersync", 10),
    BACKPACK("backpacks", 10),
    NICK("nick", 30),
    HOME("homes", 20),
    GOD("god", 20),
    VANISH("vanish", 10),
    POWERTOOL("powertools", 60),
    BAN("ban", 0),
    MUTE("mute", 0),
    KICK("kick", 0),
    JAIL("jail", 0),
    FREEZE("freeze", 0),
    WARNING("warning", 10),
    SHOP_ITEMS("shopitems", 0),
    ;

    private String module;
    private int saveDelay;

    DataModules(String module, int saveDelay) {
        this.module = module;
        this.saveDelay = saveDelay;
    }

    public String getModule() {
        return module;
    }

    public int getSaveDelay() {
        return saveDelay;
    }
}
