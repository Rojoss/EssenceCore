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

package org.essencemc.essencecore.nms;

import org.bukkit.block.Block;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Interface for skull player data handling
 */
public interface ISkull {

    /**
     * Set a specific skin texture to the given skull block.
     * If the block isn't a skull nothing will happen!
     *
     * @param skinUrl The textures.minecraft.net skin url for the skull.
     * @param block The skull block on which the skin has to be applied on.
     *              If the block is not {@link org.bukkit.Material#SKULL} AND @{@link org.bukkit.SkullType#PLAYER} then nothing will happen.
     */
    void setSkullUrl(String skinUrl, Block block);

    /**
     * Set a specific skin texture to the given skull meta.
     * It will return the SkullMeta with the texture applied if it was valid.
     *
     * @param skinUrl The textures.minecraft.net skin url for the skull.
     * @param meta The skull meta of a skull block.
     *
     * @return SkullMeta
     */
    SkullMeta setSkullUrl(String skinUrl, SkullMeta meta);

    /**
     * Get the texture url code from the skull meta.
     *
     * @param meta The skull meta of a skull block.
     *
     * @return The textures.minecraft.net skin url for the skull.
     */
    String getSkullUrl(SkullMeta meta);
}
