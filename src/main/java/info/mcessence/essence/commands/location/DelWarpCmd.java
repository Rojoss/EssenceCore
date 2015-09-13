package info.mcessence.essence.commands.location;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.StringArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DelWarpCmd extends EssenceCommand {

    public DelWarpCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new StringArgument("name", ArgumentRequirement.REQUIRED, "", 2, 32)
        };

        addModifier("-a", Message.MOD_DELWARP_ALL);

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            //TODO: Some way to not parse the argument if -a is specified.
            if (result.hasModifier("-a")) {
                ess.getWarps().clear();
                if (!result.hasModifier("-s")) {
                    sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WARP_DELETED_AlL, true));
                }
            }
            return true;
        }
        args = result.getArgs();

        String name = (String)result.getValue(0).getValue();
        if (!ess.getWarps().delWarp(name)) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WARP_INVALID, true, name));
            return true;
        }

        if (!result.hasModifier("-s")) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WARP_DELETED, true, name));
        }
        return true;
    }

}
