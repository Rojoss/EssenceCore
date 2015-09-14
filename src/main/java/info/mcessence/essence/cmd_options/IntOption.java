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

package info.mcessence.essence.cmd_options;

import info.mcessence.essence.Message;
import info.mcessence.essence.util.NumberUtil;

public class IntOption implements CommandOption {

    private Message info;
    private Integer defaultValue;
    private Integer value;

    public IntOption(int defaultValue, Message info) {
        this.defaultValue = defaultValue;
        this.info = info;
    }

    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        if (NumberUtil.getInt(input) == null) {
            return false;
        }
        return true;
    }

    public boolean setValue(String input) {
        if (NumberUtil.getInt(input) != null) {
            this.value = NumberUtil.getInt(input);
            return true;
        }
        return false;
    }

    public Object getValue() {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Message getInfo() {
        return info;
    }
}
