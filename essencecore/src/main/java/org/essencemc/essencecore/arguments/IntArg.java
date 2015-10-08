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

public class IntArg extends Argument {

    public IntArg() {
        super();
    }

    public IntArg(String name) {
        super(name);
    }

    public IntArg(Integer defaultValue) {
        super(defaultValue);
    }

    public IntArg(String name, Integer defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().parseArgs(name) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }
        value = NumberUtil.getInt(input);
        if (value == null) {
            error = Message.NOT_A_NUMBER.msg().parseArgs(input);
            success = false;
            return success;
        }
        return success;
    }

    @Override
     public IntArg clone() {
        return new IntArg(name, defaultValue == null ? null : (Integer)defaultValue);
    }

    @Override
    public EText getDescription() {
        return Message.ARG_INT.msg();
    }

    @Override
    public Class getRawClass() {
        return Integer.class;
    }

    public static Integer Parse(String input) {
        IntArg arg = new IntArg();
        if (arg.parse(input)) {
            return (Integer)arg.value;
        }
        return null;
    }

    public static String Parse(Integer input) {
        if (input == null) {
            return null;
        }
        return input.toString();
    }
}
