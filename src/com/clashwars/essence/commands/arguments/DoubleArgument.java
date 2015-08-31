package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import org.bukkit.command.CommandSender;

public class DoubleArgument extends CmdArgument {

    protected double minValue;
    protected double maxValue;
    protected boolean clampValue;

    public DoubleArgument(ArgumentRequirement requirement, String permission, double minValue, double maxValue, boolean clampValue) {
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

        double value = NumberUtil.getDouble(arg);
        if (value != -1 && value < minValue) {
            if (clampValue) {
                value = minValue;
            } else {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NUMBER_TOO_LOW, true, arg, Double.toString(minValue)));
                result.success = false;
                return result;
            }
        }
        if (value != -1 && value > maxValue) {
            if (clampValue) {
                value = maxValue;
            } else {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NUMBER_TOO_HIGH, true, arg, Double.toString(maxValue)));
                result.success = false;
                return result;
            }
        }

        result.setValue(value);
        result.success = true;

        return result;
    }
    
}
