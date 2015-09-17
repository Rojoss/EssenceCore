package info.mcessence.essence.nms.v1_8_R2.util;

import net.minecraft.server.v1_8_R2.Packet;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Handles packet sending for v1_8R1
 */
public class PacketHandler {

    public static void sendPacket(Player player, Packet packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
