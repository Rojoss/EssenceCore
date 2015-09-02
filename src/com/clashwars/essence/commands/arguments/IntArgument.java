package com.clashwars.essence.commands.arguments;

import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentRequirement;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.util.NumberUtil;
import org.bukkit.command.CommandSender;

public class IntArgument extends CmdArgument {

    protected int minValue;
    protected int maxValue;
    protected boolean clampValue;

    public IntArgument(String name, ArgumentRequirement requirement, String permission, int minValue, int maxValue, boolean clampValue) {
        super(name, requirement, permission);
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

        Integer value = NumberUtil.getInt(arg);
        if (value == null) {
            sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NOT_A_NUMBER, true, arg));
            result.success = false;
            return result;
        }
        if (minValue != -1 && value < minValue) {
            if (clampValue) {
                value = minValue;
            } else {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NUMBER_TOO_LOW, true, arg, Integer.toString(minValue)));
                result.success = false;
                return result;
            }
        }
        if (maxValue != -1 && value > maxValue) {
            if (clampValue) {
                value = maxValue;
            } else {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NUMBER_TOO_HIGH, true, arg, Integer.toString(maxValue)));
                result.success = false;
                return result;
            }
        }

        result.setValue(value);
        result.success = true;

        return result;
    }
    
}
