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

package info.mcessence.essence.commands.player;

import info.mcessence.essence.Essence;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.StringArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.cmd_options.IntOption;
import info.mcessence.essence.cmd_options.StringOption;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NicknameCmd extends EssenceCommand {

    public NicknameCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        addCommandOption("prefix", new StringOption("~", Message.OPT_NICK_PREFIX.msg()), false);
        addCommandOption("min-characters", new IntOption(3, Message.OPT_NICK_MIN_CHARS.msg()), false);
        addCommandOption("max-characters", new IntOption(16, Message.OPT_NICK_MAX_CHARS.msg()), false);

        cmdArgs = new CmdArgument[] {
                new StringArgument("nickname", ArgumentRequirement.REQUIRED, "", (Integer)cmdOptions.get("min-characters").getValue(), (Integer)cmdOptions.get("max-characters").getValue()),
                new PlayerArgument("player", ArgumentRequirement.REQUIRED_CONSOLE, "others")
        };

        addModifier("-r", Message.MOD_NICK_REMOVE.msg());

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
            player.sendMessage(Message.CMD_NICK_CHANGED.msg().getMsg(true, hasPermission(sender, "color") ? player.getDisplayName() : Util.removeColor(player.getDisplayName())));
            if (!sender.equals(player)) {
                sender.sendMessage(Message.CMD_NICK_OTHER.msg().getMsg(true, hasPermission(sender, "color") ? player.getDisplayName() : Util.removeColor(player.getDisplayName()), player.getName()));
            }
        }
        return true;
    }

}
