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
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InvseeCmd extends EssenceCommand {

    public InvseeCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new PlayerArgument("player", ArgumentRequirement.REQUIRED, "")
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_PLAYER_ONLY, true));
            return true;
        }

        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        Player player = (Player)sender;
        Player invOwner = (Player)result.getValue(0).getValue();

        // TODO: Find a way to call InventoryOpenEvent.

        if (hasPermission(invOwner, "exempt")) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_INVSEE_EXEMPT, true, invOwner.getName()));
            return true;
        }

        player.openInventory(invOwner.getInventory());

        if (!result.hasModifier("-s")) {
            player.sendMessage(ess.getMessages().getMsg(Message.CMD_INVSEE, true, invOwner.getName()));
        }

        return true;
    }

}