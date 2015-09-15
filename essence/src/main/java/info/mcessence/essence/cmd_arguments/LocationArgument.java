/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
 * Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.mcessence.essence.cmd_arguments;

import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.util.NumberUtil;
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
            sender.sendMessage(Message.INVALID_LOCATION.msg().getMsg(true, arg));
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
                        sender.sendMessage(Message.NOT_A_NUMBER.msg().getMsg(true, components[index].substring(1)));
                        return result;
                    }
                    if (selectorLoc.containsKey(key) && selectorLoc.get(key) instanceof Double) {
                        val += (Double) selectorLoc.get(key);
                    }
                    newLocValues.put(key, val);
                } else {
                    result.success = false;
                    sender.sendMessage(Message.CANT_USE_RELATIVE_COORDS.msg().getMsg(true));
                    return result;
                }
            } else {
                Double val = NumberUtil.getDouble(components[index]);
                if (val == null) {
                    result.success = false;
                    sender.sendMessage(Message.NOT_A_NUMBER.msg().getMsg(true, components[index].substring(1)));
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