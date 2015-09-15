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

package info.mcessence.essence.commands.player_status;

import info.mcessence.essence.Essence;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.MappedListArgument;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
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

        PlayerGameModeChangeEvent gamemodeChangeEvent = new PlayerGameModeChangeEvent(player, gm);
        ess.getServer().getPluginManager().callEvent(gamemodeChangeEvent);
        if (gamemodeChangeEvent.isCancelled()) {
            return true;
        }

        player.setGameMode(gm);

        if (!result.hasModifier("-s")) {
            player.sendMessage(Message.CMD_GAMEMODE_CHANGED.msg().getMsg(true, mode));
            if (!sender.equals(player)) {
                sender.sendMessage(Message.CMD_GAMEMODE_OTHER.msg().getMsg(true, mode, player.getDisplayName()));
            }
        }
        return true;
    }
}
