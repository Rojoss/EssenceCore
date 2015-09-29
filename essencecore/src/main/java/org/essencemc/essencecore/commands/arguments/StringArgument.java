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
import org.bukkit.util.StringUtil;
import org.essencemc.essencecore.commands.arguments.internal.ArgumentParseResult;
import org.essencemc.essencecore.commands.arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.commands.arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.Message;

import java.util.Arrays;
import java.util.List;

public class StringArgument extends CmdArgument {

    private String match = "";
    private String start = "";
    private String end = "";
    private Integer minChars = null;
    private Integer maxChars = null;

    public StringArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
    }

    public StringArgument(String name, ArgumentRequirement requirement, String permission, String match) {
        super(name, requirement, permission);
        this.match = match;
    }

    public StringArgument(String name, ArgumentRequirement requirement, String permission, String start, String end) {
        super(name, requirement, permission);
        this.start = start.toLowerCase();
        this.end = end.toLowerCase();
    }

    public StringArgument(String name, ArgumentRequirement requirement, String permission, Integer minChars, Integer maxChars) {
        super(name, requirement, permission);
        this.minChars = minChars;
        this.maxChars = maxChars;
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        if (!match.isEmpty()) {
            if (!arg.equalsIgnoreCase(match)) {
                sender.sendMessage(Message.NO_STRING_MATCH.msg().getMsg(true, arg, match));
                result.success = false;
                return result;
            }
        } else if (minChars != null || maxChars != null) {
            if (minChars != null && arg.length() < minChars) {
                sender.sendMessage(Message.TOO_FEW_CHARACTERS.msg().getMsg(true, arg, minChars.toString()));
                result.success = false;
                return result;
            }
            if (maxChars != null && arg.length() > maxChars) {
                sender.sendMessage(Message.TOO_MUCH_CHARACTERS.msg().getMsg(true, arg, maxChars.toString()));
                result.success = false;
                return result;
            }
        } else if (!start.isEmpty() || !end.isEmpty()) {
            if (!start.isEmpty() && !arg.toLowerCase().startsWith(start)) {
                sender.sendMessage(Message.DOESNT_START_WITH.msg().getMsg(true, arg, start));
                result.success = false;
                return result;
            }
            if (!end.isEmpty() && !arg.toLowerCase().endsWith(end)) {
                sender.sendMessage(Message.DOESNT_START_WITH.msg().getMsg(true, arg, end));
                result.success = false;
                return result;
            }
        }

        result.success = true;
        result.setValue(arg);
        return result;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        if (!match.isEmpty() && StringUtil.startsWithIgnoreCase(match, message)) {
            return Arrays.asList(match);
        }
        return null;
    }

    @Override
    public String getDescription() {
        if (!match.isEmpty()) {
            return Message.ARG_STRING_MATCH.msg().getMsg(false, match);
        }
        if (!start.isEmpty() && !end.isEmpty()) {
            return Message.ARG_STRING_START_END.msg().getMsg(false, start, end);
        }
        if (!start.isEmpty()) {
            return Message.ARG_STRING_START.msg().getMsg(false, start);
        }
        if (!end.isEmpty()) {
            return Message.ARG_STRING_END.msg().getMsg(false, end);
        }
        if (minChars != null && maxChars != null) {
            return Message.ARG_STRING_MIN_MAX.msg().getMsg(false, minChars.toString(), maxChars.toString());
        }
        if (minChars != null) {
            return Message.ARG_STRING_MIN.msg().getMsg(false, minChars.toString());
        }
        if (maxChars != null) {
            return Message.ARG_STRING_MAX.msg().getMsg(false, maxChars.toString());
        }
        return Message.ARG_STRING.msg().getMsg(false);
    }
    
}
