package com.clashwars.essence.commands.world;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.LocationArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class LightningCmd extends EssenceCommand {

    public LightningCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new LocationArgument("location|player", ArgumentRequirement.REQUIRED, "")
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        Location location = (Location) result.getValue(0).getValue();
        location.setY(location.getWorld().getHighestBlockAt(location).getY());

        location.getWorld().strikeLightning(location);

        if (!result.hasModifier("-s")) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_HEAL_HEALED, true));
        }

        return true;
    }

}
