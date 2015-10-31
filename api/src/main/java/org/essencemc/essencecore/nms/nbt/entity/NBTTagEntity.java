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

package org.essencemc.essencecore.nms.nbt.entity;

import org.bukkit.entity.Entity;

/**
 *
 */
public interface NBTTagEntity {

    NBTTagEntity setByteTag(Entity entity, EntityByteTag byteTag, byte value);

    byte getByteTag(Entity entity, EntityByteTag byteTag);

    NBTTagEntity setShortTag(Entity entity, EntityShortTag shortTag, short value);

    short getShortTag(Entity entity, EntityShortTag shortTag);

    NBTTagEntity setIntTag(Entity entity, EntityIntTag intTag, int value);

    int getIntTag(Entity entity, EntityIntTag intTag);

    NBTTagEntity setLongTag(Entity entity, EntityLongTag longTag, long value);

    long getLongTag(Entity entity, EntityLongTag longTag);

    NBTTagEntity setFloatTag(Entity entity, EntityFloatTag floatTag, float value);

    float getFloatTag(Entity entity, EntityFloatTag floatTag);

    NBTTagEntity setStringTag(Entity entity, EntityStringTag stringTag, String value);

    String getStringTag(Entity entity, EntityStringTag stringTag);
}
