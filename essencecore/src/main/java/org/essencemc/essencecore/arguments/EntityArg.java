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
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.entity.EEntity;
import org.essencemc.essencecore.entity.EItem;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.parsers.EntityParser;
import org.essencemc.essencecore.parsers.ItemParser;
import org.essencemc.essencecore.util.NumberUtil;

import java.util.List;
import java.util.UUID;

public class EntityArg extends Argument {

    public EntityArg() {
        super();
    }

    public EntityArg(String name) {
        super(name);
    }

    public EntityArg(EEntity defaultValue) {
        super(defaultValue);
    }

    public EntityArg(String name, EEntity defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().parseArgs(name) : Message.NO_ARG_VALUE.msg();
            success = false;
            return success;
        }

        Integer id = NumberUtil.getInt(input);
        UUID uuid = null;
        if (input.length() == 36 && input.split("-").length == 5) {
            uuid = UUID.fromString(input);
        }

        for (World world : Bukkit.getWorlds()) {
            List<Entity> entities = world.getEntities();
            for (Entity e : entities) {
                if ((uuid != null && e.getUniqueId().equals(uuid)) || (id != null && e.getEntityId() == id)) {
                    value = new EEntity(e);
                    success = true;
                    return success;
                }
            }
        }


        if (input.startsWith("\"") && input.endsWith("\"")) {
            input = input.substring(1, input.length()-1);
        }

        EntityParser parser = new EntityParser(input, null, false);
        if (!parser.isValid()) {
            error = parser.getError();
            success = false;
            return success;
        }

        if (parser.getEntities().size() < 1 || parser.getEntities().get(0).size() < 1) {
            error = Message.INVALID_ENTITY.msg().parseArgs(input);
            success = false;
            return success;
        }

        value = parser.getEntities().get(0).get(0);
        return success;
    }

    @Override
    public EntityArg clone() {
        return new EntityArg(name, defaultValue == null ? null : (EEntity)defaultValue);
    }

    @Override
    public String toString() {
        //Might wanna have this convert an EEntity to string instead of the entity UUId.
        return value == null ? (defaultValue == null ? null : ((EEntity) defaultValue).getUUID().toString()) : ((EEntity) value).getUUID().toString();
    }

    @Override
    public EText getDescription() {
        return Message.ARG_ENTITY.msg();
    }

    @Override
    public Class getRawClass() {
        return EItem.class;
    }

    public static EEntity Parse(String input) {
        EntityArg arg = new EntityArg();
        if (arg.parse(input)) {
            return (EEntity)arg.value;
        }
        return null;
    }

    public static String Parse(EEntity input) {
        if (input == null) {
            return null;
        }
        //Might wanna have this convert an EEntity to string instead of the entity UUId.
        return input.getUUID().toString();
    }
}
