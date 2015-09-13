package info.mcessence.essence.commands.player_status;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.cmd_arguments.IntArgument;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WalkspeedCmd extends EssenceCommand {

    public WalkspeedCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new IntArgument("speed", ArgumentRequirement.REQUIRED_CONSOLE, "", 0, 100, false),
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others")
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        Float speed = result.getValue(0).getValue() == null ? 0.2F : (Integer)result.getValue(0).getValue() / 100F;
        Player player = result.getValue(1).getValue() == null ? (Player)sender : (Player)result.getValue(1).getValue();

        player.setWalkSpeed(speed);

        speed *= 100;

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_WALKSPEED, true, speed.toString()));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WALKSPEED_OTHER, true, player.getDisplayName(), speed.toString()));
            }
        }

        return true;
    }
}