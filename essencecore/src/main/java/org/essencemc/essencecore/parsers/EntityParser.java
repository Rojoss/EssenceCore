/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://essencemc.org>
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

package org.essencemc.essencecore.parsers;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.aliases.Aliases;
import org.essencemc.essencecore.arguments.LocationArg;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.entity.EEntity;
import org.essencemc.essencecore.entity.EntityTag;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.NumberUtil;
import org.essencemc.essencecore.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The EntityParser can be used to parse strings to EEntity's.
 * To parse a string you just create an instance of this class and pass a string in.
 * Then you can use isValid() to check if the parsing was successful.
 * If not you can call getError() to get the error message which you should display to the user.
 * And then finally getEntities() to get the value.
 */
public class EntityParser {

    private String string = null;
    private List<List<EEntity>> entities = new ArrayList<List<EEntity>>();

    private String error = "";


    /**
     * Parses the given entity string in to an EEntity.
     * If ignoreErrors is set to true it will still set the errors but it will try to continue parsing the rest.
     * It would still fail in some cases for example if there is an invalid entity specified.
     * @param string with all entity data.
     * @param location Optional location which will be used as default if there is no location specified in the entity string.
     * @param ignoreErrors If true it will continue parsing even when there is an error.
     */
    public EntityParser(String string, Location location, boolean ignoreErrors) {
        this.string = string;

        List<Character> connections = new ArrayList<Character>();
        List<String> sections = new ArrayList<String>();
        char[] chars = string.toCharArray();
        char prev = ' ';
        String str = "";
        boolean quote = false;
        //Split the string in to sections for multiple entities.
        //It will split the sections for each > but it will ignore > symbols in quotes and escaped > symbols.
        //The same goes for dashes - to leash entities together instead of stacking them.
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '"') {
                quote = !quote;
            }
            if ((chars[i] == '>' || chars[i] == '=') && (!quote && prev != '\\')) {
                connections.add(chars[i]);
                sections.add(str);
                str = "";
                continue;
            }
            if (chars[i] != '\\') {
                str += chars[i];
            }
            prev = chars[i];
            if (i+1 == chars.length) {
                sections.add(str);
            }
        }

        //Get the amount and location from the last section if specified.
        int amount = 1;
        if (sections.size() > 0) {
            String lastSection = sections.get(sections.size() - 1);
            String data = "";
            if (lastSection.contains(")")) {
                String[] split = lastSection.split("\\) ", 2);
                if (split.length > 1) {
                    data = split[1];
                }
                sections.set(sections.size() - 1, split[0].trim() + ")");
            } else {
                String[] split = lastSection.split(" ", 2);
                if (split.length > 1) {
                    data = split[1];
                }
                sections.set(sections.size() - 1, split[0].trim());
            }
            data = data.trim();
            if (!data.isEmpty()) {
                String[] split = data.split(" ");
                if (NumberUtil.getInt(split[0]) == null) {
                    error = Message.PARSER_INVALID_AMOUNT.msg().getMsg(true, split[0]);
                    return;
                }
                amount = NumberUtil.getInt(split[0]);
                if (split.length > 1) {
                    LocationArg locArg = new LocationArg();
                    locArg.parse(split[1]);
                    if (!locArg.isValid()) {
                        error = locArg.getError();
                        return;
                    }
                    location = (Location)locArg.getValue();
                }
            }
        }

        if (location == null) {
            if (!ignoreErrors) {
                error = Message.ENTITY_NO_LOCATION.msg().getMsg(true);
                return;
            }

            //If there is no location specified as param and none in the entity string use default world spawn location.
            location = EssenceCore.inst().getServer().getWorlds().get(0).getSpawnLocation();
        }

        //Get a map with the EntityType as key and a map with key values for all extra data.
        Map<EntityType, Map<String, String>> entityMap = new HashMap<EntityType, Map<String, String>>();
        for (String section : sections) {
            //Get the entity and data from the entity section.
            String entity = section;
            String data = "";
            if (section.endsWith(")") && section.contains("(")) {
                String[] split = section.split("\\(", 2);
                entity = split[0];
                data = split[1].substring(0, split[1].length()-1);
            }

            //Get the entity
            //TODO: Get entity from alias.
            EntityType type = EntityType.fromName(entity);
            if (type == null) {
                error = Message.INVALID_ENITY_TYPE.msg().getMsg(true, entity);
                return;
            }

            //Get a map with keys and values for all data.
            //Each kv pair is split by a comma. Again comma's in quotes will be ignored and escaped comma's too.
            //It will then split the kv string by a semicoln and put that in the data map.
            //Input: speed:50,health:100,name:'&eExample:,> test' Output: {{'speed':'50'}, {'health':'100'}, {'name':'&eExample:,> test'}}
            Map<String, String> dataMap = new HashMap<String, String>();
            chars = data.toCharArray();
            prev = ' ';
            quote = false;
            str = "";
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '"') {
                    quote = !quote;
                }
                if (chars[i] == ',' && (!quote && prev != '\\')) {
                    String[] split = str.split(":", 2);
                    dataMap.put(split[0].toLowerCase(), (split.length > 1 ? split[1] : ""));
                    str = "";
                    continue;
                }
                if (chars[i] != '\\') {
                    str += chars[i];
                }
                prev = chars[i];
                if (i+1 == chars.length) {
                    String[] split = str.split(":", 2);
                    dataMap.put(split[0].toLowerCase(), (split.length > 1 ? split[1] : ""));
                }
            }

            entityMap.put(type, dataMap);
        }

        this.entities.clear();
        for (int i = 0; i < amount; i++) {
            List<EEntity> entities = new ArrayList<EEntity>();
            for (Map.Entry<EntityType, Map<String, String>> entry : entityMap.entrySet()) {
                EEntity entity = EEntity.create(entry.getKey(), location);

                for (Map.Entry<String, String> data : entry.getValue().entrySet()) {
                    EntityTag tag = EntityTag.fromString(data.getKey());
                    if (tag != null) {
                        if (!EntityTag.getTags(entry.getKey()).contains(tag)) {
                            //TODO: Get name from alias.
                            error = Message.INVALID_ENTITY_TAG_ENTITY.msg().getMsg(true, data.getKey(), entry.getKey().getName());
                            if (!ignoreErrors) {
                                entity.remove();
                                return;
                            }
                        }

                        Argument arg = tag.getArg().clone();
                        arg.parse(data.getValue());
                        if ((!arg.isValid() && !data.getValue().isEmpty()) || (!arg.hasDefault() && data.getValue().isEmpty())) {
                            error = arg.getError();
                            if (!ignoreErrors) {
                                entity.remove();
                                return;
                            }
                        }
                        Object val = arg.getValue();
                        if (val == null) {
                            val = arg.getDefault();
                        }

                        //Manual method calls.
                        if (tag == EntityTag.HEALTH) {
                            if (entity.getMaxHealth() < (Double)val) {
                                entity.setMaxHealth((Double)val);
                            }
                        } else if (tag == EntityTag.DOMESTICATION) {
                            if (entity.getMaxDomestication() < (Integer)val) {
                                entity.setMaxDomestication((Integer) val);
                            }
                        } else if (tag == EntityTag.DIR) {
                            BlockFace dir = BlockFace.valueOf((String)val);
                            entity.setFacingDirection(dir, true);
                            continue;
                        }

                        //Parse any extra data..
                        if (tag == EntityTag.ROTATION) {
                            val = Util.getRotation(NumberUtil.getInt((String)val));
                        } else if (tag == EntityTag.POSE || tag == EntityTag.HEAD || tag == EntityTag.LARM || tag == EntityTag.RARM || tag == EntityTag.LLEG || tag == EntityTag.RLEG) {
                            Vector v = (Vector)val;
                            val = new EulerAngle(v.getX(), v.getY(), v.getZ());
                        } else if (tag == EntityTag.ART) {
                            val = Aliases.getPainting((String)val);
                        } else if (tag == EntityTag.VARIANT) {
                            val = Aliases.getHorsVariant((String) val);
                        } else if (tag == EntityTag.STYLE) {
                            val = Aliases.getHorseStyle((String) val);
                        } else if (tag == EntityTag.COLOR) {
                            val = Aliases.getHorseColor((String) val);
                        } else if (tag == EntityTag.CATTYPE) {
                            val = Aliases.getOcelotType((String) val);
                        } else if (tag == EntityTag.RABITTYPE) {
                            val = Aliases.getRabitType((String) val);
                        } else if (tag == EntityTag.PROFESSION) {
                            val = Aliases.getVillagerType((String) val);
                        } else if (tag == EntityTag.COLLAR) {
                            val = Aliases.getDyeColor((String) val);
                        }

                        //Apply the data to the entity.
                        try {
                            Method method = entity.getClass().getMethod(tag.getMethod(), val.getClass());
                            method.invoke(entity, val);
                        } catch (NoSuchMethodException e) {
                            EssenceCore.inst().logError("No entity method for " + tag.getMethod() + " for the tag " + tag.toString() + "!");
                        } catch (IllegalAccessException e) {
                            EssenceCore.inst().logError("The entity method " + tag.getMethod() + " can't be accessed!");
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    //Do the same for potion effects by key.
                    //TODO: Potion effects as tags.

                    error = Message.INVALID_ENTITY_TAG.msg().getMsg(true, data.getKey());
                    if (!ignoreErrors) {
                        entity.remove();
                        return;
                    }
                }

                entities.add(entity);
                if (entities.size() > 1) {
                    if (connections.get(entities.size()-2) == '>') {
                        entities.get(entities.size()-2).setPassenger(entities.get(entities.size()-1));
                    } else {
                        entities.get(entities.size()-2).setLeashHolder(entities.get(entities.size()-1));
                    }
                }
            }
            this.entities.add(entities);
        }
    }

    /**
     * Checks if the parsing was successful or not.
     * Call getError() if it wasn't successful and display it to the user.
     * @return if it parsed successful.
     */
    public boolean isValid() {
        return entities != null && !entities.isEmpty() && string != null && error.isEmpty();
    }

    /**
     * If the validation was unsuccessful this will return the error message.
     * @return the message which contains the error. If it was successful the message will be empty.
     */
    public String getError() {
        return error;
    }

    /**
     * Get the parsed string value.
     * @return string which can be used in configurations and commands.
     */
    public String getString() {
        return string;
    }

    /**
     * Get the parsed EEntity list value.
     * When stacked the inner list will contain all the stacked entities where index 0 is the bottom entity.
     * @return List with EEntity instances that have all data parsed.
     */
    public List<List<EEntity>> getEntities() {
        return entities;
    }
}
