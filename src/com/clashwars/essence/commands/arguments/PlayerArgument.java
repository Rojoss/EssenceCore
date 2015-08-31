package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerArgument extends CmdArgument {

    public PlayerArgument(ArgumentRequirement requirement, String permission) {
        super(requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        Player player = null;
        String[] components = arg.split("-");
        if (components.length == 5) {
            player = Bukkit.getPlayer(UUID.fromString(arg));
        } else {
            player = Bukkit.getPlayer(arg);
        }
        //TODO: Get player by nick/display name

        result.setValue(player);

        if (player == null) {
            result.success = false;
            sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.INVALID_PLAYER, true, arg));
        } else {
            result.success = true;
        }

        return result;
    }
    
}
