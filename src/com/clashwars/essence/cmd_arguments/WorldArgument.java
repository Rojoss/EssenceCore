package com.clashwars.essence.cmd_arguments;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResult;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class WorldArgument extends CmdArgument {

    public WorldArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        Server server = Essence.inst().getServer();

        World world = null;
        if (NumberUtil.getInt(arg) != null) {
            world = server.getWorlds().size() < NumberUtil.getInt(arg) ? null : server.getWorlds().get(NumberUtil.getInt(arg));
        } else if (arg.split("-").length == 5) {
            world = server.getWorld(UUID.fromString(arg));
        } else {
            world = server.getWorld(arg);
        }

        result.setValue(world);
        result.success = true;
        if (world == null) {
            result.success = false;
            sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.INVALID_WORLD, true, arg));
        }
        return result;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        List<String> worldNames = new ArrayList<String>();
        List<World> worlds = Essence.inst().getServer().getWorlds();
        for (World world : worlds) {
            String name = world.getName();
            if (StringUtil.startsWithIgnoreCase(name, message)) {
                worldNames.add(name);
            }
        }
        return worldNames;
    }
}
