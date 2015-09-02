package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LocationArgument extends CmdArgument {


    public LocationArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        //Formats:  x,y,z
        //          x,y,z          :world
        //          x,y,z,yaw
        //          x,y,z,yaw      :world
        //          x,y,z,yaw,pitch
        //          x,y,z,yaw,pitch:world

        World world;

        // Check if a world is specified
        if (StringUtils.countMatches(arg, ":") > 1) {
            result.success = false;
            return result;
        } else if (StringUtils.countMatches(arg, ":") == 0) {
            // Check if the sender is a player
            if (sender instanceof Player) {
                Player player = (Player) sender;
                world = player.getWorld();
            } else {
                result.success = false;
                return result;
            }
        } else {
            world = Bukkit.getWorld(arg.split(":")[1]);
        }

        // Check if the world exists
        if (world == null) {
            result.success = false;
            return result;
        }

        String[] components = arg.split(":")[0].split(",");
        Map<String, Number> newLocValues = new HashMap<>();
        newLocValues.put("x", 0d);
        newLocValues.put("y", 0d);
        newLocValues.put("z", 0d);
        newLocValues.put("yaw", 0f);
        newLocValues.put("pitch", 0f);

        if (components.length < 3 || components.length > 5) {
            result.success = false;
            sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.INVALID_LOCATION, true, arg));
            return result;
        }

        Map<String, Object> selectorLoc = null;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            selectorLoc = player.getLocation().serialize();
        } else if (sender instanceof BlockCommandSender) {
            BlockCommandSender blockSender = (BlockCommandSender)sender;
            selectorLoc = blockSender.getBlock().getLocation().serialize();
        }

        int index = 0;
        for (String key : newLocValues.keySet()) {

            if (index == components.length) break;

            if (components[index].startsWith("~")) {
                if (selectorLoc != null) {
                    Double val = NumberUtil.getDouble(components[index].substring(1));
                    if (val == null) {
                        result.success = false;
                        sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NOT_A_NUMBER, true, components[index].substring(1)));
                        return result;
                    }
                    if (selectorLoc.containsKey(key) && selectorLoc.get(key) instanceof Double) {
                        val += (Double) selectorLoc.get(key);
                    }
                    newLocValues.put(key, val);
                } else {
                    result.success = false;
                    sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.CANT_USE_RELATIVE_COORDS, true));
                    return result;
                }
            } else {
                Double val = NumberUtil.getDouble(components[index]);
                if (val == null) {
                    result.success = false;
                    sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NOT_A_NUMBER, true, components[index].substring(1)));
                    return result;
                }

                newLocValues.put(key, val);
            }

            index++;
        }

        result.setValue(new Location(world, newLocValues.get("x").doubleValue(), newLocValues.get("y").doubleValue(), newLocValues.get("z").doubleValue(), newLocValues.get("yaw").floatValue(), newLocValues.get("pitch").floatValue()));

        return result;
    }

}