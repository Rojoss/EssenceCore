package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.IntArgument;
import com.clashwars.essence.commands.arguments.PlayerArgument;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResults;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.List;

public class FeedCmd extends EssenceCommand {

    public FeedCmd(Essence ess, String command, String usage, String description, String permission, List<String> aliases) {
        super(ess, command, usage, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new PlayerArgument(ArgumentRequirement.REQUIRED_CONSOLE, "others"),
                new IntArgument(ArgumentRequirement.OPTIONAL, "", 0, 20, false)
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        Player player = result.getValue(0).getValue() == null ? (Player)sender : (Player)result.getValue(0).getValue();
        int amount = result.getValue(1).getValue() == null ?  20 : (Integer)result.getValue(1).getValue();

        FoodLevelChangeEvent foodLevelChangeEvent = new FoodLevelChangeEvent(player, amount);
        ess.getServer().getPluginManager().callEvent(foodLevelChangeEvent);
        if (!foodLevelChangeEvent.isCancelled()) {
            player.setFoodLevel(amount);
            player.setSaturation(10);
            player.setExhaustion(0F);
        }

        player.sendMessage(ess.getMessages().getMsg(Message.CMD_FEED_FEEDED, true));
        if (!sender.equals(player)) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_FEED_OTHER, true, player.getDisplayName()));
        }
        return true;
    }
}
