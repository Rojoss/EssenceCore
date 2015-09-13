package info.mcessence.essence.nms;

import com.mojang.authlib.GameProfile;
import org.bukkit.block.Block;
import org.bukkit.inventory.meta.SkullMeta;

public interface NmsUtil {

    /**
     * Set a specific skin texture to the given skull block.
     * If the block isn't a skull nothing will happen!
     */
    public void setSkullUrl(String skinUrl, Block block);

    /**
     * Set a specific skin texture to the given skull meta.
     * It will return the SkullMeta with the texture applied if it was valid.
     */
    public SkullMeta setSkullUrl(String skinUrl, SkullMeta meta);

}
