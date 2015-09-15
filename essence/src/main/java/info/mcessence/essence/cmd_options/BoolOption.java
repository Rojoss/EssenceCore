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

import info.mcessence.essence.message.EMessage;

public class BoolOption implements CommandOption {

    private EMessage info;
    private Boolean defaultValue;
    private Boolean value;

    public BoolOption(boolean defaultValue, EMessage info) {
        this.defaultValue = defaultValue;
        this.info = info;
    }

    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("1") || input.equalsIgnoreCase("v")) {
            return true;
        }
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
            return true;
        }
        return false;
    }

    public boolean setValue(String input) {
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("1") || input.equalsIgnoreCase("v")) {
            this.value = true;
            return true;
        }
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
            this.value = false;
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

    public EMessage getInfo() {
        return info;
    }
}
