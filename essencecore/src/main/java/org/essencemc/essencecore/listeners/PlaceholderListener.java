package org.essencemc.essencecore.listeners;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.util.Vector;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.aliases.AliasType;
import org.essencemc.essencecore.aliases.Aliases;
import org.essencemc.essencecore.placeholders.Placeholder;
import org.essencemc.essencecore.placeholders.PlaceholderRequestEvent;
import org.essencemc.essencecore.placeholders.PlaceholderType;

import java.util.List;
import java.util.Random;

public class PlaceholderListener implements Listener {

    private EssenceCore ess;
    private Random random = new Random();

    public PlaceholderListener(EssenceCore ess) {
        this.ess = ess;
    }

    //For testing purposes.
    @EventHandler
    private void chat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().isOp()) {
            event.setMessage(Placeholder.parse(event.getMessage(), event.getPlayer()));
        }
    }

    //All default placeholders.
    @EventHandler(priority = EventPriority.LOWEST)
    private void placeholderRequest(PlaceholderRequestEvent event) {
        String[] split = event.getPlaceholder().split("_", 2);
        if (split.length <= 1) {
            return;
        }
        String p = split[1];

        Object[] data = event.getData();

        // ##################################################
        // #################### STRINGS #####################
        // ##################################################
        if (event.getType() == PlaceholderType.STRING) {
            if (data.length < 1 || !(data[0] instanceof String)) {
                return;
            }
            String data0 = ((String)data[0]);

            if (p.equals("empty")) {
                event.setValue(data0.isEmpty());
            } else if (p.equals("length") || p.equals("size")) {
                event.setValue(data0.length());
            } else if (p.equals("uppercase") || p.equals("upper")) {
                event.setValue(data0.toUpperCase());
            } else if (p.equals("lowercase") || p.equals("lower")) {
                event.setValue(data0.toLowerCase());
            }
            return;
        }

        // ##################################################
        // #################### INTEGERS ####################
        // ##################################################
        if (event.getType() == PlaceholderType.INTEGER) {
            if (p.equals("random") || p.equals("rand")) {
                event.setValue(random.nextInt());
            }

            if (data.length < 1 || !(data[0] instanceof Integer)) {
                return;
            }
            Integer data0 = ((Integer)data[0]);

            if (p.equals("double")) {
                event.setValue(data0.doubleValue());
            } else if (p.equals("float")) {
                event.setValue(data0.floatValue());
            } else if (p.equals("long")) {
                event.setValue(data0.longValue());
            } else if (p.equals("abs")) {
                event.setValue(Math.abs(data0));
            }
            return;
        }

        // ##################################################
        // ##################### FLOAT ######################
        // ##################################################
        if (event.getType() == PlaceholderType.FLOAT) {
            if (p.equals("random") || p.equals("rand")) {
                event.setValue(random.nextFloat());
            }

            if (data.length < 1 || !(data[0] instanceof Float)) {
                return;
            }
            Float data0 = ((Float)data[0]);

            if (p.equals("double")) {
                event.setValue(data0.doubleValue());
            } else if (p.equals("int")) {
                event.setValue(data0.intValue());
            } else if (p.equals("long")) {
                event.setValue(data0.longValue());
            } else if (p.equals("round")) {
                event.setValue(Math.round(data0));
            } else if (p.equals("abs")) {
                event.setValue(Math.abs(data0));
            }
            return;
        }

        // ##################################################
        // ##################### DOUBLE #####################
        // ##################################################
        if (event.getType() == PlaceholderType.DOUBLE) {
            if (p.equals("random") || p.equals("rand")) {
                event.setValue(random.nextDouble());
            }

            if (data.length < 1 || !(data[0] instanceof Double)) {
                return;
            }
            Double data0 = ((Double)data[0]);

            if (p.equals("float")) {
                event.setValue(data0.floatValue());
            } else if (p.equals("int")) {
                event.setValue(data0.intValue());
            } else if (p.equals("long")) {
                event.setValue(data0.longValue());
            } else if (p.equals("ceil")) {
                event.setValue(Math.ceil(data0));
            } else if (p.equals("floor")) {
                event.setValue(Math.floor(data0));
            } else if (p.equals("abs")) {
                event.setValue(Math.abs(data0));
            }
            return;
        }

        // ##################################################
        // ################### LOCATIONS ####################
        // ##################################################
        if (event.getType() == PlaceholderType.LOCATION) {
            if (data.length < 1 || !(data[0] instanceof Location)) {
                return;
            }
            Location data0 = ((Location)data[0]);

            if (p.equals("x")) {
                event.setValue(data0.getX());
            } else if (p.equals("y")) {
                event.setValue(data0.getY());
            } else if (p.equals("z")) {
                event.setValue(data0.getZ());
            } else if (p.equals("bx") || p.equals("blockx")) {
                event.setValue(data0.getBlockX());
            } else if (p.equals("by") || p.equals("blocky")) {
                event.setValue(data0.getBlockY());
            } else if (p.equals("bz") || p.equals("blockz")) {
                event.setValue(data0.getBlockZ());
            } else if (p.equals("yaw") || p.equals("y")) {
                event.setValue(data0.getYaw());
            } else if (p.equals("pitch") || p.equals("p")) {
                event.setValue(data0.getPitch());
            } else if (p.equals("world")) {
                event.setValue(data0.getWorld());
            } else if (p.equals("vector")) {
                event.setValue(data0.toVector());
            } else if (p.equals("direction") || p.equals("dir")) {
                event.setValue(data0.getDirection());
            }
            return;
        }

        // ##################################################
        // #################### VECTORS #####################
        // ##################################################
        if (event.getType() == PlaceholderType.VECTOR) {
            if (data.length < 1 || !(data[0] instanceof Vector)) {
                return;
            }
            Vector data0 = ((Vector)data[0]);

            if (p.equals("x")) {
                event.setValue(data0.getX());
            } else if (p.equals("y")) {
                event.setValue(data0.getY());
            } else if (p.equals("z")) {
                event.setValue(data0.getZ());
            } else if (p.equals("bx") || p.equals("blockx")) {
                event.setValue(data0.getBlockX());
            } else if (p.equals("by") || p.equals("blocky")) {
                event.setValue(data0.getBlockY());
            } else if (p.equals("bz") || p.equals("blockz")) {
                event.setValue(data0.getBlockZ());
            }
            return;
        }

        // ##################################################
        // ##################### WORLD ######################
        // ##################################################
        if (event.getType() == PlaceholderType.WORLD) {
            if (data.length < 1 || !(data[0] instanceof World)) {
                return;
            }
            World data0 = ((World)data[0]);

            if (p.equals("uuid") || p.equals("uid")) {
                event.setValue(data0.getUID().toString());
            } else if (p.equals("id") || p.equals("index")) {
                List<World> worlds = ess.getServer().getWorlds();
                for (int i = 0; i < ess.getServer().getWorlds().size(); i++) {
                    if (worlds.get(i).getName().equals(data0.getName())) {
                        event.setValue(i);
                        break;
                    }
                }
            } else if (p.equals("animals")) {
                event.setValue(data0.getAllowAnimals());
            } else if (p.equals("monsters") || p.equals("mobs")) {
                event.setValue(data0.getAllowMonsters());
            } else if (p.equals("pvp")) {
                event.setValue(data0.getPVP());
            } else if (p.equals("difficulty")) {
                event.setValue(data0.getDifficulty().name());
            } else if (p.equals("environment") || p.equals("env")) {
                event.setValue(data0.getEnvironment().name());
            } else if (p.equals("height") || p.equals("maxheight")) {
                event.setValue(data0.getMaxHeight());
            } else if (p.equals("seed")) {
                event.setValue(data0.getSeed());
            } else if (p.equals("spawn")) {
                event.setValue(data0.getSpawnLocation());
            } else if (p.equals("time")) {
                event.setValue(data0.getTime());
            } else if (p.equals("type")) {
                event.setValue(data0.getWorldType().getName());
            }
            return;
        }

        // ##################################################
        // #################### PLAYER ######################
        // ##################################################
        if (event.getType() == PlaceholderType.PLAYER) {
            if (data.length < 1 || !(data[0] instanceof World)) {
                return;
            }
            Player data0 = ((Player)data[0]);

                //General
            if (p.equals("name")) {
                event.setValue(data0.getName());
            } else if (p.equals("displayname") || p.equals("dname")) {
                event.setValue(data0.getDisplayName());
            } else if (p.equals("listname") || p.equals("tabname") || p.equals("scorename")) {
                event.setValue(data0.getPlayerListName());
            } else if (p.equals("uuid")) {
                event.setValue(data0.getUniqueId().toString());
            } else if (p.equals("id") || p.equals("entityid")) {
                event.setValue(data0.getEntityId());
            } else if (p.equals("ip") || p.equals("address")) {
                event.setValue(data0.getAddress().getHostString());
            } else if (p.equals("whitelisted") || p.equals("whitelist")) {
                event.setValue(data0.isWhitelisted());
            } else if (p.equals("banned") || p.equals("ban")) {
                event.setValue(data0.isBanned());
            } else if (p.equals("lastplayed") || p.equals("lastonline")) {
                event.setValue(data0.getLastPlayed());
            } else if (p.equals("firstplayed") || p.equals("firstonline")) {
                event.setValue(data0.getFirstPlayed());

                //Location
            } else if (p.equals("location") || p.equals("loc")) {
                event.setValue(data0.getLocation());
            } else if (p.equals("world") ) {
                event.setValue(data0.getWorld());
            } else if (p.equals("compass") || p.equals("compassloc")) {
                event.setValue(data0.getCompassTarget());
            } else if (p.equals("bed") || p.equals("bedspawn") || p.equals("bedloc") || p.equals("bedspawnloc")) {
                event.setValue(data0.getBedSpawnLocation());
            } else if (p.equals("bed") || p.equals("bedspawn") || p.equals("bedloc") || p.equals("bedspawnloc")) {
                event.setValue(data0.getBedSpawnLocation());
            } else if (p.equals("eye") || p.equals("eyes") || p.equals("eyeloc")) {
                event.setValue(data0.getEyeLocation());
            } else if (p.equals("velocity") || p.equals("vel") || p.equals("v")) {
                event.setValue(data0.getVelocity());
            } else if (p.equals("direction") || p.equals("dir")) {
                event.setValue(data0.getLocation().getDirection());

                //Status
            } else if (p.equals("health") || p.equals("hp")) {
                event.setValue(data0.getHealth());
            } else if (p.equals("maxhealth") || p.equals("maxhp") || p.equals("mhealth") || p.equals("mhp")) {
                event.setValue(data0.getMaxHealth());
            } else if (p.equals("hunger") || p.equals("food") || p.equals("foodlevel")) {
                event.setValue(data0.getFoodLevel());
            } else if (p.equals("exhaustion") || p.equals("exhaust") || p.equals("exh")) {
                event.setValue(data0.getExhaustion());
            } else if (p.equals("saturation") || p.equals("saturate") || p.equals("sat")) {
                event.setValue(data0.getSaturation());
            } else if (p.equals("air") || p.equals("oxygen")) {
                event.setValue(data0.getRemainingAir());
            } else if (p.equals("maxair") || p.equals("maxoxygen") || p.equals("mair") || p.equals("moxygen")) {
                event.setValue(data0.getMaximumAir());
            } else if (p.equals("flyspeed") || p.equals("flyingspeed") || p.equals("fspeed")) {
                event.setValue((Integer)Math.round(data0.getFlySpeed() * 100));
            } else if (p.equals("walkspeed") || p.equals("movespeed") || p.equals("walkingspeed") || p.equals("movingspeed") || p.equals("mspeed") || p.equals("wspeed")) {
                event.setValue((Integer)Math.round(data0.getWalkSpeed() * 100));
            } else if (p.equals("fire") || p.equals("firetick") || p.equals("fireticks")) {
                event.setValue(data0.getFireTicks());
            } else if (p.equals("maxfire") || p.equals("maxfiretick") || p.equals("maxfireticks") || p.equals("mfire") || p.equals("mfiretick") || p.equals("mfireticks")) {
                event.setValue(data0.getMaxFireTicks());
            } else if (p.equals("allowflight") || p.equals("flight") || p.equals("canfly")) {
                event.setValue(data0.getAllowFlight());
            } else if (p.equals("flying") || p.equals("fly")) {
                event.setValue(data0.isFlying());
            } else if (p.equals("sprinting") || p.equals("sprint")) {
                event.setValue(data0.isSprinting());
            } else if (p.equals("sneaking") || p.equals("sneak") || p.equals("shifting") || p.equals("shift")) {
                event.setValue(data0.isSneaking());
            } else if (p.equals("blocking") || p.equals("block")) {
                event.setValue(data0.isBlocking());
            } else if (p.equals("dead") || p.equals("death")) {
                event.setValue(data0.isDead());
            } else if (p.equals("alive")) {
                event.setValue(!data0.isDead());
            } else if (p.equals("killer")) {
                event.setValue(data0.getKiller() == null ? null : data0.getKiller());
            } else if (p.equals("lastdamage") || p.equals("lastdmg")) {
                event.setValue(data0.getLastDamage());
            } else if (p.equals("lastdamagecause") || p.equals("lastdmgcause")) {
                event.setValue(data0.getLastDamageCause().getCause().name());
            } else if (p.equals("nodamageticks") || p.equals("nodmgticks") || p.equals("nodamagetick") || p.equals("nodmgtick")) {
                event.setValue(data0.getNoDamageTicks());
            } else if (p.equals("maxnodamageticks") || p.equals("maxnodmgticks") || p.equals("maxnodamagetick") || p.equals("maxnodmgtick")) {
                event.setValue(data0.getMaximumNoDamageTicks());
            } else if (p.equals("gamemode")) {
                event.setValue(Aliases.getName(AliasType.GAME_MODE, data0.getGameMode().toString()));
            } else if (p.equals("time")) {
                event.setValue(data0.getPlayerTime());
            } else if (p.equals("timeoffset")) {
                event.setValue(data0.getPlayerTimeOffset());
            } else if (p.equals("weather")) {
                event.setValue(data0.getPlayerWeather().name());
            } else if (p.equals("pickupitems") || p.equals("itempickup")) {
                event.setValue(data0.getCanPickupItems());
            } else if (p.equals("tickslived") || p.equals("ticklived") || p.equals("ticksalive") || p.equals("tickalive") || p.equals("aliveticks") || p.equals("alivetick")) {
                event.setValue(data0.getTicksLived());
            } else if (p.equals("sleepticks") || p.equals("sleeptick")) {
                event.setValue(data0.getSleepTicks());

                //Misc
            } else if (p.equals("invehicle")) {
                event.setValue(data0.isInsideVehicle());
            } else if (p.equals("vehicle")) {
                event.setValue(data0.getVehicle());

                //Item/Inv
            } else if (p.equals("hand") || p.equals("holding")) {
                event.setValue(data0.getItemInHand());
            } else if (p.equals("helmet") || p.equals("helm") || p.equals("cap")) {
                event.setValue(data0.getInventory().getHelmet());
            } else if (p.equals("chestplate") || p.equals("chest") || p.equals("cplate") || p.equals("chestp")) {
                event.setValue(data0.getInventory().getChestplate());
            } else if (p.equals("leggings") || p.equals("legs") || p.equals("leg") || p.equals("pants")) {
                event.setValue(data0.getInventory().getLeggings());
            } else if (p.equals("boots") || p.equals("boot") || p.equals("shoes")) {
                event.setValue(data0.getInventory().getBoots());
            } else if (p.equals("cursor") || p.equals("selection")) {
                event.setValue(data0.getItemOnCursor());
            } else if (p.equals("slot")) {
                event.setValue(data0.getInventory().getHeldItemSlot());
            }

            //TODO: Experience with utility (vanilla experience is messed up)
            //TODO: Methods that require extra data like statistics, potion effects etc.
            //TODO: Methods that return Entity, Inventory etc.
            return;
        }

        // ##################################################
        // ##################### SERVER #####################
        // ##################################################
        if (event.getType() == PlaceholderType.SERVER) {
            Server server = ess.getServer();

            if (p.equals("name")) {
                event.setValue(server.getName());
            } else if (p.equals("bukkitversion") || p.equals("bukkitver") || p.equals("bversion") || p.equals("bver")) {
                event.setValue(server.getBukkitVersion());
            } else if (p.equals("version") || p.equals("ver")) {
                event.setValue(server.getVersion());
            } else if (p.equals("ip")) {
                event.setValue(server.getIp());
            } else if (p.equals("port")) {
                event.setValue(server.getPort());
            } else if (p.equals("motd")) {
                event.setValue(server.getMotd());
            } else if (p.equals("id")) {
                event.setValue(server.getServerId());
            } else if (p.equals("servername") || p.equals("sname")) {
                event.setValue(server.getServerName());
            } else if (p.equals("maxplayers") || p.equals("maxp") || p.equals("max")) {
                event.setValue(server.getMaxPlayers());
            } else if (p.equals("onlinemode") || p.equals("online")) {
                event.setValue(server.getOnlineMode());
            } else if (p.equals("spawnradius")) {
                event.setValue(server.getSpawnRadius());
            } else if (p.equals("viewdistance") || p.equals("renderdistance") || p.equals("view")) {
                event.setValue(server.getViewDistance());
            } else if (p.equals("whitelisted") || p.equals("whitelist")) {
                event.setValue(server.hasWhitelist());
            } else if (p.equals("hardcore")) {
                event.setValue(server.isHardcore());
            }
            return;
        }
    }
}
