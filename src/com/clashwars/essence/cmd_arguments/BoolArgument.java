package com.clashwars.essence.cmd_arguments;

import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResult;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class BoolArgument extends CmdArgument {

    public BoolArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
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

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        List<String> tabComplete = new ArrayList<String>();
        if (StringUtil.startsWithIgnoreCase("true", message)) {
            tabComplete.add("true");
        }
        if (StringUtil.startsWithIgnoreCase("false", message)) {
            tabComplete.add("false");
        }
        return tabComplete;
    }
}
