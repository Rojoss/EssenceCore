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
import info.mcessence.essence.commands.CommandOption;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.IntArgument;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.arguments.BoolArg;
import info.mcessence.essence.arguments.IntArg;
import info.mcessence.essence.util.Debug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.List;

public class FeedCmd extends EssenceCommand {

    public FeedCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others"),
                new IntArgument("amount", ArgumentRequirement.OPTIONAL, "", 0, 20, false)
        };

        addCommandOption("saturation", Message.OPT_FEED_SATURATION.msg(), new IntArg(5));
        addCommandOption("exhaustion", Message.OPT_FEED_EXHAUSTION.msg(), new BoolArg(true));

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        Player player = result.getValue(0).getValue() == null ? (Player)sender : (Player)result.getValue(0).getValue();
        int amount = result.getValue(1).getValue() == null ?  20 : (Integer)result.getValue(1).getValue();

        FoodLevelChangeEvent foodLevelChangeEvent = new FoodLevelChangeEvent(player, amount);
        ess.getServer().getPluginManager().callEvent(foodLevelChangeEvent);
        if (foodLevelChangeEvent.isCancelled()) {
            return true;
        }

        player.setFoodLevel(amount);
        player.setSaturation((Integer)result.getOptionalArg("saturation"));
        if ((Boolean)result.getOptionalArg("exhaustion")) {
            player.setExhaustion(0F);
        }

        if (!result.hasModifier("-s")) {
            player.sendMessage(Message.CMD_FEED_FEEDED.msg().getMsg(true));
            if (!sender.equals(player)) {
                sender.sendMessage(Message.CMD_FEED_OTHER.msg().getMsg(true, player.getDisplayName()));
            }
        }
        return true;
    }
}
