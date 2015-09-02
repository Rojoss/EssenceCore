package com.clashwars.essence.commands.location;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.PlayerArgument;
import com.clashwars.essence.cmd_arguments.StringArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.cmd_options.BoolOption;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WarpCmd extends EssenceCommand {

    public WarpCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new StringArgument("name", ArgumentRequirement.REQUIRED, "", 2, 32),
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others")
        };

        addCommandOption("permission-based-warps", new BoolOption(true, Message.OPT_WARP_PERM_BASED), false);

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        String name = (String)result.getValue(0).getValue();
        Player player = result.getValue(1).getValue() == null ? (Player)sender : (Player)result.getValue(1).getValue();

        if (ess.getWarps().getWarp(name) == null) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WARP_INVALID, true, name));
            return true;
        }

        if ((Boolean)cmdOptions.get("permission-based-warps").getValue()) {
            if (!sender.hasPermission("essence.*") && !sender.hasPermission("essence.warps.*") && !sender.hasPermission("essence.warps." + name)) {
                player.sendMessage(ess.getMessages().getMsg(Message.NO_PERM, true, "essence.warps." + name));
                return true;
            }
        }

        player.teleport(ess.getWarps().getWarp(name));
        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_WARP_USE, true, name));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WARP_OTHER, true, player.getDisplayName(), name));
            }
        }
        return true;
    }

}
