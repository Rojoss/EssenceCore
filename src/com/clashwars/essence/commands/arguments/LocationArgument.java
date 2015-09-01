package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationArgument extends CmdArgument {


    public LocationArgument(ArgumentRequirement requirement, String permission) {
        super(requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        //Formats:  x,y,z
        //          x,y,z,yaw:world
        //          x,y,z,yaw,pitch:world

        Location location = null;
        String[] components = arg.split(",|:");
        double x = 0;
        double y = 0;
        double z = 0;
        float yaw = 0;
        float pitch = 0;
        World world = null;

        if (components.length >= 3) {

            x = NumberUtil.getInt(components[0]);
            y = NumberUtil.getInt(components[1]);
            z = NumberUtil.getInt(components[2]);

            switch (components.length) {
                case 3:
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        yaw = 0;
                        pitch = 0;
                        world = player.getWorld();
                    }
                    break;
                case 4:
                    yaw = NumberUtil.getInt(components[3]);
                    pitch = 0;
                    world = Bukkit.getWorld(components[4]);
                    break;
                case 5:
                    yaw = NumberUtil.getInt(components[3]);
                    pitch = NumberUtil.getInt(components[4]);
                    world = Bukkit.getWorld(components[5]);
                    break;
                default:
                    result.success = false;
                    return result;
            }

        } else {
            result.success = false;
            return result;
        }

        if (world == null) {
            result.success = false;
            return result;
        }

        result.setValue(new Location(world, x, y, z, yaw, pitch));

        return result;
    }

}