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

import org.bukkit.util.Vector;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.message.Param;
import org.essencemc.essencecore.util.NumberUtil;

public class VectorArg extends Argument {

    public VectorArg() {
        super();
    }

    public VectorArg(String name) {
        super(name);
    }

    public VectorArg(Vector defaultValue) {
        super(defaultValue);
    }

    public VectorArg(String name, Vector defaultValue) {
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

        String[] split = input.split(",");
        if (split.length < 3) {
            error = Message.NOT_A_VECTOR.msg().params(Param.P("input", input));
            success = false;
            return success;
        }

        Float x = NumberUtil.getFloat(split[0]);
        if (x == null) {
            error = Message.NOT_A_DECIMAL.msg().params(Param.P("input", split[0]));
            success = false;
            return success;
        }

        Float y = NumberUtil.getFloat(split[1]);
        if (y == null) {
            error = Message.NOT_A_DECIMAL.msg().params(Param.P("input", split[1]));
            success = false;
            return success;
        }

        Float z = NumberUtil.getFloat(split[2]);
        if (z == null) {
            error = Message.NOT_A_DECIMAL.msg().params(Param.P("input", split[2]));
            success = false;
            return success;
        }

        value = new Vector(x,y,z);
        return success;
    }

    @Override
     public VectorArg clone() {
        return new VectorArg(name, defaultValue == null ? null : ((Vector)defaultValue).clone());
    }

    @Override
    public String toString() {
        return VectorArg.Parse(value == null ? (defaultValue == null ? null : (Vector) defaultValue) : (Vector) value);
    }

    @Override
    public EText getDescription() {
        return Message.ARG_VECTOR.msg();
    }

    @Override
    public Class getRawClass() {
        return Vector.class;
    }

    public static Vector Parse(String input) {
        VectorArg arg = new VectorArg();
        if (arg.parse(input)) {
            return (Vector)arg.value;
        }
        return null;
    }

    public static String Parse(Vector input) {
        return Parse(input, false);
    }

    public static String Parse(Vector input, boolean blockVector) {
        if (input == null) {
            return null;
        }
        if (blockVector) {
            return input.getBlockX() + "," + input.getBlockY() + "," + input.getBlockZ();
        } else {
            return input.getX() + "," + input.getY() + "," + input.getZ();
        }
    }
}
