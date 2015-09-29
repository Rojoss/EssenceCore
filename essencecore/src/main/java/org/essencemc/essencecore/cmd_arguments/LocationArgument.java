/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://essencemc.org>
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

package org.essencemc.essencecore.cmd_arguments;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentParseResult;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.cmd_arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.Message;

import java.util.UUID;

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

        Location location = new Location(null, 0, 0, 0, 0, 0);

        // If player and not location
        if (arg.startsWith("@")) {
            Player player = null;
            String[] components = arg.split("-");
            if (components.length == 5) {
                location = Bukkit.getPlayer(UUID.fromString(arg.replace("@", ""))).getLocation();
            } else {
                location = Bukkit.getPlayer(arg.replace("@", "")).getLocation();
            }

            if (location == null) {
                result.success = false;
                sender.sendMessage(Message.INVALID_PLAYER.msg().getMsg(true, arg));
            }

            result.setValue(location);
            return result;
        }

        // If world not specified or used relative locations by Console
        if (!arg.contains(":") || arg.contains("~")) {
            if (sender instanceof ConsoleCommandSender) {
                result.success = false;
                return result;
            }
        }

        // Set the location's world
        if (arg.contains(":")) {
            String worldName = arg.substring(arg.indexOf(":"));
            World world = Bukkit.getWorld(worldName);

            // If the world doesn't exist
            if (world == null) {
                result.success = false;
                return result;
            }

            location.setWorld(world);
        } else {
            location.setWorld(getLocation(sender).getWorld());
        }

        // Get x, y, z, yaw and pitch components
        String[] components = arg.split(":")[0].split(",");

        Object[] keySet = getLocation(sender).serialize().keySet().toArray();

        // Adds the relative location and removes ~'s
        for (int i = 1; i < components.length; i++) {
            if (components[i - 1].contains("~")) {
                float value = (float)keySet[i];
                addToLocation(location, new float[] {value}, i, i);
                components[i - 1].replace("~", "");
            }
        }

        if (components.length >= 3 && components.length <= 5) {
            float[] parsedValues = parseValues(components);

            if (parsedValues == null) {
                result.success = false;
                return result;
            }

            addToLocation(location, parsedValues, 1, components.length);
        } else {
            result.success = false;
            return result;
        }

        result.setValue(location);

        return result;
    }

    public Location getLocation(CommandSender sender) {
        if (sender instanceof BlockCommandSender) {
            return ((BlockCommandSender)sender).getBlock().getLocation();
        } else /*if (sender instanceof Player)*/ {
            return ((Player)sender).getLocation();
        }
    }

    public void addToLocation(Location location, float[] vec, int minLimit, int maxLimit) {
        if (minLimit <= 1 && maxLimit >= 1)
            location.setX(location.getX() + vec[minLimit - 1]);
        if (minLimit <= 2 && maxLimit >= 2)
            location.setY(location.getY() + vec[minLimit - 2]);
        if (minLimit <= 3 && maxLimit >= 3)
            location.setZ(location.getZ() + vec[minLimit - 3]);
        if (minLimit <= 4 && maxLimit >= 4)
            location.setYaw(location.getYaw() + vec[minLimit - 4]);
        if (minLimit <= 5 && maxLimit >= 5)
            location.setPitch(location.getPitch() + vec[minLimit - 5]);
    }

    public float[] parseValues(String[] components) {
        float parsed[] = {0, 0, 0, 0, 0};

        for (int i = 0; i < 6; i++) {
            try {
                parsed[i] = Float.valueOf(components[i]);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return parsed;
    }

    @Override
    public String getDescription() {
        return Message.ARG_LOCATION.msg().getMsg(false);
    }

}