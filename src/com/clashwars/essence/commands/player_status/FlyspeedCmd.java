package com.clashwars.essence.commands.player_status;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.IntArgument;
import com.clashwars.essence.cmd_arguments.PlayerArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FlyspeedCmd extends EssenceCommand {


    public FlyspeedCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new IntArgument("speed", ArgumentRequirement.REQUIRED_CONSOLE, "", 0, 100, false),
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others")
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        Float speed = result.getValue(0).getValue() == null ? 0.2F : (Integer)result.getValue(0).getValue() / 100F;
        Player player = result.getValue(1).getValue() == null ? (Player)sender : (Player)result.getValue(1).getValue();

        player.setFlySpeed(speed);

        speed *= 100;

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_FLYSPEED, true, speed.toString()));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_FLYSPEED_OTHER, true, player.getDisplayName(), speed.toString()));
            }
        }

        return true;
    }


}
