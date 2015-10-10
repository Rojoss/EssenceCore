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
import org.essencemc.essencecore.message.Param;
import org.essencemc.essencecore.util.NumberUtil;

public class FloatArg extends Argument {

    private Float min;
    private Float max;

    public FloatArg() {
        super();
    }

    public FloatArg(Float min, Float max) {
        super();
        this.min = min;
        this.max = max;
    }

    public FloatArg(String name) {
        super(name);
    }

    public FloatArg(String name, Float min, Float max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    public FloatArg(Float defaultValue) {
        super(defaultValue);
    }

    public FloatArg(Float defaultValue, Float min, Float max) {
        super(defaultValue);
        this.min = min;
        this.max = max;
    }

    public FloatArg(String name, Float defaultValue) {
        super(name, defaultValue);
    }

    public FloatArg(String name, Float defaultValue, Float min, Float max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().params(Param.P("arg", name)) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }
        value = NumberUtil.getFloat(input);
        if (value == null) {
            error = Message.NOT_A_DECIMAL.msg().params(Param.P("input", input));
            success = false;
            return success;
        }
        if (min != null && (Float)value < min) {
            error = Message.NUMBER_TOO_LOW.msg().params(Param.P("input", input), Param.P("min", Float.toString(min)));
            success = false;
            return success;
        }
        if (max != null && (Float)value > max) {
            error = Message.NUMBER_TOO_HIGH.msg().params(Param.P("input", input), Param.P("max", Float.toString(max)));
            success = false;
            return success;
        }
        return success;
    }

    @Override
    public FloatArg clone() {
        return new FloatArg(name, defaultValue == null ? null : (Float)defaultValue);
    }

    @Override
    public EText getDescription() {
        if (min != null && max != null) {
            return Message.ARG_DECIMAL_MIN_MAX.msg().params(Param.P("min", Float.toString(min)), Param.P("max", Float.toString(max)));
        } else if (min != null) {
            return Message.ARG_DECIMAL_MIN.msg().params(Param.P("min", Float.toString(min)));
        } else if (max != null) {
            return Message.ARG_DECIMAL_MAX.msg().params(Param.P("max", Float.toString(max)));
        }
        return Message.ARG_DECIMAL.msg();
    }

    @Override
    public Class getRawClass() {
        return Float.class;
    }

    public static Float Parse(String input) {
        FloatArg arg = new FloatArg();
        if (arg.parse(input)) {
            return (Float)arg.value;
        }
        return null;
    }

    public static String Parse(Float input) {
        if (input == null) {
            return null;
        }
        return input.toString();
    }
}
