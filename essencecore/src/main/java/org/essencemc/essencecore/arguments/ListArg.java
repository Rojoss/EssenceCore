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

package org.essencemc.essencecore.arguments;

import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ListArg extends Argument {

    List<String> values = new ArrayList<String>();

    public ListArg(List<String> values) {
        this.values = values;
    }

    public ListArg(String defaultValue, List<String> values) {
        super(defaultValue);
        this.values = values;
    }

    public ListArg(String name, String defaultValue, List<String> values) {
        super(name, defaultValue);
        this.values = values;
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().getMsg(true, name) : Message.NO_ARG_VALUE.msg().getMsg(true);
            success = false;
            return success;
        }

        if (!values.contains(input.toLowerCase())) {
            error = Message.INVALID_LIST_ARGUMENT.msg().getMsg(true, input, Util.implode(values, ","));
            success = false;
            return success;
        }

        value = input;
        return success;
    }

    @Override
    public ListArg clone() {
        return new ListArg(name, defaultValue == null ? null : (String)defaultValue, new ArrayList<String>(values));
    }

    @Override
    public String getDescription() {
        return Message.ARG_LIST.msg().getMsg(false, Util.implode(values, ","));
    }

    @Override
    public Class getRawClass() {
        return String.class;
    }
}
