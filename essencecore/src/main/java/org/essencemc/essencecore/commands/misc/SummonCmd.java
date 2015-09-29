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

package org.essencemc.essencecore.commands.misc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.cmd_arguments.StringArgument;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentParseResults;
import org.essencemc.essencecore.cmd_arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.cmd_arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.parsers.EntityParser;
import org.essencemc.essencecore.util.Util;

import java.util.List;

public class SummonCmd extends EssenceCommand {

    public SummonCmd(EssenceCore ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        cmdArgs = new CmdArgument[] {
                new StringArgument("entity[(data)][(>|-)entity(>|-)...] [amount] [location]", ArgumentRequirement.REQUIRED, ""),
        };

        addModifier("-r", Message.MOD_RIDE_ENTITY.msg());

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        EntityParser entityParser = new EntityParser(Util.implode(args, " "), sender instanceof Player ? ((Player)sender).getLocation() : null, false);
        if (!entityParser.isValid()) {
            sender.sendMessage(entityParser.getError());
            return true;
        }

        if (result.hasModifier("-r") && sender instanceof Player) {
            entityParser.getEntities().get(0).get(entityParser.getEntities().size()-1).setPassenger((Player)sender);
        }

        if (!result.hasModifier("-s")) {
            sender.sendMessage(Message.CMD_SUMMON.msg().getMsg(true));
        }
        return true;
    }

}
