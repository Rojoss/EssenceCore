package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.Debug;
import com.clashwars.essence.util.TabCompleteUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HealCmd extends EssenceCommand {

    public HealCmd(Essence ess, String command, String usage, String description, String permission, List<String> aliases) {
        super(ess, command, usage, description, permission, aliases);
        Debug.bc("Heal instance created!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Debug.bc("Heal");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 1) {
            return TabCompleteUtil.onlinePlayers(ess, sender, args[0]);
        }
        return Arrays.asList("20");
    }
}
