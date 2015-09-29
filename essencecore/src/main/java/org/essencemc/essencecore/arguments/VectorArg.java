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

import org.bukkit.util.Vector;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.Message;
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
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().getMsg(true, name) : Message.NO_ARG_VALUE.msg().getMsg(true);
            success = false;
            return success;
        }

        String[] split = input.split(";");
        if (split.length < 3) {
            error = Message.NOT_A_VECTOR.msg().getMsg(true, input);
            success = false;
            return success;
        }

        Float x = NumberUtil.getFloat(split[0]);
        if (x == null) {
            error = Message.NOT_A_DECIMAL.msg().getMsg(true, split[0]);
            success = false;
            return success;
        }

        Float y = NumberUtil.getFloat(split[1]);
        if (y == null) {
            error = Message.NOT_A_DECIMAL.msg().getMsg(true, split[1]);
            success = false;
            return success;
        }

        Float z = NumberUtil.getFloat(split[2]);
        if (z == null) {
            error = Message.NOT_A_DECIMAL.msg().getMsg(true, split[2]);
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
    public String getDescription() {
        return Message.ARG_VECTOR.msg().getMsg(false);
    }

    @Override
    public Class getRawClass() {
        return Vector.class;
    }
}
