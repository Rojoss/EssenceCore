/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://essencemc.org>
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

package org.essencemc.essencecore.commands.arguments;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.commands.arguments.internal.ArgumentParseResult;
import org.essencemc.essencecore.commands.arguments.internal.ArgumentRequirement;
import org.essencemc.essencecore.commands.arguments.internal.CmdArgument;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldArgument extends CmdArgument {

    public WorldArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        Server server = EssenceCore.inst().getServer();

        World world = null;
        if (NumberUtil.getInt(arg) != null) {
            world = server.getWorlds().size() < NumberUtil.getInt(arg) ? null : server.getWorlds().get(NumberUtil.getInt(arg));
        } else if (arg.split("-").length == 5) {
            world = server.getWorld(UUID.fromString(arg));
        } else {
            world = server.getWorld(arg);
        }

        result.setValue(world);
        result.success = true;
        if (world == null) {
            result.success = false;
            sender.sendMessage(Message.INVALID_WORLD.msg().getMsg(true, arg));
        }
        return result;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message) {
        List<String> worldNames = new ArrayList<String>();
        List<World> worlds = EssenceCore.inst().getServer().getWorlds();
        for (World world : worlds) {
            String name = world.getName();
            if (StringUtil.startsWithIgnoreCase(name, message)) {
                worldNames.add(name);
            }
        }
        return worldNames;
    }

    @Override
    public String getDescription() {
        return Message.ARG_WORLD.msg().getMsg(false);
    }
}
