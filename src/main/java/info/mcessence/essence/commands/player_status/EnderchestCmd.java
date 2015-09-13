package info.mcessence.essence.commands.player_status;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EnderchestCmd extends EssenceCommand {

    public EnderchestCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new PlayerArgument("player", ArgumentRequirement.OPTIONAL, "others")
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_PLAYER_ONLY, true));
            return true;
        }

        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        // TODO: Offline player support

        Player player = (Player)sender;
        Player targetPlayer = result.getValue(0).getValue() == null ? (Player)sender : (Player)result.getValue(0).getValue();

        player.openInventory(targetPlayer.getEnderChest());

        if (!result.hasModifier("-s")) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_ENDERCHEST, true));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_ENDERCHEST_OTHER, true, player.getName()));
            }
        }

        return true;
    }

}
