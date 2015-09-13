package info.mcessence.essence.cmd_arguments;

import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.util.NumberUtil;
import org.bukkit.command.CommandSender;

public class IntArgument extends CmdArgument {

    protected Integer minValue;
    protected Integer maxValue;
    protected boolean clampValue;

    public IntArgument(String name, ArgumentRequirement requirement, String permission, Integer minValue, Integer maxValue, boolean clampValue) {
        super(name, requirement, permission);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.clampValue = clampValue;
    }

    public IntArgument(String name, ArgumentRequirement requirement, String permission) {
        super(name, requirement, permission);
        this.minValue = null;
        this.maxValue = null;
        this.clampValue = false;
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
        if (minValue != null && value < minValue) {
            if (clampValue) {
                value = minValue;
            } else {
                sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NUMBER_TOO_LOW, true, arg, Integer.toString(minValue)));
                result.success = false;
                return result;
            }
        }
        if (maxValue != null && value > maxValue) {
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
