package com.clashwars.essence.commands.player_status;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.IntArgument;
import com.clashwars.essence.cmd_arguments.PlayerArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import com.clashwars.essence.cmd_options.BoolOption;
import com.clashwars.essence.cmd_options.IntOption;
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

        addCommandOption("saturation", new IntOption(5, Message.OPT_FEED_SATURATION));
        addCommandOption("exhaustion", new BoolOption(true, Message.OPT_FEED_EXHAUSTION));

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

        player.setSaturation(result.hasOptionalArg("saturation") ? (Integer)result.getOptionalArg("saturation") : (Integer)cmdOptions.get("saturation").getValue());
        if (result.hasOptionalArg("exhaustion") ? (Boolean)result.getOptionalArg("exhaustion") : (Boolean)cmdOptions.get("exhaustion").getValue()) {
            player.setExhaustion(0F);
        }

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_FEED_FEEDED, true));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_FEED_OTHER, true, player.getDisplayName()));
            }
        }
        return true;
    }
}