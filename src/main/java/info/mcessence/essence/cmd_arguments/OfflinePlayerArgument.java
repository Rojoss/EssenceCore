package info.mcessence.essence.cmd_arguments;

import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class OfflinePlayerArgument extends CmdArgument {

    public OfflinePlayerArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        OfflinePlayer player = null;
        String[] components = arg.split("-");
        if (components.length == 5) {
            player = Bukkit.getOfflinePlayer(UUID.fromString(arg));
        } else {
            player = Bukkit.getOfflinePlayer(arg);
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
