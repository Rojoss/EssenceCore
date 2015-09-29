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

package info.mcessence.essence.arguments;

import info.mcessence.essence.Essence;
import info.mcessence.essence.arguments.internal.Argument;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.util.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WorldArg extends Argument {

    public WorldArg() {
        super();
    }

    public WorldArg(String name) {
        super(name);
    }

    public WorldArg(World defaultValue) {
        super(defaultValue);
    }

    public WorldArg(String name, World defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().getMsg(true, name) : Message.NO_ARG_VALUE.msg().getMsg(true);
            success = false;
            return success;
        }

        Server server = Essence.inst().getServer();

        World world = null;
        if (NumberUtil.getInt(input) != null) {
            world = server.getWorlds().size() < NumberUtil.getInt(input) ? null : server.getWorlds().get(NumberUtil.getInt(input));
        } else if (input.split("-").length == 5) {
            world = server.getWorld(UUID.fromString(input));
        } else {
            world = server.getWorld(input);
        }

        if (world == null) {
            success = false;
            error = Message.INVALID_WORLD.msg().getMsg(true, input);
        } else {
            success = true;
        }

        value = world;
        return success;
    }

    @Override
    public WorldArg clone() {
        return new WorldArg(name, defaultValue == null ? null : (World)defaultValue);
    }

    @Override
    public String getDescription() {
        return Message.ARG_WORLD.msg().getMsg(false);
    }

    @Override
    public Class getRawClass() {
        return World.class;
    }
}
