package com.clashwars.essence.commands.player_status;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.IntArgument;
import com.clashwars.essence.cmd_arguments.PlayerArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.cmd_options.BoolOption;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BurnCmd extends EssenceCommand {

    public BurnCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new IntArgument("duration", ArgumentRequirement.REQUIRED, ""),
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others")
        };

        addModifier("-i", Message.MOD_BURN_INCREMENT);

        addCommandOption("ticks-instead-of-seconds", new BoolOption(true, Message.OPT_BURN_TICKS));

        register();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        int duration = (int)result.getValue(0).getValue();
        Player player = result.getValue(1).getValue() == null ? (Player)sender : (Player)result.getValue(1).getValue();

        // TODO: Check for optional arg ticks-instead-of-seconds.

        if (duration < 0) {
            result.addModifier("-i");
        }

        if (result.hasModifier("-i")) {
            if (duration > player.getFireTicks() * 20) duration = 0;

            player.setFireTicks(player.getFireTicks() + duration / 20);
        } else {
            player.setFireTicks(duration * 20);
        }

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_BURN, true, Integer.toString(duration)));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_HEAL_OTHER, true, player.getDisplayName(), Integer.toString(duration)));
            }
        }

        return true;
    }

}
