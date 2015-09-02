package com.clashwars.essence.config.data;

import com.clashwars.essence.config.EasyConfig;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warps extends EasyConfig {

    public Map<String, Location> warps = new HashMap<String, Location>();

    public Warps(String fileName) {
        this.setFile(fileName);
        load();
    }

    /**
     * Get a warp by name.
     * The name is not case sensitive.
     * It will return null if no warp is found with the given name.
     */
    public Location getWarp(String name) {
        for (Map.Entry<String, Location> warp : warps.entrySet()) {
            if (warp.getKey().equalsIgnoreCase(name)) {
                return warp.getValue();
            }
        }
        return null;
    }

    /** Get the map with all the warps. */
    public Map<String, Location> getWarps() {
        return warps;
    }

    /** Get a list of all the warp names */
    public List<String> getWarpNames() {
        return new ArrayList<String>(warps.keySet());
    }

    /**
     * Set a warp with a name and location.
     * If a warp with this location already exisists it will overwrite the location.
     */
    public void setWarp(String name, Location location) {
        warps.put(name, location);
        save();
    }

    /**
     * Try to delete a warp with the specified name.
     * The name is not case sensitive.
     * It will return true if it deleted a warp and false if not.
     */
    public boolean delWarp(String name) {
        for (Map.Entry<String, Location> warp : warps.entrySet()) {
            if (warp.getKey().equalsIgnoreCase(name)) {
                warps.remove(warp.getKey());
                save();
                return true;
            }
        }
        return false;
    }


}
