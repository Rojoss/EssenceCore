package com.clashwars.essence.commands.location;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.LocationArgument;
import com.clashwars.essence.cmd_arguments.StringArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetWarpCmd extends EssenceCommand {

    public SetWarpCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new StringArgument("name", ArgumentRequirement.REQUIRED, "", 2, 32),
                new LocationArgument("location|player", ArgumentRequirement.REQUIRED_CONSOLE, "location")
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

        String name = (String)result.getValue(0).getValue();
        Location location = result.getValue(1).getValue() == null ? ((Player)sender).getLocation() : (Location) result.getValue(1).getValue();

        ess.getWarps().setWarp(name, location);
        if (!result.hasModifier("-s")) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WARP_SET, true, name));
        }
        return true;
    }

}
