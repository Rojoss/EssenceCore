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

package info.mcessence.essence.modules.ban;

import java.sql.Timestamp;
import java.util.UUID;

public class Ban {

    private Timestamp timestamp;
    private Long duration;
    private UUID punisher;
    private String reason;

    private Long remainingTime;

    public Ban(Timestamp timestamp, Long duration, UUID punisher, String reason) {
        this.timestamp = timestamp;
        this.duration = duration;
        this.remainingTime = duration;
        this.punisher = punisher;
        this.reason = reason;
    }

    public boolean isActive(Long onlineTime) {
        if (getRemainingTime(onlineTime) <= 0) {
            return false;
        }
        return true;
    }

    public Long getRemainingTime(Long onlineTime) {
        return remainingTime - onlineTime;
    }

    public void setRemainingTime(Long remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Long getDuration() {
        return duration;
    }

    public UUID getPunisher() {
        return punisher;
    }

    public String getReason() {
        return reason;
    }

}
