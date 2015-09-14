package info.mcessence.essence.nms.v1_8_R1;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import info.mcessence.essence.nms.api.ISkull;
import info.mcessence.essence.util.Util;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.TileEntitySkull;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;


/**
 * Handles player skull data
 */
public class SkullUtil_v1_8_R1 implements ISkull {

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
        Skull skullData = (Skull)block.getState();
        skullData.setSkullType(SkullType.PLAYER);

        TileEntitySkull skullTile = (TileEntitySkull)((CraftWorld)block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
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

}
