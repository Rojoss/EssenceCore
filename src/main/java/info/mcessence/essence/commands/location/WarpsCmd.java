package info.mcessence.essence.commands.location;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.WorldArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.util.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class WarpsCmd extends EssenceCommand {

    public WarpsCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new WorldArgument("world", ArgumentRequirement.OPTIONAL, ""),
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        World world = result.getValue(0).getValue() == null ? null : (World)result.getValue(0).getValue();

        List<String> warps = ess.getWarps().getWarpNames();
        if (world != null) {
            warps.clear();
            Map<String, Location> warpsMap = ess.getWarps().getWarps();
            for (Map.Entry<String, Location> warp : warpsMap.entrySet()) {
                if (warp.getValue().getWorld().equals(world)) {
                    warps.add(warp.getKey());
                }
            }
        }

        sender.sendMessage(ess.getMessages().getMsg(Message.CMD_WARPS, true, warps.size() <= 0 ? ess.getMessages().getMsg(Message.CMD_WARPS_NONE, false) : Util.implode(warps, ", ")));
        return true;
    }

}
