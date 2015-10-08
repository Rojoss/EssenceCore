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

package org.essencemc.essencecore.commands.arguments.internal;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;

import java.util.List;

public abstract class CmdArgument {

    private String name;
    protected String permission;
    protected ArgumentRequirement requirement;

    public CmdArgument(String name) {
        this(name, ArgumentRequirement.REQUIRED, "");
    }

    public CmdArgument(String name, ArgumentRequirement requirement) {
        this(name, requirement, "");
    }

    public CmdArgument(String name, ArgumentRequirement requirement, String permission) {
        this.name = name;
        this.requirement = requirement;
        this.permission = permission;
    }


    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = new ArgumentParseResult();

        if (arg == null || arg.isEmpty()) {
            if (isRequired(sender)) {
                Message.CMD_INVALID_USAGE.msg(true, true, cmd.castPlayer(sender)).parseArgs(cmd.getUsage()).send(sender);
                result.success = false;
            } else {
                result.success = true;
            }
            return result;
        }

        if (!cmd.hasPermission(sender, permission)) {
            Message.NO_PERM.msg(true, true, cmd.castPlayer(sender)).parseArgs(cmd.getPermission() + "." + permission).send(sender);
            result.success = false;
            return result;
        }

        result.success = true;
        return result;
    }

    public boolean isRequired(CommandSender sender) {
        if (sender instanceof Player) {
            if (requirement == ArgumentRequirement.REQUIRED) {
                return true;
            }
        } else {
            if (requirement == ArgumentRequirement.REQUIRED || requirement == ArgumentRequirement.REQUIRED_CONSOLE) {
                return true;
            }
        }
        return false;
    }

    public void setRequirement(ArgumentRequirement requirement) {
        this.requirement = requirement;
    }

    public String getName(boolean format) {
        if (!format) {
            return name;
        }
        if (requirement == ArgumentRequirement.REQUIRED) {
            return "{" + name + "}";
        } else {
            return "[" + name + "]";
        }
    }

    public String getName(CommandSender sender) {
        if (sender == null) {
            return getName(true);
        } else {
            if (isRequired(sender)) {
                return "{" + name + "}";
            } else {
                return "[" + name + "]";
            }
        }
    }

    public EText getDescription() {
        return Message.ARG_NO_DESC.msg();
    }

    public List<String> tabComplete(CommandSender sender, String message) {
        return null;
    }
}
