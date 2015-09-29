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

package info.mcessence.essence.commands.player_status;

import info.mcessence.essence.Essence;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.BoolArgument;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.arguments.BoolArg;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.List;

public class FlyCmd extends EssenceCommand {

    public FlyCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others"),
                new BoolArgument("state", ArgumentRequirement.OPTIONAL, "")
        };

        addCommandOption("allow-fly", Message.OPT_BURN_TICKS.msg(), new BoolArg(true));
        //addCommandOption("flying", new BoolArg(true, Message.OPT_BURN_TICKS.msg()));

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if(!result.success) {
            return true;
        }
        args = result.getArgs();

        Player player = (Player)result.getArg("player", castPlayer(sender));
        Boolean state = (Boolean)result.getArg("state", !player.isFlying());

        PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(player, state);
        ess.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return true;

        player.setAllowFlight(true);
        player.setFlying(state);
        player.setAllowFlight((Boolean)result.getOptionalArg("allow-fly"));

        if (!result.hasModifier("-s")) {
            player.sendMessage(Message.CMD_FLY.msg().getMsg(true, state.toString()));
            if (!sender.equals(player)) {
                sender.sendMessage(Message.CMD_FLY_OTHER.msg().getMsg(true, player.getDisplayName(), state.toString()));
            }
        }

        return true;
    }

}