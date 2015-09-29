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

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentParseResult;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.cmd_arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerArgument extends CmdArgument {

    public PlayerArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = new ArgumentParseResult();
        if (arg == null || arg.isEmpty()) {
            if (isRequired(sender)) {
                sender.sendMessage(Message.CMD_INVALID_USAGE.msg().getMsg(true, cmd.getUsage(sender)));
                result.success = false;
                return result;
            }
            result.success = true;
        }

        Player player = null;
        String[] components = arg.split("-");
        if (components.length == 5) {
            player = Bukkit.getPlayer(UUID.fromString(arg));
        } else {
            player = Bukkit.getPlayer(arg);
        }
        //TODO: Get player by nick/display name

        //If the specified player is the sender don't do a permission check...
        if (!sender.equals(player)) {
            if (!cmd.hasPermission(sender, permission)) {
                sender.sendMessage(Message.NO_PERM.msg().getMsg(true, cmd.getPermission() + "." + permission));
                result.success = false;
                return result;
            }
        }

        result.setValue(player);

        if (player == null) {
            result.success = false;
            sender.sendMessage(Message.INVALID_PLAYER.msg().getMsg(true, arg));
        } else {
            result.success = true;
        }

        return result;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        Player player = sender instanceof Player ? (Player) sender : null;
        List<String> playerNames = new ArrayList<String>();
        Collection<Player> players = (Collection<Player>) EssenceCore.inst().getServer().getOnlinePlayers();
        for (Player p : players) {
            String name = p.getName();
            if ((player == null || player.canSee(p)) && StringUtil.startsWithIgnoreCase(name, message)) {
                playerNames.add(name);
            }
        }
        return playerNames;
    }

    @Override
    public String getDescription() {
        return Message.ARG_PLAYER.msg().getMsg(false);
    }
}
