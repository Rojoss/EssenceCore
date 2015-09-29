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

package org.essencemc.essencecore.cmd_arguments;

import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentParseResult;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.cmd_arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedListArgument extends CmdArgument {

    Map<String, List<String>> strings = new HashMap<String, List<String>>();

    public MappedListArgument(String name, ArgumentRequirement requirement, String permission, Map<String, List<String>> strings) {
        super(name, requirement, permission);
        this.strings = strings;
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        List<String> allArgs = new ArrayList<String>();
        if (strings != null && !strings.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : strings.entrySet()) {
                for (String str : entry.getValue()) {
                    if (str.equalsIgnoreCase(arg)) {
                        result.success = true;
                        result.setValue(entry.getKey());
                        return result;
                    }
                    allArgs.add(str);
                }
            }
        }

        sender.sendMessage(Message.INVALID_LIST_ARGUMENT.msg().getMsg(true, arg, Util.implode(allArgs, ",")));
        result.success = false;
        return result;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        List<String> tabComplete = new ArrayList<String>();
        if (strings != null && !strings.isEmpty()) {
            for (String str : strings.keySet()) {
                if (StringUtil.startsWithIgnoreCase(str, message)) {
                    tabComplete.add(str);
                }
            }
        }
        return tabComplete;
    }

    @Override
    public String getDescription() {
        return Message.ARG_LIST.msg().getMsg(false, Util.implode(strings.keySet(), ","));
    }
}
