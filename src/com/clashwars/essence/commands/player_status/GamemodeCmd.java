package com.clashwars.essence.commands.player_status;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.cmd_arguments.MappedListArgument;
import com.clashwars.essence.cmd_arguments.PlayerArgument;
import com.clashwars.essence.cmd_arguments.internal.ArgumentParseResults;
import com.clashwars.essence.cmd_arguments.internal.ArgumentRequirement;
import com.clashwars.essence.cmd_arguments.internal.CmdArgument;
import com.clashwars.essence.commands.EssenceCommand;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamemodeCmd extends EssenceCommand {

    public GamemodeCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        Map<String, List<String>> modes = new HashMap<String, List<String>>();
        modes.put("survival", Arrays.asList("survival", "0", "sur", "s"));
        modes.put("creative", Arrays.asList("creative", "1", "crea", "c"));
        modes.put("adventure", Arrays.asList("adventure", "2", "adv", "a"));
        modes.put("spectator", Arrays.asList("spectator", "3", "spec", "sp"));

        cmdArgs = new CmdArgument[] {
                new MappedListArgument("mode", ArgumentRequirement.REQUIRED, "", modes),
                new PlayerArgument("player", ArgumentRequirement.OPTIONAL, "others")
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

        String mode = (String)result.getValue(0).getValue();
        Player player = result.getValue(1).getValue() == null ? (Player)sender : (Player)result.getValue(1).getValue();

        GameMode gm = null;
        for (GameMode gameMode : GameMode.values()) {
            if (gameMode.toString().equalsIgnoreCase(mode)) {
                gm = gameMode;
            }
        }

        if (gm == null) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_GAMEMODE_INVALID, true, mode));
            return true;
        }

        PlayerGameModeChangeEvent gamemodeChangeEvent = new PlayerGameModeChangeEvent(player, gm);
        ess.getServer().getPluginManager().callEvent(gamemodeChangeEvent);
        if (gamemodeChangeEvent.isCancelled()) {
            return true;
        }

        player.setGameMode(gm);

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_GAMEMODE_CHANGED, true, mode));
            if (!sender.equals(player)) {
                sender.sendMessage(ess.getMessages().getMsg(Message.CMD_GAMEMODE_OTHER, true, mode, player.getDisplayName()));
            }
        }
        return true;
    }
}
