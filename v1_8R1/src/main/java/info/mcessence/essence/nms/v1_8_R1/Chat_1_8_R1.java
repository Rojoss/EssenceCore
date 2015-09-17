package info.mcessence.essence.nms.v1_8_R1;

import info.mcessence.essence.nms.IChat;
import info.mcessence.essence.nms.v1_8_R1.util.PacketHandler;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.bukkit.entity.Player;

/**
 * Handles the chat and actionbar for v1_8R1
 */
public class Chat_1_8_R1 implements IChat {

    // TODO Make a ChatBuilder to allow for more flexible building

    // TODO Implement the ChatBuilder

    private PacketPlayOutChat packet =  null;
    private IChatBaseComponent icbc = null;
    /**
     * Send actionbar to the player
     *
     * @param message
     * @param player
     */
    @Override
    public void sendActionbar(String message, Player player) {
        icbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        
        packet = new PacketPlayOutChat(icbc, (byte) 2);

        PacketHandler.sendPacket(player, packet);
    }

    /**
     * Send actionbar to the players
     *
     * @param message
     * @param players
     */
    @Override
    public void sendActionbar(String message, Player[] players) {
        for (Player p = players[0]; p == players[players.length - 1];) {
            sendActionbar(message, p);
        }
    }
}
