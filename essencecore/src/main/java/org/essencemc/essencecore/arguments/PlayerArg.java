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

package org.essencemc.essencecore.arguments;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.message.Param;
import org.essencemc.essencecore.util.Util;

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
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().params(Param.P("arg", name)) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }

        Player player = null;
        String[] components = input.split("-");
        if ( input.length() == 36 && components.length == 5 && !components[0].isEmpty()) {
            player = Bukkit.getPlayer(UUID.fromString(input));
        } else {
            player = Bukkit.getPlayer(input);
        }
        if (player == null) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Util.stripAllColor(p.getDisplayName()).equalsIgnoreCase(input) || Util.stripAllColor(p.getPlayerListName()).equalsIgnoreCase(input)) {
                    player = p;
                    break;
                }
            }
        }

        if (player == null) {
            success = false;
            error = Message.INVALID_PLAYER.msg().params(Param.P("input", input));
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
    public String toString() {
        return PlayerArg.Parse(value == null ? (defaultValue == null ? null : (Player) defaultValue) : (Player) value);
    }

    @Override
    public EText getDescription() {
        return Message.ARG_PLAYER.msg();
    }

    @Override
    public Class getRawClass() {
        return Player.class;
    }

    public static Player Parse(String input) {
        PlayerArg arg = new PlayerArg();
        if (arg.parse(input)) {
            return (Player)arg.value;
        }
        return null;
    }

    public static String Parse(Player input) {
        if (input == null) {
            return null;
        }
        return input.getName();
    }
}
