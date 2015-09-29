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

package org.essencemc.essencecore.arguments;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.Message;

import java.util.UUID;

public class PlayerArg extends Argument {

    public PlayerArg() {
        super();
    }

    public PlayerArg(String name) {
        super(name);
    }

    public PlayerArg(Player defaultValue) {
        super(defaultValue);
    }

    public PlayerArg(String name, Player defaultValue) {
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

        Player player = null;
        String[] components = input.split("-");
        if (components.length == 5) {
            player = Bukkit.getPlayer(UUID.fromString(input));
        } else {
            player = Bukkit.getPlayer(input);
        }
        //TODO: Get player by nick/display name

        if (player == null) {
            success = false;
            error = Message.INVALID_PLAYER.msg().getMsg(true, input);
        } else {
            success = true;
        }

        value = player;
        return success;
    }

    @Override
    public PlayerArg clone() {
        return new PlayerArg(name, defaultValue == null ? null : (Player)defaultValue);
    }

    @Override
    public String getDescription() {
        return Message.ARG_PLAYER.msg().getMsg(false);
    }

    @Override
    public Class getRawClass() {
        return Player.class;
    }
}
