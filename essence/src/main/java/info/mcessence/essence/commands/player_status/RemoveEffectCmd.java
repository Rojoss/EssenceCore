/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
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

package info.mcessence.essence.commands.player_status;


import info.mcessence.essence.Essence;
import info.mcessence.essence.aliases.AliasType;
import info.mcessence.essence.aliases.Aliases;
import info.mcessence.essence.message.Message;
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

        Map<String, List<String>> effects = Aliases.getAliasesMap(AliasType.POTION_EFFECT);
        effects.put("ALL", Arrays.asList("all", "*"));

        cmdArgs = new CmdArgument[] {
                new MappedListArgument("effect", ArgumentRequirement.REQUIRED, "", effects),
                new PlayerArgument("player", ArgumentRequirement.OPTIONAL, "others")
        };

        addModifier("-n", Message.MOD_REMOVEEFFECT_NEGATIVE.msg());
        addModifier("-p", Message.MOD_REMOVEEFFECT_POSITIVE.msg());

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
                    player.sendMessage(Message.CMD_REMOVEEFFECT.msg().getMsg(true, effectType.toLowerCase()));
                } else {
                    player.sendMessage(Message.CMD_REMOVEEFFECT_ALL.msg().getMsg(true));
                }
            } else {
                if (single == true) {
                    player.sendMessage(Message.CMD_REMOVEEFFECT_OTHER.msg().getMsg(true, player.getName(), effectType.toLowerCase()));
                } else {
                    player.sendMessage(Message.CMD_REMOVEEFFECT_OTHER_ALL.msg().getMsg(true));
                }
            }
        }

        return true;
    }

}
