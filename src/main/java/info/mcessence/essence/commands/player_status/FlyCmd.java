package info.mcessence.essence.commands.player_status;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.BoolArgument;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.cmd_options.BoolOption;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.List;

public class FlyCmd extends EssenceCommand {

    public FlyCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others"),
                new BoolArgument("state", ArgumentRequirement.OPTIONAL, "")
        };

        addCommandOption("allow-fly", new BoolOption(true, Message.OPT_BURN_TICKS));
        //addCommandOption("flying", new BoolOption(true, Message.OPT_BURN_TICKS));

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if(!result.success) {
            return true;
        }
        args = result.getArgs();

        Player player = result.getValue(0).getValue() == null ? (Player)sender : (Player)result.getValue(0).getValue();
        Boolean state = result.getValue(1).getValue() == null ? !player.isFlying() : (Boolean)result.getValue(1).getValue();

        PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(player, state);
        ess.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return true;

        player.setAllowFlight(true);
        player.setFlying(state);
        player.setAllowFlight(result.hasOptionalArg("allow-fly") ? (Boolean)result.getOptionalArg("allow-fly") : (Boolean)cmdOptions.get("allow-fly").getValue());

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_FLY, true, state.toString()));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_FLY_OTHER, true, player.getDisplayName(), state.toString()));
            }
        }

        return true;
    }

}