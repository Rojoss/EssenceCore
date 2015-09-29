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

package info.mcessence.essence.arguments;

import info.mcessence.essence.arguments.internal.Argument;
import info.mcessence.essence.entity.EItem;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.parsers.ItemParser;

public class ItemArg extends Argument {

    public ItemArg() {
        super();
    }

    public ItemArg(String name) {
        super(name);
    }

    public ItemArg(EItem defaultValue) {
        super(defaultValue);
    }

    public ItemArg(String name, EItem defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().getMsg(true, name) : Message.NO_ARG_VALUE.msg().getMsg(true);
            success = false;
            return success;
        }

        if (input.startsWith("\"") && input.endsWith("\"")) {
            input = input.substring(1, input.length()-1);
        }

        ItemParser parser = new ItemParser(input, false);
        if (!parser.isValid()) {
            error = parser.getError();
            success = false;
            return success;
        }

        value = parser.getItem();
        return success;
    }

    @Override
    public ItemArg clone() {
        return new ItemArg(name, defaultValue == null ? null : ((EItem)defaultValue).clone());
    }

    @Override
    public String getDescription() {
        return Message.ARG_ITEM.msg().getMsg(false);
    }

    @Override
    public Class getRawClass() {
        return EItem.class;
    }
}
