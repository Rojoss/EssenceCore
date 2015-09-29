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

package info.mcessence.essence.commands.location;

import info.mcessence.essence.Essence;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.StringArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.arguments.BoolArg;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WarpCmd extends EssenceCommand {

    public WarpCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new StringArgument("name", ArgumentRequirement.REQUIRED, "", 2, 32),
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others")
        };

        addCommandOption("permission-based-warps", Message.OPT_WARP_PERM_BASED.msg(), new BoolArg(true), false);

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        String name = (String)result.getArg(0).getValue();
        Player player = result.getArg(1).getValue() == null ? (Player)sender : (Player)result.getArg(1).getValue();

        if (ess.getWarps().getWarp(name) == null) {
            sender.sendMessage(Message.CMD_WARP_INVALID.msg().getMsg(true, name));
            return true;
        }

        if ((Boolean)cmdOptions.get("permission-based-warps").getArg().getValue()) {
            if (!sender.hasPermission("essence.*") && !sender.hasPermission("essence.warps.*") && !sender.hasPermission("essence.warps." + name)) {
                player.sendMessage(Message.NO_PERM.msg().getMsg(true, "essence.warps." + name));
                return true;
            }
        }

        player.teleport(ess.getWarps().getWarp(name));
        if (!result.hasModifier("-s")) {
            player.sendMessage(Message.CMD_WARP_USE.msg().getMsg(true, name));
            if (!sender.equals(player)) {
                sender.sendMessage(Message.CMD_WARP_OTHER.msg().getMsg(true, player.getDisplayName(), name));
            }
        }
        return true;
    }

}
