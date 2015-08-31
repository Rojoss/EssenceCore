package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.DoubleArgument;
import com.clashwars.essence.commands.arguments.PlayerArgument;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResults;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
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

        cmdArgs = new CmdArgument[] {
            new PlayerArgument(ArgumentRequirement.REQUIRED_CONSOLE, "others"),
            new DoubleArgument(ArgumentRequirement.OPTIONAL, "max", 1, 2048, false)
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        Player player = result.getValue(0).getValue() == null ? (Player)sender : (Player)result.getValue(0).getValue();
        double max = result.getValue(1).getValue() == null ? player.getMaxHealth() : (Double)result.getValue(1).getValue();

        if (player.isDead() || player.getHealth() == 0) {
            sender.sendMessage(ess.getMessages().getMsg(Message.DEAD_PLAYER, true, args[0]));
            return true;
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
        //TODO: Tab completion based on cmdArgs (inside EssenceCommand I guess)
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 1) {
            return TabCompleteUtil.onlinePlayers(ess, sender, args[0]);
        }
        return Arrays.asList("20");
    }
}
