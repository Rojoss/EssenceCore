package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.Debug;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
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

    @EventHandler
    private void interact(PlayerInteractEvent event) {
        Bukkit.broadcastMessage("Interact");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 1) {
            //TODO: Do this different
            List<String> playerNames = new ArrayList<String>();
            for (Player p : ess.getServer().getOnlinePlayers()) {
                String name = p.getName();
                if ((player == null || player.canSee(p)) && StringUtil.startsWithIgnoreCase(name, args[0])) {
                    playerNames.add(name);
                }
            }
            return playerNames;
        }
        return null;
    }
}
