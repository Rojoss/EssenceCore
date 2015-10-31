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

package org.essencemc.essencecore.nms.nbt;

import org.bukkit.entity.Entity;
import org.essencemc.essencecore.nms.nbt.entity.*;
import org.essencemc.essencecore.nms.v1_8R3.util.NBTUtil;

/**
 *
 */
public class NBTTagEntity_1_8_R3 implements NBTTagEntity {
    @Override
    public NBTTagEntity setByteTag(Entity entity, EntityByteTag byteTag, byte value) {
        NBTUtil.getNBTTagCompound(entity).setByte(byteTag.getNbtTagName(), value);
        return this;
    }

    @Override
    public byte getByteTag(Entity entity, EntityByteTag byteTag) {
        return NBTUtil.getNBTTagCompound(entity).getByte(byteTag.getNbtTagName());
    }

    @Override
    public NBTTagEntity setShortTag(Entity entity, EntityShortTag shortTag, short value) {
        NBTUtil.getNBTTagCompound(entity).setShort(shortTag.getNbtTagName(), value);
        return this;
    }

    @Override
    public short getShortTag(Entity entity, EntityShortTag shortTag) {
        return NBTUtil.getNBTTagCompound(entity).getShort(shortTag.getNbtTagName());
    }

    @Override
    public NBTTagEntity setIntTag(Entity entity, EntityIntTag intTag, int value) {
        NBTUtil.getNBTTagCompound(entity).setInt(intTag.getNbtTagName(), value);
        return this;
    }

    @Override
    public int getIntTag(Entity entity, EntityIntTag intTag) {
        return NBTUtil.getNBTTagCompound(entity).getInt(intTag.getNbtTagName());
    }

    @Override
    public NBTTagEntity setLongTag(Entity entity, EntityLongTag longTag, long value) {
        NBTUtil.getNBTTagCompound(entity).setLong(longTag.getNbtTagName(), value);
        return this;
    }

    @Override
    public long getLongTag(Entity entity, EntityLongTag longTag) {
        return NBTUtil.getNBTTagCompound(entity).getLong(longTag.getNbtTagName());
    }

    @Override
    public NBTTagEntity setFloatTag(Entity entity, EntityFloatTag floatTag, float value) {
        NBTUtil.getNBTTagCompound(entity).setFloat(floatTag.getNbtTagName(), value);
        return this;
    }

    @Override
    public float getFloatTag(Entity entity, EntityFloatTag floatTag) {
        return NBTUtil.getNBTTagCompound(entity).getFloat(floatTag.getNbtTagName());
    }

    @Override
    public NBTTagEntity setStringTag(Entity entity, EntityStringTag stringTag, String value) {
        NBTUtil.getNBTTagCompound(entity).setString(stringTag.getNbtTagName(), value);
        return this;
    }

    @Override
    public String getStringTag(Entity entity, EntityStringTag stringTag) {
        return NBTUtil.getNBTTagCompound(entity).getString(stringTag.getNbtTagName());
    }
}
