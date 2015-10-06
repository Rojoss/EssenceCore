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
        String p = event.getPlaceholder();
        Object[] data = event.getData();

        // ##################################################
        // #################### STRINGS #####################
        // ##################################################
        if (event.getType() == PlaceholderType.STRING) {
            if (!(event.getSource() instanceof String)) {
                return;
            }
            String source = (String)event.getSource();

            if (p.equals("empty")) {
                event.setValue(source.isEmpty());
            } else if (p.equals("length") || p.equals("size")) {
                event.setValue(source.length());
            } else if (p.equals("uppercase") || p.equals("upper")) {
                event.setValue(source.toUpperCase());
            } else if (p.equals("lowercase") || p.equals("lower")) {
                event.setValue(source.toLowerCase());
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

            if (!(event.getSource() instanceof Integer)) {
                return;
            }
            Integer source = (Integer)event.getSource();

            if (p.equals("double")) {
                event.setValue(source.doubleValue());
            } else if (p.equals("float")) {
                event.setValue(source.floatValue());
            } else if (p.equals("long")) {
                event.setValue(source.longValue());
            } else if (p.equals("abs")) {
                event.setValue(Math.abs(source));
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

            if (!(event.getSource() instanceof Float)) {
                return;
            }
            Float source = (Float)event.getSource();

            if (p.equals("double")) {
                event.setValue(source.doubleValue());
            } else if (p.equals("int")) {
                event.setValue(source.intValue());
            } else if (p.equals("long")) {
                event.setValue(source.longValue());
            } else if (p.equals("round")) {
                event.setValue(Math.round(source));
            } else if (p.equals("abs")) {
                event.setValue(Math.abs(source));
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

            if (!(event.getSource() instanceof Double)) {
                return;
            }
            Double source = (Double)event.getSource();

            if (p.equals("float")) {
                event.setValue(source.floatValue());
            } else if (p.equals("int")) {
                event.setValue(source.intValue());
            } else if (p.equals("long")) {
                event.setValue(source.longValue());
            } else if (p.equals("ceil")) {
                event.setValue(Math.ceil(source));
            } else if (p.equals("floor")) {
                event.setValue(Math.floor(source));
            } else if (p.equals("abs")) {
                event.setValue(Math.abs(source));
            }
            return;
        }

        // ##################################################
        // ################### LOCATIONS ####################
        // ##################################################
        if (event.getType() == PlaceholderType.LOCATION) {
            if (!(event.getSource() instanceof Location)) {
                return;
            }
            Location source = (Location)event.getSource();

            if (p.equals("x")) {
                event.setValue(source.getX());
            } else if (p.equals("y")) {
                event.setValue(source.getY());
            } else if (p.equals("z")) {
                event.setValue(source.getZ());
            } else if (p.equals("bx") || p.equals("blockx")) {
                event.setValue(source.getBlockX());
            } else if (p.equals("by") || p.equals("blocky")) {
                event.setValue(source.getBlockY());
            } else if (p.equals("bz") || p.equals("blockz")) {
                event.setValue(source.getBlockZ());
            } else if (p.equals("yaw") || p.equals("y")) {
                event.setValue(source.getYaw());
            } else if (p.equals("pitch") || p.equals("p")) {
                event.setValue(source.getPitch());
            } else if (p.equals("world")) {
                event.setValue(source.getWorld());
            } else if (p.equals("vector")) {
                event.setValue(source.toVector());
            } else if (p.equals("direction") || p.equals("dir")) {
                event.setValue(source.getDirection());
            }
            return;
        }

        // ##################################################
        // #################### VECTORS #####################
        // ##################################################
        if (event.getType() == PlaceholderType.VECTOR) {
            if (!(event.getSource() instanceof Vector)) {
                return;
            }
            Vector source = (Vector)event.getSource();

            if (p.equals("x")) {
                event.setValue(source.getX());
            } else if (p.equals("y")) {
                event.setValue(source.getY());
            } else if (p.equals("z")) {
                event.setValue(source.getZ());
            } else if (p.equals("bx") || p.equals("blockx")) {
                event.setValue(source.getBlockX());
            } else if (p.equals("by") || p.equals("blocky")) {
                event.setValue(source.getBlockY());
            } else if (p.equals("bz") || p.equals("blockz")) {
                event.setValue(source.getBlockZ());
            }
            return;
        }

        // ##################################################
        // ##################### WORLD ######################
        // ##################################################
        if (event.getType() == PlaceholderType.WORLD) {
            if (!(event.getSource() instanceof World)) {
                return;
            }
            World source = (World)event.getSource();

            if (p.equals("uuid") || p.equals("uid")) {
                event.setValue(source.getUID().toString());
            } else if (p.equals("id") || p.equals("index")) {
                List<World> worlds = ess.getServer().getWorlds();
                for (int i = 0; i < ess.getServer().getWorlds().size(); i++) {
                    if (worlds.get(i).getName().equals(source.getName())) {
                        event.setValue(i);
                        break;
                    }
                }
            } else if (p.equals("animals")) {
                event.setValue(source.getAllowAnimals());
            } else if (p.equals("monsters") || p.equals("mobs")) {
                event.setValue(source.getAllowMonsters());
            } else if (p.equals("pvp")) {
                event.setValue(source.getPVP());
            } else if (p.equals("difficulty")) {
                event.setValue(source.getDifficulty().name());
            } else if (p.equals("environment") || p.equals("env")) {
                event.setValue(source.getEnvironment().name());
            } else if (p.equals("height") || p.equals("maxheight")) {
                event.setValue(source.getMaxHeight());
            } else if (p.equals("seed")) {
                event.setValue(source.getSeed());
            } else if (p.equals("spawn")) {
                event.setValue(source.getSpawnLocation());
            } else if (p.equals("time")) {
                event.setValue(source.getTime());
            } else if (p.equals("type")) {
                event.setValue(source.getWorldType().getName());
            }
            return;
        }

        // ##################################################
        // #################### PLAYER ######################
        // ##################################################
        if (event.getType() == PlaceholderType.PLAYER) {
            if (!(event.getSource() instanceof Player)) {
                return;
            }
            Player source = (Player)event.getSource();

                //General
            if (p.equals("name")) {
                event.setValue(source.getName());
            } else if (p.equals("displayname") || p.equals("dname")) {
                event.setValue(source.getDisplayName());
            } else if (p.equals("listname") || p.equals("tabname") || p.equals("scorename")) {
                event.setValue(source.getPlayerListName());
            } else if (p.equals("uuid")) {
                event.setValue(source.getUniqueId().toString());
            } else if (p.equals("id") || p.equals("entityid")) {
                event.setValue(source.getEntityId());
            } else if (p.equals("ip") || p.equals("address")) {
                event.setValue(source.getAddress().getHostString());
            } else if (p.equals("whitelisted") || p.equals("whitelist")) {
                event.setValue(source.isWhitelisted());
            } else if (p.equals("banned") || p.equals("ban")) {
                event.setValue(source.isBanned());
            } else if (p.equals("lastplayed") || p.equals("lastonline")) {
                event.setValue(source.getLastPlayed());
            } else if (p.equals("firstplayed") || p.equals("firstonline")) {
                event.setValue(source.getFirstPlayed());

                //Location
            } else if (p.equals("location") || p.equals("loc")) {
                event.setValue(source.getLocation());
            } else if (p.equals("world") ) {
                event.setValue(source.getWorld());
            } else if (p.equals("compass") || p.equals("compassloc")) {
                event.setValue(source.getCompassTarget());
            } else if (p.equals("bed") || p.equals("bedspawn") || p.equals("bedloc") || p.equals("bedspawnloc")) {
                event.setValue(source.getBedSpawnLocation());
            } else if (p.equals("bed") || p.equals("bedspawn") || p.equals("bedloc") || p.equals("bedspawnloc")) {
                event.setValue(source.getBedSpawnLocation());
            } else if (p.equals("eye") || p.equals("eyes") || p.equals("eyeloc")) {
                event.setValue(source.getEyeLocation());
            } else if (p.equals("velocity") || p.equals("vel") || p.equals("v")) {
                event.setValue(source.getVelocity());
            } else if (p.equals("direction") || p.equals("dir")) {
                event.setValue(source.getLocation().getDirection());

                //Status
            } else if (p.equals("health") || p.equals("hp")) {
                event.setValue(source.getHealth());
            } else if (p.equals("maxhealth") || p.equals("maxhp") || p.equals("mhealth") || p.equals("mhp")) {
                event.setValue(source.getMaxHealth());
            } else if (p.equals("hunger") || p.equals("food") || p.equals("foodlevel")) {
                event.setValue(source.getFoodLevel());
            } else if (p.equals("exhaustion") || p.equals("exhaust") || p.equals("exh")) {
                event.setValue(source.getExhaustion());
            } else if (p.equals("saturation") || p.equals("saturate") || p.equals("sat")) {
                event.setValue(source.getSaturation());
            } else if (p.equals("air") || p.equals("oxygen")) {
                event.setValue(source.getRemainingAir());
            } else if (p.equals("maxair") || p.equals("maxoxygen") || p.equals("mair") || p.equals("moxygen")) {
                event.setValue(source.getMaximumAir());
            } else if (p.equals("flyspeed") || p.equals("flyingspeed") || p.equals("fspeed")) {
                event.setValue((Integer)Math.round(source.getFlySpeed() * 100));
            } else if (p.equals("walkspeed") || p.equals("movespeed") || p.equals("walkingspeed") || p.equals("movingspeed") || p.equals("mspeed") || p.equals("wspeed")) {
                event.setValue((Integer)Math.round(source.getWalkSpeed() * 100));
            } else if (p.equals("fire") || p.equals("firetick") || p.equals("fireticks")) {
                event.setValue(source.getFireTicks());
            } else if (p.equals("maxfire") || p.equals("maxfiretick") || p.equals("maxfireticks") || p.equals("mfire") || p.equals("mfiretick") || p.equals("mfireticks")) {
                event.setValue(source.getMaxFireTicks());
            } else if (p.equals("allowflight") || p.equals("flight") || p.equals("canfly")) {
                event.setValue(source.getAllowFlight());
            } else if (p.equals("flying") || p.equals("fly")) {
                event.setValue(source.isFlying());
            } else if (p.equals("sprinting") || p.equals("sprint")) {
                event.setValue(source.isSprinting());
            } else if (p.equals("sneaking") || p.equals("sneak") || p.equals("shifting") || p.equals("shift")) {
                event.setValue(source.isSneaking());
            } else if (p.equals("blocking") || p.equals("block")) {
                event.setValue(source.isBlocking());
            } else if (p.equals("dead") || p.equals("death")) {
                event.setValue(source.isDead());
            } else if (p.equals("alive")) {
                event.setValue(!source.isDead());
            } else if (p.equals("killer")) {
                event.setValue(source.getKiller() == null ? null : source.getKiller());
            } else if (p.equals("lastdamage") || p.equals("lastdmg")) {
                event.setValue(source.getLastDamage());
            } else if (p.equals("lastdamagecause") || p.equals("lastdmgcause")) {
                event.setValue(source.getLastDamageCause().getCause().name());
            } else if (p.equals("nodamageticks") || p.equals("nodmgticks") || p.equals("nodamagetick") || p.equals("nodmgtick")) {
                event.setValue(source.getNoDamageTicks());
            } else if (p.equals("maxnodamageticks") || p.equals("maxnodmgticks") || p.equals("maxnodamagetick") || p.equals("maxnodmgtick")) {
                event.setValue(source.getMaximumNoDamageTicks());
            } else if (p.equals("gamemode")) {
                event.setValue(Aliases.getName(AliasType.GAME_MODE, source.getGameMode().toString()));
            } else if (p.equals("time")) {
                event.setValue(source.getPlayerTime());
            } else if (p.equals("timeoffset")) {
                event.setValue(source.getPlayerTimeOffset());
            } else if (p.equals("weather")) {
                event.setValue(source.getPlayerWeather().name());
            } else if (p.equals("pickupitems") || p.equals("itempickup")) {
                event.setValue(source.getCanPickupItems());
            } else if (p.equals("tickslived") || p.equals("ticklived") || p.equals("ticksalive") || p.equals("tickalive") || p.equals("aliveticks") || p.equals("alivetick")) {
                event.setValue(source.getTicksLived());
            } else if (p.equals("sleepticks") || p.equals("sleeptick")) {
                event.setValue(source.getSleepTicks());

                //Misc
            } else if (p.equals("invehicle")) {
                event.setValue(source.isInsideVehicle());
            } else if (p.equals("vehicle")) {
                event.setValue(source.getVehicle());

                //Item/Inv
            } else if (p.equals("hand") || p.equals("holding")) {
                event.setValue(source.getItemInHand());
            } else if (p.equals("helmet") || p.equals("helm") || p.equals("cap")) {
                event.setValue(source.getInventory().getHelmet());
            } else if (p.equals("chestplate") || p.equals("chest") || p.equals("cplate") || p.equals("chestp")) {
                event.setValue(source.getInventory().getChestplate());
            } else if (p.equals("leggings") || p.equals("legs") || p.equals("leg") || p.equals("pants")) {
                event.setValue(source.getInventory().getLeggings());
            } else if (p.equals("boots") || p.equals("boot") || p.equals("shoes")) {
                event.setValue(source.getInventory().getBoots());
            } else if (p.equals("cursor") || p.equals("selection")) {
                event.setValue(source.getItemOnCursor());
            } else if (p.equals("slot")) {
                event.setValue(source.getInventory().getHeldItemSlot());
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
