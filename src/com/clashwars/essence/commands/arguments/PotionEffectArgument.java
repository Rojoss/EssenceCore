package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectArgument extends CmdArgument {

    public PotionEffectArgument(ArgumentRequirement requirement, String permission) {
        super(requirement, permission);
    }

    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        //TODO: Get effect by custom configured name.
        PotionEffectType type = PotionEffectType.getByName(arg);
        if (type == null) {
            result.success = false;
            return result;
        } else {
            result.success = true;
        }

        result.setValue(type);
        return result;
    }
}
