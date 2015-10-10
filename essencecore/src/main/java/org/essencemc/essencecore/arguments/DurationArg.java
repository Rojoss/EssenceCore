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
import org.essencemc.essencecore.entity.EItem;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.parsers.ItemParser;
import org.essencemc.essencecore.util.Duration;

public class DurationArg extends Argument {

    public DurationArg() {
        super();
    }

    public DurationArg(String name) {
        super(name);
    }

    public DurationArg(Duration defaultValue) {
        super(defaultValue);
    }

    public DurationArg(String name, Duration defaultValue) {
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

        Duration dur = new Duration(input);
        if (!dur.isValid()) {
            error = dur.getError();
            success = false;
            return success;
        }

        value = dur;
        return success;
    }

    @Override
    public DurationArg clone() {
        return new DurationArg(name, defaultValue == null ? null : ((Duration)defaultValue));
    }

    @Override
    public String toString() {
        return DurationArg.Parse(value == null ? (defaultValue == null ? null : (Duration) defaultValue) : (Duration) value);
    }

    @Override
    public EText getDescription() {
        return Message.ARG_DURATION.msg();
    }

    @Override
    public Class getRawClass() {
        return Duration.class;
    }

    public static Duration Parse(String input) {
        DurationArg arg = new DurationArg();
        if (arg.parse(input)) {
            return (Duration)arg.value;
        }
        return null;
    }

    public static String Parse(Duration input) {
        if (input == null) {
            return null;
        }
        return input.getString();
    }
}
