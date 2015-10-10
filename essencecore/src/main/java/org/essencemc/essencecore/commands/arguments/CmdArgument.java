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

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.arguments.*;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.arguments.internal.MatchString;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.message.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CmdArgument {

    private Argument argument;
    private String name;
    protected String permission;
    protected ArgumentRequirement requirement;

    public CmdArgument(String name, Argument argument, ArgumentRequirement requirement, String permission) {
        this.name = name;
        this.argument = argument;
        this.requirement = requirement;
        this.permission = permission;
    }


    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = new ArgumentParseResult();

        if (arg == null || arg.isEmpty()) {
            if (isRequired(sender)) {
                Message.CMD_INVALID_USAGE.msg().send(sender, Param.P("perm", cmd.getUsage(sender)));
                result.success = false;
            } else {
                result.success = true;
            }
            return result;
        }

        if (!cmd.hasPermission(sender, permission)) {
            Message.NO_PERM.msg().send(sender, Param.P("perm", cmd.getPermission() + "." + permission));
            result.success = false;
            return result;
        }

        if (!argument.parse(arg)) {
            argument.getError().send(sender);
            result.success = false;
            return result;
        }

        result.setValue(argument.getValue());
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

    public Argument getArgument() {
        return argument;
    }

    public void setArgument(Argument argument) {
        this.argument = argument;
    }

    public EText getDescription() {
        return argument.getDescription();
    }

    public List<String> tabComplete(CommandSender sender, String message) {
        List<String> tabComplete = new ArrayList<String>();

        if (argument instanceof BoolArg) {
            if (StringUtil.startsWithIgnoreCase("true", message)) {
                tabComplete.add("true");
            }
            if (StringUtil.startsWithIgnoreCase("false", message)) {
                tabComplete.add("false");
            }
            return tabComplete;
        }

        if (argument instanceof ListArg) {
            if (((ListArg)argument).values == null || ((ListArg)argument).values.isEmpty()) {
                return null;
            }
            for (String str : ((ListArg)argument).values) {
                str = str.replaceAll(" ", "");
                if (StringUtil.startsWithIgnoreCase(str, message)) {
                    tabComplete.add(str);
                }
            }
            return tabComplete;
        }

        if (argument instanceof MappedListArg) {
            if (((MappedListArg)argument).values != null && !((MappedListArg)argument).values.isEmpty()) {
                for (String str : ((MappedListArg)argument).values.keySet()) {
                    str = str.replaceAll(" ", "");
                    if (StringUtil.startsWithIgnoreCase(str, message)) {
                        tabComplete.add(str);
                    }
                }
            }
            return tabComplete;
        }

        if (argument instanceof PlayerArg) {
            Player player = sender instanceof Player ? (Player) sender : null;
            Collection<Player> players = (Collection<Player>) EssenceCore.inst().getServer().getOnlinePlayers();
            for (Player p : players) {
                String name = p.getName();
                if ((player == null || player.canSee(p)) && StringUtil.startsWithIgnoreCase(name, message)) {
                    tabComplete.add(name);
                }
            }
            return tabComplete;
        }

        if (argument instanceof OfflinePlayerArg) {
            Player player = sender instanceof Player ? (Player) sender : null;
            OfflinePlayer[] players = EssenceCore.inst().getServer().getOfflinePlayers();
            for (OfflinePlayer p : players) {
                String name = p.getName();
                if ((player == null || (p.isOnline() && player.canSee((Player)p))) && StringUtil.startsWithIgnoreCase(name, message)) {
                    tabComplete.add(name);
                }
            }
            return tabComplete;
        }

        if (argument instanceof StringArg) {
            MatchString match = ((StringArg)argument).match;
            if (match != null && match.match != null && !match.match.isEmpty() && StringUtil.startsWithIgnoreCase(match.match, message)) {
                return Arrays.asList(match.match);
            }
            return null;
        }

        if (argument instanceof WorldArg) {
            List<World> worlds = EssenceCore.inst().getServer().getWorlds();
            for (World world : worlds) {
                String name = world.getName();
                if (StringUtil.startsWithIgnoreCase(name, message)) {
                    tabComplete.add(name);
                }
            }
            return tabComplete;
        }

        return null;
    }
}
