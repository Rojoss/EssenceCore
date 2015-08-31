package com.clashwars.essence.commands.internal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReflectCommand extends Command {

    private EssenceCommand cmd = null;

    protected ReflectCommand(String command) {
        super(command);
    }

    public void setExecutor(EssenceCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (cmd != null) {
            if (!cmd.hasPermission(sender)) {
                //TODO: Send no permission message.
                return true;
            }
            return cmd.onCommand(sender, this, commandLabel, args);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message, String[] args) {
        if (cmd != null) {
            return cmd.onTabComplete(sender, this, message, args);
        }
        return null;
    }
}
