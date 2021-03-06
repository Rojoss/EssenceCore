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

import org.bukkit.material.MaterialData;
import org.essencemc.essencecore.aliases.Items;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.message.Param;

public class MaterialArg extends Argument {

    public MaterialArg() {
        super();
    }

    public MaterialArg(String name) {
        super(name);
    }

    public MaterialArg(MaterialData defaultValue) {
        super(defaultValue);
    }

    public MaterialArg(String name, MaterialData defaultValue) {
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

        value = Items.getMaterialData(input);
        if (value == null) {
            error = Message.NOT_A_MATERIAL.msg().params(Param.P("input", input));
            success = false;
            return success;
        }

        return success;
    }

    @Override
    public MaterialArg clone() {
        return new MaterialArg(name, defaultValue == null ? null : ((MaterialData)defaultValue).clone());
    }

    @Override
    public String toString() {
        return MaterialArg.Parse(value == null ? (defaultValue == null ? null : (MaterialData) defaultValue) : (MaterialData) value);
    }

    @Override
    public EText getDescription() {
        return Message.ARG_MATERIAL.msg();
    }

    @Override
    public Class getRawClass() {
        return MaterialData.class;
    }


    public static MaterialData Parse(String input) {
        MaterialArg arg = new MaterialArg();
        if (arg.parse(input)) {
            return (MaterialData)arg.value;
        }
        return null;
    }

    public static String Parse(MaterialData input) {
        if (input == null) {
            return null;
        }
        return Items.getItem(input.getItemType(), input.getData()).getName();
    }
}
