/*
 *
 *  * The MIT License (MIT)
 *  *
 *  * Copyright (c) 2015 Essence <http://www.mc-essence.info>
 *  * Copyright (c) 2015 contributors
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */

package info.mcessence.essence.nms.util;

import org.apache.commons.codec.binary.Base64;

/**
 * @author KingGoesGaming
 */
public class Util {

    /**
     * Get a Byte[] array with the texture skull url
     * The input string can be a textures.minecraft.net link, the code from the link only or a Base64 encoded string.
     * http://heads.freshcoal.com/maincollection.php
     */
    public static byte[] getSkullTexture(String input) {
        if (input.endsWith("=")) {
            //Encoded texture.
            //eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU1ZDYxMWE4NzhlODIxMjMxNzQ5YjI5NjU3MDhjYWQ5NDI2NTA2NzJkYjA5ZTI2ODQ3YTg4ZTJmYWMyOTQ2In19fQ==
            return input.getBytes();
        } else {
            if (input.contains("/")) {
                //Whole texture url from textures.minecraft.net
                //http://textures.minecraft.net/texture/955d611a878e821231749b2965708cad942650672db09e26847a88e2fac2946
                String[] split = input.split("/");
                input = split[split.length-1];
            }
            //Texture code split from the url.
            //955d611a878e821231749b2965708cad942650672db09e26847a88e2fac2946
            input = "http://textures.minecraft.net/texture/" + input;
            return Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", input).getBytes());
        }
    }
}
