package com.clashwars.essence.commands.player;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.PlayerArgument;
import com.clashwars.essence.cmd_arguments.StringArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.cmd_options.IntOption;
import com.clashwars.essence.cmd_options.StringOption;
import com.clashwars.essence.commands.EssenceCommand;
import com.clashwars.essence.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NicknameCmd extends EssenceCommand {

    public NicknameCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        addCommandOption("prefix", new StringOption("~", Message.OPT_NICK_PREFIX), false);
        addCommandOption("min-characters", new IntOption(3, Message.OPT_NICK_MIN_CHARS), false);
        addCommandOption("max-characters", new IntOption(16, Message.OPT_NICK_MAX_CHARS), false);

        cmdArgs = new CmdArgument[] {
                new StringArgument("nickname", ArgumentRequirement.REQUIRED, "", (Integer)cmdOptions.get("min-characters").getValue(), (Integer)cmdOptions.get("max-characters").getValue()),
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others")
        };

        addModifier("-r", Message.MOD_NICK_REMOVE);

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        String nick = (String)result.getValue(0).getValue();
        Player player = result.getValue(1).getValue() == null ? (Player)sender : (Player)result.getValue(1).getValue();

        if (hasPermission(sender, "color")) {
            player.setDisplayName(Util.color((String)cmdOptions.get("prefix").getValue() + nick));
        } else {
            player.setDisplayName((String)cmdOptions.get("prefix").getValue() + nick);
        }

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_NICK_CHANGED, true, hasPermission(sender, "color") ? player.getDisplayName() : Util.removeColor(player.getDisplayName())));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_NICK_OTHER, true, hasPermission(sender, "color") ? player.getDisplayName() : Util.removeColor(player.getDisplayName()), player.getName()));
            }
        }
        return true;
    }

}
