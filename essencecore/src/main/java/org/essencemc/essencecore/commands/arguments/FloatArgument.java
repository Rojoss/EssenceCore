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

package org.essencemc.essencecore.commands.arguments;

import org.bukkit.command.CommandSender;
import org.essencemc.essencecore.commands.arguments.internal.ArgumentParseResult;
import org.essencemc.essencecore.commands.arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.commands.arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.NumberUtil;

public class FloatArgument extends CmdArgument {

    protected Float minValue;
    protected Float maxValue;
    protected boolean clampValue;

    public FloatArgument(String name, ArgumentRequirement requirement, String permission, float minValue, float maxValue, boolean clampValue) {
        super(name, requirement, permission);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.clampValue = clampValue;
    }


    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        Float value = NumberUtil.getFloat(arg);
        if (value == null) {
            Message.NOT_A_NUMBER.msg(true, true, cmd.castPlayer(sender)).parseArgs(arg).send(sender);
            result.success = false;
            return result;
        }
        if (minValue != null && value < minValue) {
            if (clampValue) {
                value = minValue;
            } else {
                Message.NUMBER_TOO_LOW.msg(true, true, cmd.castPlayer(sender)).parseArgs(arg, Float.toString(minValue)).send(sender);
                result.success = false;
                return result;
            }
        }
        if (maxValue != null && value > maxValue) {
            if (clampValue) {
                value = maxValue;
            } else {
                Message.NUMBER_TOO_HIGH.msg(true, true, cmd.castPlayer(sender)).parseArgs(arg, Float.toString(maxValue)).send(sender);
                result.success = false;
                return result;
            }
        }

        result.setValue(value);
        result.success = true;

        return result;
    }

    @Override
    public EText getDescription() {
        if (minValue != null && maxValue != null) {
            return Message.ARG_DECIMAL_MIN_MAX.msg().parseArgs(minValue.toString(), maxValue.toString());
        }
        if (minValue != null) {
            return Message.ARG_DECIMAL_MIN.msg().parseArgs(minValue.toString());
        }
        if (maxValue != null) {
            return Message.ARG_DECIMAL_MAX.msg().parseArgs(maxValue.toString());
        }
        return Message.ARG_DECIMAL.msg();
    }
    
}
