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

package info.mcessence.essence.nms.v1_8_R2;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import info.mcessence.essence.nms.ISkull;
import info.mcessence.essence.nms.util.Util;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.TileEntitySkull;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;

/**
 * Handles player skull data for v1_8R2
 */
public class SkullUtil_1_8_R2 implements ISkull {

    /**
     * Set a specific skin texture to the given skull block.
     * If the block isn't a skull nothing will happen!
     *
     * @param skinUrl
     * @param block
     */
    @Override
    public void setSkullUrl(String skinUrl, Block block) {
        if (block.getType() != Material.SKULL) {
            return;
        }

        block.setType(Material.SKULL);
        Skull skullData = (Skull) block.getState();
        skullData.setSkullType(SkullType.PLAYER);

        TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", new String(Util.getSkullTexture(skinUrl))));
        skullTile.setGameProfile(profile);
        block.getState().update(true);
    }

    /**
     * Set a specific skin texture to the given skull meta.
     * It will return the SkullMeta with the texture applied if it was valid.
     *
     * @param skinUrl
     * @param meta
     */
    @Override
    public SkullMeta setSkullUrl(String skinUrl, SkullMeta meta) {
        if (meta == null) {
            return meta;
        }

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", new String(Util.getSkullTexture(skinUrl))));
        Field profileField = null;

        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return meta;
    }

    @Override
    public String getSkullUrl(SkullMeta meta) {
        if (meta == null) {
            return null;
        }

        String skinUrl = null;
        try {
            //Get the profile properties.
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            Collection<Property> properties = ((GameProfile) profileField.get(meta)).getProperties().get("textures");

            //Get the texture property.
            for (Property prop : properties) {
                if (prop.getName().equalsIgnoreCase("textures")) {
                    //Convert the encoded texture string to json.
                    String jsonString = new String(Base64.decodeBase64(prop.getValue()));
                    JSONObject json = (JSONObject)new JSONParser().parse(jsonString);

                    //Get the texture code from JSON.
                    json = (JSONObject)json.get("textures");
                    json = (JSONObject)json.get("SKIN");
                    skinUrl = (String)json.get("url");

                    //Get the last bit of the url to get the short code.
                    String[] split = skinUrl.split("/");
                    skinUrl = split[split.length - 1];
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return skinUrl;
    }

}
