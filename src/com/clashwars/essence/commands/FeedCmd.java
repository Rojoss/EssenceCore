package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import com.clashwars.essence.util.TabCompleteUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Arrays;
import java.util.List;

public class FeedCmd extends EssenceCommand {

    public FeedCmd(Essence ess, String command, String usage, String description, String permission, List<String> aliases) {
        super(ess, command, usage, description, permission, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && args.length < 1) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_INVALID_USAGE, true, "/feed {player} [amount]"));
            return true;
        }

        Player player = sender instanceof Player ? (Player)sender : null;
        if (args.length > 0) {
            if (!hasPermission(sender, "others")) {
                sender.sendMessage(ess.getMessages().getMsg(Message.NO_PERM, true, getPermission() + ".others"));
                return true;
            }
            player = ess.getServer().getPlayer(args[0]);
        }
        if (player == null) {
            sender.sendMessage(ess.getMessages().getMsg(Message.INVALID_PLAYER, true, args[0]));
            return true;
        }

        int amount = 20;
        if (args.length > 1) {
            amount = NumberUtil.getInt(args[1]);
        }
        amount = Math.min(Math.max(amount, 0), 20);

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 1) {
            return TabCompleteUtil.onlinePlayers(ess, sender, args[0]);
        }
        return Arrays.asList("20");
    }
}
