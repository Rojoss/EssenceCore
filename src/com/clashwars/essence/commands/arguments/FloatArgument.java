package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import org.bukkit.command.CommandSender;

public class FloatArgument extends CmdArgument {

    protected float minValue;
    protected float maxValue;
    protected boolean clampValue;

    public FloatArgument(ArgumentRequirement requirement, String permission, float minValue, float maxValue, boolean clampValue) {
        super(requirement, permission);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.clampValue = clampValue;
    }


    @Override
    public ArgumentParseResult parse(EssenceCommand cmd, CommandSender sender, String arg) {
        ArgumentParseResult result = super.parse(cmd, sender, arg);
        if (!result.success) {
            return result;
        }

        Float value = NumberUtil.getFloat(arg);
        if (value == null) {
            sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NOT_A_NUMBER, true, arg));
            result.success = false;
            return result;
        }
        if (value != -1 && value < minValue) {
            if (clampValue) {
                value = minValue;
            } else {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NUMBER_TOO_LOW, true, arg, Float.toString(minValue)));
                result.success = false;
                return result;
            }
        }
        if (value != -1 && value > maxValue) {
            if (clampValue) {
                value = maxValue;
            } else {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NUMBER_TOO_HIGH, true, arg, Float.toString(maxValue)));
                result.success = false;
                return result;
            }
        }

        result.setValue(value);
        result.success = true;

        return result;
    }
    
}
