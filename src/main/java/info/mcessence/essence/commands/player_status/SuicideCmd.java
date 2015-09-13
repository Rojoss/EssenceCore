package info.mcessence.essence.commands.player_status;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class SuicideCmd extends EssenceCommand {

    public SuicideCmd(Essence ess, String label, String description, String permission, List<String> aliases) {
        super(ess, label, description, permission, aliases);

        cmdArgs = new CmdArgument[] {};

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ess.getMessages().getMsg(Message.CMD_PLAYER_ONLY, true));
            return true;
        }

        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }

        Player player = (Player)sender;
        double health = player.getHealth();

        player.setHealth(0);
        player.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, health));

        return true;
    }

}
