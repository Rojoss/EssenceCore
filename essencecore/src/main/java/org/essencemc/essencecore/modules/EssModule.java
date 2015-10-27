package org.essencemc.essencecore.modules;

import org.bukkit.plugin.Plugin;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.commands.EssenceCommand;

import java.util.ArrayList;
import java.util.List;

public class EssModule {

    protected final Plugin plugin;
    protected final EssenceCore ess = EssenceCore.inst();

    private List<Class<? extends EssModule>> dependencies = new ArrayList<Class<? extends EssModule>>();
    private List<Class<? extends EssModule>> softDependencies = new ArrayList<Class<? extends EssModule>>();

    public EssModule(Plugin plugin) {
        this.plugin = plugin;
    }

    protected void addDependency(Class<? extends EssModule> dependency) {
        if (!dependencies.contains(dependency)) {
            dependencies.add(dependency);
        }
    }

    protected void addSoftDependency(Class<? extends EssModule> dependency) {
        if (!softDependencies.contains(dependency)) {
            softDependencies.add(dependency);
        }
    }

    public boolean checkDependencies() {
        for (Class<? extends EssModule> c : dependencies) {
            if (c.isAssignableFrom(Module.class)) {
                if (EssenceCore.inst().getModules().getModule((Class<? extends Module>)c) == null) {
                    return false;
                }
            } else if (c.isAssignableFrom(EssenceCommand.class)) {
                if (EssenceCore.inst().getCommands().getCommand((Class<? extends EssenceCommand>) c) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean checkDependency(Class<? extends EssModule> dependency) {
        if (dependency.isAssignableFrom(Module.class)) {
            if (EssenceCore.inst().getModules().getModule((Class<? extends Module>)dependency) == null) {
                return false;
            }
        } else if (dependency.isAssignableFrom(EssenceCommand.class)) {
            if (EssenceCore.inst().getCommands().getCommand((Class<? extends EssenceCommand>)dependency) == null) {
                return false;
            }
        }
        return true;
    }

    protected Module getModule(Class<? extends Module> module) {
        return EssenceCore.inst().getModules().getModule(module);
    }

    protected EssenceCommand getCmd(Class<? extends EssenceCommand> cmd) {
        return EssenceCore.inst().getCommands().getCommand(cmd);
    }
}
