package com.clashwars.essence.commands.item;

import com.clashwars.essence.Essence;
import com.clashwars.essence.aliases.ItemAlias;
import com.clashwars.essence.aliases.Items;
import com.clashwars.essence.cmd_arguments.StringArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ItemInfoCmd extends EssenceCommand {

    public ItemInfoCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new StringArgument("[item[:data]]", ArgumentRequirement.REQUIRED_CONSOLE, "others", 1, 100)
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

        String itemString = (String)result.getValue(0).getValue();

        ItemAlias item = Items.getItem(itemString);
        if (item == null) {
            sender.sendMessage("Invalid item...");
            return true;
        }

        sender.sendMessage(item.getName());
        sender.sendMessage(item.getType().toString() + "(" + item.getId() + ")");
        sender.sendMessage("" + item.getData());
        sender.sendMessage(item.getAliasesStr());
        return true;
    }
}
