package com.clashwars.essence.util;

import com.clashwars.essence.Essence;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteUtil {

    public static List<String> onlinePlayers(Essence ess, CommandSender sender, String message) {
        Player player = sender instanceof Player ? (Player) sender : null;
        List<String> playerNames = new ArrayList<String>();
        for (Player p : ess.getServer().getOnlinePlayers()) {
            String name = p.getName();
            if ((player == null || player.canSee(p)) && StringUtil.startsWithIgnoreCase(name, message)) {
                playerNames.add(name);
            }
        }
        return playerNames;
    }


}
