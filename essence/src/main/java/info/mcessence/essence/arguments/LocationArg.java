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

package info.mcessence.essence.arguments;

import info.mcessence.essence.arguments.internal.Argument;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.util.NumberUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Map;

public class LocationArg extends Argument {

    public LocationArg() {
        super();
    }

    public LocationArg(String name) {
        super(name);
    }

    public LocationArg(Location defaultValue) {
        super(defaultValue);
    }

    public LocationArg(String name, Location defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean parse(String input) {
        success = true;
        if (input == null || input.isEmpty()) {
            error = hasName() ? Message.NO_ARG_VALUE_NAME.msg().getMsg(true, name) : Message.NO_ARG_VALUE.msg().getMsg(true);
            success = false;
            return success;
        }
        Location location = new Location(null, 0,0,0, 0f,0f);

        //Get location from player. @{Player name/uuid}
        if (input.startsWith("@")) {
            PlayerArg pArg = new PlayerArg();
            pArg.parse(input.substring(1));
            if (!pArg.isValid()) {
                error = pArg.getError();
                success = false;
                return success;
            }
            location = ((Player)pArg.getValue()).getLocation();
            value = location;
            return success;
        }

        //Split string by semicolon like x,y,z:world or x,y,z:player
        String[] split = input.split(":");
        if (split.length > 1) {
            String data = split[1];
            if (data.startsWith("@")) {
                //Get player instead of world.
                PlayerArg pArg = new PlayerArg();
                pArg.parse(data.substring(1));
                if (!pArg.isValid()) {
                    error = pArg.getError();
                    success = false;
                    return success;
                }
                location = ((Player)pArg.getValue()).getLocation();
            } else {
                //Get world.
                WorldArg wArg = new WorldArg();
                wArg.parse(data);
                if (!wArg.isValid()) {
                    error = wArg.getError();
                    success = false;
                    return success;
                }
                location.setWorld((World)wArg.getValue());
            }
        }

        //Get the coords x,y,z[,yaw,pitch]
        String[] coords = split[0].split(",");
        if (coords.length < 3) {
            error = Message.MISSING_XYZ_LOCATION.msg().getMsg(true, input);
            success = false;
            return success;
        }

        //Convert the location in a map so we can easily modify the values.
        Map<String, Object> locMap = location.serialize();
        String[] mapKeys = new String[] {"x", "y", "z", "yaw", "pitch"};

        //Go through all the values.
        for (int i = 0; i < coords.length; i++) {
            String value = coords[i];
            //Check if it's a relative value or not.
            boolean relative = false;
            if (value.startsWith("~")) {
                value = value.substring(1);
                relative = true;
            }
            //Parse the value.
            Double val = NumberUtil.getDouble(value);
            if (val == null && !value.isEmpty()) {
                error = Message.NOT_A_DECIMAL.msg().getMsg(true, value);
                success = false;
                return success;
            }
            //If leaving the value empty use 0 so ,,:world would be 0,0,0:world
            //This is more usefull for relative coords so you can do ~,~1,~:@Worstboy
            if (val == null) {
                val = 0d;
            }
            //Add relative coords to the value.
            if (relative) {
                val += (Double)locMap.get(mapKeys[i]);
            }

            //Update the value for the location.
            locMap.put(mapKeys[i], val);
        }

        //Convert the location map back to a location.
        location = Location.deserialize(locMap);

        //Final error check just to make sure.
        if (location == null) {
            error = Message.INVALID_LOCATION.msg().getMsg(true, input);
            success = false;
            return success;
        }

        value = location;
        return success;
    }

    @Override
     public LocationArg clone() {
        return new LocationArg(name, defaultValue == null ? null : ((Location)defaultValue).clone());
    }

    @Override
    public String getDescription() {
        return Message.ARG_LOCATION.msg().getMsg(false);
    }

    @Override
    public Class getRawClass() {
        return Location.class;
    }
}
