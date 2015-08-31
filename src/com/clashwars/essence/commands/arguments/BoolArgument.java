package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import org.bukkit.command.CommandSender;

public class BoolArgument extends CmdArgument {

    public BoolArgument(ArgumentRequirement requirement, String permission) {
        super(requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        if (arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("yes") || arg.equalsIgnoreCase("1")) {
            result.setValue(true);
        } else {
            result.setValue(false);
        }
        result.success = true;

        return result;
    }
    
}
