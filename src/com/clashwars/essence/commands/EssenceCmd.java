package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.Debug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class EssenceCmd extends EssenceCommand {

    public EssenceCmd(Essence ess, String command, String usage, String description, String permission, List<String> aliases) {
        super(ess, command, usage, description, permission, aliases);
        Debug.bc("Essence instance created!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1 || !args[0].equalsIgnoreCase("reload")) {
            //TODO: Send plugin information like version and such.
            sender.sendMessage("Essence...");
            return true;
        }

        if (!hasPermission(sender, "reload")) {
            //TODO: Send no permission message.
            sender.sendMessage("No perms");
            return true;
        }

        ess.getCommandsCfg().load();
        ess.getCommands().registerCommands();


        //TODO: Send reload message
        sender.sendMessage("Configs reloaded..");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        return Arrays.asList("reload");
    }
}
