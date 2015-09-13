package info.mcessence.essence.commands.item;

import info.mcessence.essence.Essence;
import info.mcessence.essence.aliases.ItemAlias;
import info.mcessence.essence.aliases.Items;
import info.mcessence.essence.cmd_arguments.StringArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
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
