package info.mcessence.essence.commands;

import info.mcessence.essence.Essence;
import info.mcessence.essence.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReflectCommand extends Command {

    private EssenceCommand cmd = null;

    protected ReflectCommand(String command) {
        super(command);
    }

    public void setExecutor(EssenceCommand cmd) {
        this.cmd = cmd;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (cmd != null) {
            if (!cmd.hasPermission(sender)) {
                sender.sendMessage(Essence.inst().getMessages().getMsg(Message.NO_PERM, true, cmd.getPermission()));
                return true;
            }
            return cmd.onCommand(sender, this, commandLabel, args);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String message, String[] args) {
        if (cmd != null) {
            return cmd.onTabComplete(sender, this, message, args);
        }
        return null;
    }
}
