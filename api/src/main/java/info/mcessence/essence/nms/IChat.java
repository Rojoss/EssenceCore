package info.mcessence.essence.nms;

import org.bukkit.entity.Player;

/**
 * Interface for handling actionbars and chat
 */
public interface IChat {

    /**
     * Send actionbar to the player
     */
    void sendActionbar(String message, Player player);

    /**
     * Send actionbar to the players
     */
    void sendActionbar(String message, Player[] players);
}
