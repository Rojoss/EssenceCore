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

package info.mcessence.essence.cmd_arguments;

import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.Arrays;
import java.util.List;

public class StringArgument extends CmdArgument {

    private String match = "";
    private String start = "";
    private String end = "";
    private int minChars = -1;
    private int maxChars = -1;

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

    public StringArgument(String name, ArgumentRequirement requirement, String permission, int minChars, int maxChars) {
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
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NO_STRING_MATCH, true, arg, match));
                result.success = false;
                return result;
            }
        } else if (minChars > 0 || maxChars > 0) {
            if (minChars > 0 && arg.length() < minChars) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.TOO_FEW_CHARACTERS, true, arg, Integer.toString(minChars)));
                result.success = false;
                return result;
            }
            if (maxChars > 0 && arg.length() > maxChars) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.TOO_MUCH_CHARACTERS, true, arg, Integer.toString(maxChars)));
                result.success = false;
                return result;
            }
        } else if (!start.isEmpty() || !end.isEmpty()) {
            if (!start.isEmpty() && !arg.toLowerCase().startsWith(start)) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.DOESNT_START_WITH, true, arg, start));
                result.success = false;
                return result;
            }
            if (!end.isEmpty() && !arg.toLowerCase().endsWith(end)) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.DOESNT_START_WITH, true, arg, end));
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
    
}
