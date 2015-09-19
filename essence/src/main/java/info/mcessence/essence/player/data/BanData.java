/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
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

package info.mcessence.essence.player.data;

import info.mcessence.essence.modules.ban.Ban;
import info.mcessence.essence.player.data.internal.PlayerData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanData extends PlayerData {

    List<Ban> bans = new ArrayList<Ban>();

    public boolean isBanned() {
        return getActiveBan() != null;
    }

    public boolean ban(Long duration, UUID punisher, String reason) {
        if (isBanned()) {
            return false;
        }
        bans.add(new Ban(new Timestamp(System.currentTimeMillis()), duration, punisher, reason));
        return true;
    }

    public boolean unban() {
        if (!isBanned()) {
            return false;
        }
        getActiveBan().setRemainingTime(0l);
        //TODO: Set it back?
        return true;
    }

    public List<Ban> getBans() {
        return bans;
    }

    public Ban getActiveBan() {
        //TODO: Get the time the user has been online.
        Long onlineTime = 0l;
        for (Ban ban : bans) {
            if (ban.isActive(onlineTime)) {
                return ban;
            }
        }
        return null;
    }
}
