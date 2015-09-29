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

package org.essencemc.essencecore.cmd_arguments;

import org.bukkit.command.CommandSender;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentParseResult;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.cmd_arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.NumberUtil;

public class DoubleArgument extends CmdArgument {

    protected Double minValue;
    protected Double maxValue;
    protected boolean clampValue;

    public DoubleArgument(String name, ArgumentRequirement requirement, String permission, double minValue, double maxValue, boolean clampValue) {
        super(name, requirement, permission);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.clampValue = clampValue;
    }

    public DoubleArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
    }


    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        Double value = NumberUtil.getDouble(arg);
        if (value == null) {
            sender.sendMessage(Message.NOT_A_NUMBER.msg().getMsg(true, arg));
            result.success = false;
            return result;
        }
        if (minValue != null && value < minValue) {
            if (clampValue) {
                value = minValue;
            } else {
                sender.sendMessage(Message.NUMBER_TOO_LOW.msg().getMsg(true, arg, Double.toString(minValue)));
                result.success = false;
                return result;
            }
        }
        if (maxValue != null && value > maxValue) {
            if (clampValue) {
                value = maxValue;
            } else {
                sender.sendMessage(Message.NUMBER_TOO_HIGH.msg().getMsg(true, arg, Double.toString(maxValue)));
                result.success = false;
                return result;
            }
        }

        result.setValue(value);
        result.success = true;

        return result;
    }

    @Override
    public String getDescription() {
        if (minValue != null && maxValue != null) {
            return Message.ARG_DECIMAL_MIN_MAX.msg().getMsg(false, minValue.toString(), maxValue.toString());
        }
        if (minValue != null) {
            return Message.ARG_DECIMAL_MIN.msg().getMsg(false, minValue.toString());
        }
        if (maxValue != null) {
            return Message.ARG_DECIMAL_MAX.msg().getMsg(false, maxValue.toString());
        }
        return Message.ARG_DECIMAL.msg().getMsg(false);
    }
    
}
