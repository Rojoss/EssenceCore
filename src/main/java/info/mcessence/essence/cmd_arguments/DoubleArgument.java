package info.mcessence.essence.cmd_arguments;

import info.mcessence.essence.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.util.NumberUtil;
import org.bukkit.command.CommandSender;

public class DoubleArgument extends CmdArgument {

    protected double minValue;
    protected double maxValue;
    protected boolean clampValue;

    public DoubleArgument(String name, ArgumentRequirement requirement, String permission, double minValue, double maxValue, boolean clampValue) {
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

        Double value = NumberUtil.getDouble(arg);
        if (value == null) {
            sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.NOT_A_NUMBER, true, arg));
            result.success = false;
            return result;
        }
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
