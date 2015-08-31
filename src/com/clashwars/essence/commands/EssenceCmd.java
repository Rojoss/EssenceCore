package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.Debug;
import com.clashwars.essence.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Arrays;
import java.util.List;

public class EssenceCmd extends EssenceCommand {

    public EssenceCmd(Essence ess, String command, String usage, String description, String permission, List<String> aliases) {
        super(ess, command, usage, description, permission, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1 || !args[0].equalsIgnoreCase("reload")) {
            PluginDescriptionFile pdf = ess.getDescription();
            sender.sendMessage(Util.color(ess.getMessages().getMsg(Message.CMD_ESSENCE_INFO, false, pdf.getDescription(), pdf.getVersion(), pdf.getWebsite(),
                    "&7" + Util.implode(pdf.getAuthors(), "&8, &7", " &8& &7"))));
            return true;
        }

        if (!hasPermission(sender, "reload")) {
            sender.sendMessage(ess.getMessages().getMsg(Message.NO_PERM, true, getPermission() + ".reload"));
            return true;
        }

        //Reload configs and commands.
        ess.getMessages().load();
        ess.getCommandsCfg().load();
        ess.getCommands().registerCommands();

        sender.sendMessage(ess.getMessages().getMsg(Message.CMD_ESSENCE_RELOAD, true));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        return Arrays.asList("reload");
    }
}
