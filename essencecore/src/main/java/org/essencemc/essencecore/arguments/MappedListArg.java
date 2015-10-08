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
import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedListArg extends Argument {

    Map<String, List<String>> values = new HashMap<String, List<String>>();

    public MappedListArg(Map<String, List<String>> values) {
        this.values = values;
    }

    public MappedListArg(String defaultValue, Map<String, List<String>> values) {
        super(defaultValue);
        this.values = values;
    }

    public MappedListArg(String name, String defaultValue, Map<String, List<String>> values) {
        super(name, defaultValue);
        this.values = values;
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().parseArgs(name) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }

        List<String> allArgs = new ArrayList<String>();
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            for (String str : entry.getValue()) {
                if (str.equalsIgnoreCase(input)) {
                    value = entry.getKey();
                    return success;
                }
                allArgs.add(str);
            }
        }

        error = Message.INVALID_LIST_ARGUMENT.msg().parseArgs(input, Util.implode(allArgs, ","));
        success = false;
        return success;
    }

    @Override
     public MappedListArg clone() {
        return new MappedListArg(name, defaultValue == null ? null : (String)defaultValue, new HashMap<String, List<String>>(values));
    }

    @Override
    public EText getDescription() {
        return Message.ARG_LIST.msg().parseArgs(Util.implode(values.keySet(), ","));
    }

    @Override
    public Class getRawClass() {
        return String.class;
    }
}
