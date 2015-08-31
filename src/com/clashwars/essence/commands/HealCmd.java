package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import com.clashwars.essence.util.TabCompleteUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.Arrays;
import java.util.List;

public class HealCmd extends EssenceCommand {

    public HealCmd(Essence ess, String command, String usage, String description, String permission, List<String> aliases) {
        super(ess, command, usage, description, permission, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && args.length < 1) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_INVALID_USAGE, true, "/heal {player} [max]"));
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

        if (player.isDead() || player.getHealth() == 0) {
            sender.sendMessage(ess.getMessages().getMsg(Message.DEAD_PLAYER, true, args[0]));
            return true;
        }

        double max = -1;
        if (args.length > 1) {
            if (!hasPermission(sender, "max")) {
                sender.sendMessage(ess.getMessages().getMsg(Message.NO_PERM, true, getPermission() + ".max"));
                return true;
            }
            max = NumberUtil.getDouble(args[1]);
        }
        if (max < 0) {
            max = player.getMaxHealth();
        }

        player.setMaxHealth(max);
        double amount = max - player.getHealth();
        EntityRegainHealthEvent regainHealthEvent = new EntityRegainHealthEvent(player, amount, EntityRegainHealthEvent.RegainReason.CUSTOM);
        ess.getServer().getPluginManager().callEvent(regainHealthEvent);
        if (!regainHealthEvent.isCancelled()) {
            player.setHealth(max);
        }

        player.sendMessage(ess.getMessages().getMsg(Message.CMD_HEAL_HEALED, true));
        if (!sender.equals(player)) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_HEAL_OTHER, true, player.getDisplayName()));
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
