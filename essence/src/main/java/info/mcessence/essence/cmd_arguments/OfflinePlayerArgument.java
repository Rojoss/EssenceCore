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

import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class OfflinePlayerArgument extends CmdArgument {

    public OfflinePlayerArgument(String name, ArgumentRequirement requirement, String permission) {
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

        OfflinePlayer player = null;
        String[] components = arg.split("-");
        if (components.length == 5) {
            player = Bukkit.getOfflinePlayer(UUID.fromString(arg));
        } else {
            player = Bukkit.getOfflinePlayer(arg);
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
    public String getDescription() {
        return Message.ARG_OFFLINE_PLAYER.msg().getMsg(false);
    }

}
