package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedListArgument extends CmdArgument {

    Map<String, List<String>> strings = new HashMap<String, List<String>>();

    public MappedListArgument(String name, ArgumentRequirement requirement, String permission, Map<String, List<String>> strings) {
        super(name, requirement, permission);
        this.strings = strings;
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        List<String> allArgs = new ArrayList<String>();
        if (strings != null && !strings.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : strings.entrySet()) {
                for (String str : entry.getValue()) {
                    if (str.equalsIgnoreCase(arg)) {
                        result.success = true;
                        result.setValue(entry.getKey());
                        return result;
                    }
                    allArgs.add(str);
                }
            }
        }

        sender.sendMessage(Essence.inst().getMessages().getMsg(Message.INVALID_LIST_ARGUMENT, true, arg, Util.implode(allArgs, ",")));
        result.success = false;
        return result;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        List<String> tabComplete = new ArrayList<String>();
        if (strings != null && !strings.isEmpty()) {
            for (String str : strings.keySet()) {
                if (StringUtil.startsWithIgnoreCase(str, message)) {
                    tabComplete.add(str);
                }
            }
        }
        return tabComplete;
    }
}
