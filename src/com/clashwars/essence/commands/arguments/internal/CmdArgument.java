package com.clashwars.essence.commands.arguments.internal;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.internal.EssenceCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CmdArgument {

    private String name;
    protected String permission;
    protected ArgumentRequirement requirement;

    public CmdArgument(String name) {
        this(name, ArgumentRequirement.REQUIRED, "");
    }

    public CmdArgument(String name, ArgumentRequirement requirement) {
        this(name, requirement, "");
    }

    public CmdArgument(String name, ArgumentRequirement requirement, String permission) {
        this.name = name;
        this.requirement = requirement;
        this.permission = permission;
    }


    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = new ArgumentParseResult();

        if (arg == null || arg.isEmpty()) {
            if (isRequired(sender)) {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.CMD_INVALID_USAGE, true, cmd.getUsage(sender)));
                result.success = false;
            } else {
                result.success = true;
            }
            return result;
        }

        if (!cmd.hasPermission(sender, permission)) {
            sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NO_PERM, true, cmd.getPermission() + "." + permission));
            result.success = false;
            return result;
        }

        result.success = true;
        return result;
    }

    public boolean isRequired(CommandSender sender) {
        if (sender instanceof Player) {
            if (requirement == ArgumentRequirement.REQUIRED) {
                return true;
            }
        } else {
            if (requirement == ArgumentRequirement.REQUIRED || requirement == ArgumentRequirement.REQUIRED_CONSOLE) {
                return true;
            }
        }
        return false;
    }

    public String getName(boolean format) {
        if (!format) {
            return name;
        }
        if (requirement == ArgumentRequirement.REQUIRED) {
            return "{" + name + "}";
        } else {
            return "[" + name + "]";
        }
    }

    public String getName(CommandSender sender) {
        if (sender == null) {
            return getName(true);
        } else {
            if (isRequired(sender)) {
                return "{" + name + "}";
            } else {
                return "[" + name + "]";
            }
        }
    }

    public List<String> tabComplete(CommandSender sender, String message) {
        return null;
    }
}
