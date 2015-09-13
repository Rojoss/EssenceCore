package info.mcessence.essence.commands.player_status;


import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.MappedListArgument;
import info.mcessence.essence.cmd_arguments.PlayerArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class RemoveEffectCmd extends EssenceCommand {

    public RemoveEffectCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        Map<String, List<String>> effects = new HashMap<String, List<String>>();
        effects.put("BLINDNESS", Arrays.asList("blindness"));
        effects.put("FIRE_RESISTANCE", Arrays.asList("fire_resistance"));
        effects.put("DAMAGE_RESISTANCE", Arrays.asList("damage_resistance", "resistance"));
        effects.put("FAST_DIGGING", Arrays.asList("fast_digging", "haste"));
        effects.put("ALL", Arrays.asList("all", "*"));

        cmdArgs = new CmdArgument[] {
                new MappedListArgument("effect", ArgumentRequirement.REQUIRED, "", effects),
                new PlayerArgument("player", ArgumentRequirement.OPTIONAL, "others")
        };

        addModifier("-n", Message.MOD_REMOVEEFFECT_NEGATIVE);
        addModifier("-p", Message.MOD_REMOVEEFFECT_POSITIVE);

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        String effectType = (String)result.getValue(0).getValue();
        Player player = result.getValue(1).getValue() == null ? (Player)sender : (Player)result.getValue(1).getValue();
        boolean single = true;

        if (effectType.equals("ALL")) single = false;

        if (single == false) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (result.hasModifier("-n") && !Util.isNegativePotionEffect(effect.getType())) continue;
                if (result.hasModifier("-p") && Util.isNegativePotionEffect(effect.getType())) continue;
                player.removePotionEffect(effect.getType());
            }
        } else {
            player.removePotionEffect(PotionEffectType.getByName(effectType));
        }

        if (!result.hasModifier("-s")) {
            if (sender.equals(player)) {
                if (single == true) {
                    player.sendMessage(ess.getMessages().getMsg(Message.CMD_REMOVEEFFECT, true, effectType.toLowerCase()));
                } else {
                    player.sendMessage(ess.getMessages().getMsg(Message.CMD_REMOVEEFFECT_ALL, true));
                }
            } else {
                if (single == true) {
                    player.sendMessage(ess.getMessages().getMsg(Message.CMD_REMOVEEFFECT_OTHER, true, player.getName(), effectType.toLowerCase()));
                } else {
                    player.sendMessage(ess.getMessages().getMsg(Message.CMD_REMOVEEFFECT_OTHER_ALL, true));
                }
            }
        }

        return true;
    }

}
