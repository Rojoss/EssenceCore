package com.clashwars.essence.commands.world;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.LocationArgument;
import com.clashwars.essence.cmd_arguments.MappedListArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TreeCmd extends EssenceCommand {

    public TreeCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        Map<String, List<String>> types = new HashMap<String, List<String>>();
        types.put("ACACIA", Arrays.asList("acacia"));
        types.put("BIG_TREE", Arrays.asList("big_tree"));
        types.put("BIRCH", Arrays.asList("birch"));
        types.put("BROWN_MUSHROOM", Arrays.asList("brown_mushroom"));
        types.put("COCOA_TREE", Arrays.asList("cocoa_tree"));
        types.put("DARK_OAK", Arrays.asList("dark_oak"));
        types.put("JUNGLE", Arrays.asList("jungle"));
        types.put("JUNGLE_BUSH", Arrays.asList("jungle_bush"));
        types.put("MEGA_REDWOOD", Arrays.asList("mega_redwood"));
        types.put("RED_MUSHROOM", Arrays.asList("red_mushroom"));
        types.put("REDWOOD", Arrays.asList("redwood"));
        types.put("SMALL_JUNGLE", Arrays.asList("small_jungle"));
        types.put("SWAMP", Arrays.asList("swamp"));
        types.put("TALL_BIRCH", Arrays.asList("tall_birch"));
        types.put("TALL_REDWOOD", Arrays.asList("tall_redwood"));
        types.put("TREE", Arrays.asList("tree", "oak"));

        cmdArgs = new CmdArgument[] {
                new MappedListArgument("type", ArgumentRequirement.REQUIRED, "", types),
                new LocationArgument("location", ArgumentRequirement.REQUIRED_CONSOLE, "")
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        TreeType type = TreeType.valueOf(result.getValue(0).getValue().toString());
        Location location = (Location)result.getValue(1).getValue();

        if (result.getValue(1).getValue() == null) {
            location = ((Player)sender).getTargetBlock((HashSet<Byte>)null, 100).getRelative(BlockFace.UP).getLocation();
        }

        boolean success = location.getWorld().generateTree(location, type);

        if (success) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_TREE, true));
        } else {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_TREE_FAILURE, true));
        }

        return true;
    }


}
