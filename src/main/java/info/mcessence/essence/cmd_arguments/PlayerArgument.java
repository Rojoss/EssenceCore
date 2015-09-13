package info.mcessence.essence.cmd_arguments;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerArgument extends CmdArgument {

    public PlayerArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
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

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        Player player = sender instanceof Player ? (Player) sender : null;
        List<String> playerNames = new ArrayList<String>();
        Collection<Player> players = (Collection<Player>)Essence.inst().getServer().getOnlinePlayers();
        for (Player p : players) {
            String name = p.getName();
            if ((player == null || player.canSee(p)) && StringUtil.startsWithIgnoreCase(name, message)) {
                playerNames.add(name);
            }
        }
        return playerNames;
    }
}