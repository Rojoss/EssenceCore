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
import org.essencemc.essencecore.message.Param;
import org.essencemc.essencecore.parsers.ItemParser;

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
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().params(Param.P("arg", name)) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }

        if (input.startsWith("\"") && input.endsWith("\"")) {
            input = input.substring(1, input.length()-1);
        }

        ItemParser parser = new ItemParser(input, 1, false);
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
    public String toString() {
        return ItemArg.Parse(value == null ? (defaultValue == null ? null : (EItem)defaultValue) : (EItem)value);
    }

    @Override
    public EText getDescription() {
        return Message.ARG_ITEM.msg();
    }

    @Override
    public Class getRawClass() {
        return EItem.class;
    }

    public static EItem Parse(String input) {
        ItemArg arg = new ItemArg();
        if (arg.parse(input)) {
            return (EItem)arg.value;
        }
        return null;
    }

    public static String Parse(EItem input) {
        if (input == null) {
            return null;
        }
        return new ItemParser(input).getString();
    }
}
