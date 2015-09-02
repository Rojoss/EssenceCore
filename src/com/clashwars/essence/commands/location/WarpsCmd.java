package com.clashwars.essence.commands.location;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.LocationArgument;
import com.clashwars.essence.cmd_arguments.StringArgument;
import com.clashwars.essence.cmd_arguments.WorldArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import com.clashwars.essence.util.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
