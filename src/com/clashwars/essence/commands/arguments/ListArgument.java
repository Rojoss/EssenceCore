package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ListArgument extends CmdArgument {

    List<String> strings = new ArrayList<String>();

    public ListArgument(ArgumentRequirement requirement, String permission) {
        super(requirement, permission);
    }

    public ListArgument(ArgumentRequirement requirement, String permission, List<String> strings) {
        super(requirement, permission);
        this.strings = strings;
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        if (strings != null && !strings.isEmpty()) {
            if (!strings.contains(arg)) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.INVALID_LIST_ARGUMENT, true, arg, Util.implode(strings, ",")));
                result.success = false;
                return result;
            }
        }

        result.success = true;
        result.setValue(arg);
        return result;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        List<String> tabComplete = new ArrayList<String>();
        for (String str : strings) {
            if (StringUtil.startsWithIgnoreCase(str, message)) {
                tabComplete.add(str);
            }
        }
        return tabComplete;
    }
}
