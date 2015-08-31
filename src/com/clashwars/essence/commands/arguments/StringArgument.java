package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import org.bukkit.command.CommandSender;

public class StringArgument extends CmdArgument {

    private String match = "";
    private String start = "";
    private String end = "";
    private int minChars = -1;
    private int maxChars = -1;

    public StringArgument(ArgumentRequirement requirement, String permission) {
        super(requirement, permission);
    }

    public StringArgument(ArgumentRequirement requirement, String permission, String match) {
        super(requirement, permission);
        this.match = match;
    }

    public StringArgument(ArgumentRequirement requirement, String permission, String start, String end) {
        super(requirement, permission);
        this.start = start.toLowerCase();
        this.end = end.toLowerCase();
    }

    public StringArgument(ArgumentRequirement requirement, String permission, int minChars, int maxChars) {
        super(requirement, permission);
        this.minChars = minChars;
        this.maxChars = maxChars;
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        if (!match.isEmpty()) {
            if (!arg.equalsIgnoreCase(match)) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NO_STRING_MATCH, true, arg, match));
                result.success = false;
                return result;
            }
        } else if (minChars > 0 || maxChars > 0) {
            if (minChars > 0 && arg.length() < minChars) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.TOO_FEW_CHARACTERS, true, arg, Integer.toString(minChars)));
                result.success = false;
                return result;
            }
            if (maxChars > 0 && arg.length() > maxChars) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.TOO_MUCH_CHARACTERS, true, arg, Integer.toString(maxChars)));
                result.success = false;
                return result;
            }
        } else if (!start.isEmpty() || !end.isEmpty()) {
            if (!start.isEmpty() && !arg.toLowerCase().startsWith(start)) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.DOESNT_START_WITH, true, arg, start));
                result.success = false;
                return result;
            }
            if (!end.isEmpty() && !arg.toLowerCase().endsWith(end)) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.DOESNT_START_WITH, true, arg, end));
                result.success = false;
                return result;
            }
        }

        result.success = true;
        result.setValue(arg);
        return result;
    }
    
}
