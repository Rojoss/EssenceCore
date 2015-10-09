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

package org.essencemc.essencecore.arguments;

import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.NumberUtil;

public class LongArg extends Argument {

    private Long min;
    private Long max;

    public LongArg() {
        super();
    }

    public LongArg(Long min, Long max) {
        super();
        this.min = min;
        this.max = max;
    }

    public LongArg(String name) {
        super(name);
    }

    public LongArg(String name, Long min, Long max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    public LongArg(Long defaultValue) {
        super(defaultValue);
    }

    public LongArg(Long defaultValue, Long min, Long max) {
        super(defaultValue);
        this.min = min;
        this.max = max;
    }

    public LongArg(String name, Long defaultValue) {
        super(name, defaultValue);
    }

    public LongArg(String name, Long defaultValue, Long min, Long max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().parseArgs(name) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }
        value = NumberUtil.getLong(input);
        if (value == null) {
            error = Message.NOT_A_NUMBER.msg().parseArgs(input);
            success = false;
            return success;
        }
        if (min != null && (Long)value < min) {
            error = Message.NUMBER_TOO_LOW.msg().parseArgs(input, Long.toString(min));
            success = false;
            return success;
        }
        if (max != null && (Long)value > max) {
            error = Message.NUMBER_TOO_LOW.msg().parseArgs(input, Long.toString(max));
            success = false;
            return success;
        }
        return success;
    }

    @Override
     public LongArg clone() {
        return new LongArg(name, defaultValue == null ? null : (Long)defaultValue);
    }

    @Override
    public EText getDescription() {
        if (min != null && max != null) {
            return Message.ARG_INT_MIN_MAX.msg().parseArgs(Long.toString(min), Long.toString(max));
        } else if (min != null) {
            return Message.ARG_INT_MIN.msg().parseArgs(Long.toString(min));
        } else if (max != null) {
            return Message.ARG_INT_MAX.msg().parseArgs(Long.toString(max));
        }
        return Message.ARG_INT.msg();
    }

    @Override
    public Class getRawClass() {
        return Long.class;
    }

    public static Long Parse(String input) {
        LongArg arg = new LongArg();
        if (arg.parse(input)) {
            return (Long)arg.value;
        }
        return null;
    }

    public static String Parse(Long input) {
        if (input == null) {
            return null;
        }
        return input.toString();
    }
}
