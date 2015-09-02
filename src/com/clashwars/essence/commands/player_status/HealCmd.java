package com.clashwars.essence.commands.player_status;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.DoubleArgument;
import com.clashwars.essence.cmd_arguments.PlayerArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import com.clashwars.essence.cmd_options.BoolOption;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class HealCmd extends EssenceCommand {

    public HealCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others"),
                new DoubleArgument("max", ArgumentRequirement.OPTIONAL, "max", 1, 2048, false)
        };

        addCommandOption("feed", new BoolOption(true, Message.OPT_HEAL_FEED));
        addCommandOption("clear-effects", new BoolOption(true, Message.OPT_HEAL_CLEAR_EFFECTS));
        addCommandOption("extinguish", new BoolOption(true, Message.OPT_HEAL_EXTINGUISH));

        addModifier("-h", Message.MOD_HEAL_ONLY);
        addModifier("-m", Message.MOD_HEAL_MAX_ONLY);

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
        double max = result.getValue(1).getValue() == null ? player.getMaxHealth() : (Double)result.getValue(1).getValue();

        if (player.isDead() || player.getHealth() == 0) {
            sender.sendMessage(ess.getMessages().getMsg(Message.DEAD_PLAYER, true, args[0]));
            return true;
        }

        if (!result.hasModifier("-h")) {
            player.setMaxHealth(max);
        }

        if (!result.hasModifier("-m")) {
            max = Math.min(player.getMaxHealth(), max);
            double amount = max - player.getHealth();
            EntityRegainHealthEvent regainHealthEvent = new EntityRegainHealthEvent(player, amount, EntityRegainHealthEvent.RegainReason.CUSTOM);
            ess.getServer().getPluginManager().callEvent(regainHealthEvent);
            if (!regainHealthEvent.isCancelled()) {
                player.setHealth(max);
            }
        }

        if (result.hasOptionalArg("feed") ? (Boolean)result.getOptionalArg("feed") : (Boolean)cmdOptions.get("feed").getValue()) {
            player.setFoodLevel(20);
        }

        if (result.hasOptionalArg("extinguish") ? (Boolean)result.getOptionalArg("extinguish") : (Boolean)cmdOptions.get("extinguish").getValue()) {
            player.setFireTicks(0);
        }
        if (result.hasOptionalArg("clear-effects") ? (Boolean)result.getOptionalArg("clear-effects") : (Boolean)cmdOptions.get("clear-effects").getValue()) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_HEAL_HEALED, true));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_HEAL_OTHER, true, player.getDisplayName()));
            }
        }
        return true;
    }
}
