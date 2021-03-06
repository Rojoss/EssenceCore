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
import org.essencemc.essencecore.arguments.internal.MatchString;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.message.Param;

public class StringArg extends Argument {

    private Integer minChars;
    private Integer maxChars;
    public MatchString match;

    public StringArg() {
        super();
    }

    public StringArg(MatchString match) {
        super();
        this.match = match;
    }

    public StringArg(Integer minChars, Integer maxChars) {
        super();
        this.minChars = minChars;
        this.maxChars = maxChars;
    }

    public StringArg(String defaultValue) {
        super(null, defaultValue);
    }

    public StringArg(String defaultValue, MatchString match) {
        super(null, defaultValue);
        this.match = match;
    }

    public StringArg(String defaultValue, Integer minChars, Integer maxChars) {
        super(null, defaultValue);
        this.minChars = minChars;
        this.maxChars = maxChars;
    }

    public StringArg(String name, String defaultValue) {
        super(name, defaultValue);
    }

    public StringArg(String name, String defaultValue, MatchString match) {
        super(name, defaultValue);
        this.match = match;
    }

    public StringArg(String name, String defaultValue, String match) {
        super(name, defaultValue);
        this.match = new MatchString(match);
    }

    public StringArg(String name, String defaultValue, Integer minChars, Integer maxChars) {
        super(name, defaultValue);
        this.minChars = minChars;
        this.maxChars = maxChars;
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().params(Param.P("arg", name)) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }
        if (match != null && match.match != null && !match.match.isEmpty() && !input.equalsIgnoreCase(match.match)) {
            error = Message.NO_STRING_MATCH.msg().params(Param.P("input", input), Param.P("match", match.match));
            success = false;
            return success;
        }
        if (minChars != null && input.length() < minChars) {
            error = Message.TOO_FEW_CHARACTERS.msg().params(Param.P("input", input), Param.P("min", Integer.toString(minChars)));
            success = false;
            return success;
        }
        if (maxChars != null && input.length() > maxChars) {
            error = Message.TOO_MUCH_CHARACTERS.msg().params(Param.P("input", input), Param.P("max", Integer.toString(maxChars)));
            success = false;
            return success;
        }
        value = input;
        return success;
    }

    @Override
     public StringArg clone() {
        return new StringArg(name, defaultValue == null ? null : (String)defaultValue);
    }

    @Override
    public EText getDescription() {
        if (match != null && match.match != null && !match.match.isEmpty()) {
            return Message.ARG_STRING_MATCH.msg().params(Param.P("match", match.match));
        } else if (minChars != null && maxChars != null) {
            return Message.ARG_STRING_MIN_MAX.msg().params(Param.P("min", Integer.toString(minChars)), Param.P("max", Integer.toString(maxChars)));
        } else if (minChars != null) {
            return Message.ARG_STRING_MIN.msg().params(Param.P("min", Integer.toString(minChars)));
        } else if (maxChars != null) {
            return Message.ARG_STRING_MAX.msg().params(Param.P("max", Integer.toString(maxChars)));
        }
        return Message.ARG_STRING.msg();
    }

    @Override
    public Class getRawClass() {
        return String.class;
    }
}
