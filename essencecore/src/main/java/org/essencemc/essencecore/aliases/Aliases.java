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

package org.essencemc.essencecore.aliases;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Aliases {

    private static final Map<AliasType, List<Alias>> aliases = new HashMap<AliasType, List<Alias>>();
    private static AliasType currentType = AliasType.ENCHANTMENT;

    public static List<Alias> getAliases(AliasType aliasType) {
        if (aliases.containsKey(aliasType)) {
            return aliases.get(aliasType);
        }
        return new ArrayList<Alias>();
    }

    public static Map<String, List<String>> getAliasesMap(AliasType aliasType) {
        if (aliases.containsKey(aliasType)) {
            Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
            for (Alias alias : aliases.get(aliasType)) {
                resultMap.put(alias.getName(), alias.getAliases());
            }
            return resultMap;
        }
        return Collections.emptyMap();
    }

    public static List<String> getNames(AliasType aliasType, boolean noSpaces) {
        if (aliases.containsKey(aliasType)) {
            List<String> keys = new ArrayList<String>();
            for (Alias alias : aliases.get(aliasType)) {
                keys.add(noSpaces ? alias.getName().replace(" ", "") : alias.getName());
            }
            return keys;
        }
        return new ArrayList<String>();
    }

    public static Enchantment getEnchantment(String string) {
        try {
            return Enchantment.getByName(getKey(AliasType.ENCHANTMENT, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static PotionEffectType getPotionEffect(String string) {
        try {
            return PotionEffectType.getByName(getKey(AliasType.POTION_EFFECT, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static EntityType getEntity(String string) {
        try {
            return EntityType.valueOf(getKey(AliasType.ENTITY, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static GameMode getGameMode(String string) {
        try {
            return GameMode.valueOf(getKey(AliasType.GAME_MODE, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Art getPainting(String string) {
        try {
            return Art.valueOf(getKey(AliasType.PAINTING, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Biome getBiome(String string) {
        try {
            return Biome.valueOf(getKey(AliasType.BIOME, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static DyeColor getDyeColor(String string) {
        try {
            return DyeColor.valueOf(getKey(AliasType.DYE_COLOR, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static FireworkEffect.Type getFireworkEffect(String string) {
        try {
            return FireworkEffect.Type.valueOf(getKey(AliasType.FIREWORK_EFFECT, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Horse.Variant getHorsVariant(String string) {
        try {
            return Horse.Variant.valueOf(getKey(AliasType.HORSE_VARIANT, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Horse.Color getHorseColor(String string) {
        try {
            return Horse.Color.valueOf(getKey(AliasType.HORSE_COLOR, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Horse.Style getHorseStyle(String string) {
        try {
            return Horse.Style.valueOf(getKey(AliasType.HORSE_STYLE, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Ocelot.Type getOcelotType(String string) {
        try {
            return Ocelot.Type.valueOf(getKey(AliasType.OCELOT_TYPES, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Rabbit.Type getRabitType(String string) {
        try {
            return Rabbit.Type.valueOf(getKey(AliasType.RABBIT_TYPES, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Villager.Profession getVillagerType(String string) {
        try {
            return Villager.Profession.valueOf(getKey(AliasType.VILLAGER_TYPES, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static PatternType getBannerPattern(String string) {
        try {
            return PatternType.valueOf(getKey(AliasType.BANNER_PATTERNS, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static TreeType getTree(String string) {
        try {
            return TreeType.valueOf(getKey(AliasType.TREES, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static Sound getSound(String string) {
        try {
            return Sound.valueOf(getKey(AliasType.SOUNDS, string));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getName(AliasType type, String string) {
        List<Alias> aliases = getAliases(type);
        string = string.toLowerCase().replaceAll(" ", "").replaceAll("_", "");
        for (Alias alias : aliases) {
            if (alias.getName().toLowerCase().replaceAll(" ", "").equals(string)) {
                return alias.getName();
            }
            if (alias.getKey().toLowerCase().replaceAll("_", "").equals(string)) {
                return alias.getName();
            }
            if (alias.getAliases().contains(string)) {
                return alias.getName();
            }
        }
        return null;
    }

    public static List<String> getAliases(AliasType type, String string) {
        List<Alias> aliases = getAliases(type);
        string = string.toLowerCase().replaceAll(" ", "").replaceAll("_", "");
        for (Alias alias : aliases) {
            if (alias.getName().toLowerCase().replaceAll(" ", "").equals(string)) {
                return alias.getAliases();
            }
            if (alias.getKey().toLowerCase().replaceAll("_", "").equals(string)) {
                return alias.getAliases();
            }
            if (alias.getAliases().contains(string)) {
                return alias.getAliases();
            }
        }
        return null;
    }

    private static String getKey(AliasType type, String string) {
        List<Alias> aliases = getAliases(type);
        string = string.toLowerCase().replaceAll(" ", "").replaceAll("_", "");
        for (Alias alias : aliases) {
            if (alias.getName().toLowerCase().replaceAll(" ", "").equals(string)) {
                return alias.getKey();
            }
            if (alias.getKey().toLowerCase().replaceAll("_", "").equals(string)) {
                return alias.getKey();
            }
            if (alias.getAliases().contains(string)) {
                return alias.getKey();
            }
        }
        return null;
    }




    static {
        List<Alias> list = new ArrayList<Alias>();

        //Enchantments
        currentType = AliasType.ENCHANTMENT;
        list.add(getAlias(Enchantment.ARROW_INFINITE.getName(), "Infinity", new String[]{"51", "arrowinfinite", "inf", "infinite"}));
        list.add(getAlias(Enchantment.ARROW_DAMAGE.getName(), "Power", new String[]{"48", "pow", "arrowdamage", "arrowdmg", "dmgarrow", "damagearrow"}));
        list.add(getAlias(Enchantment.ARROW_FIRE.getName(), "Flame", new String[]{"50", "flaming", "firearrow", "arrowfire"}));
        list.add(getAlias(Enchantment.ARROW_KNOCKBACK.getName(), "Punch", new String[]{"49", "arrowknockback", "bowknockback"}));
        list.add(getAlias(Enchantment.DAMAGE_ALL.getName(), "Sharpness", new String[]{"16", "sharp", "damageall", "dmgall"}));
        list.add(getAlias(Enchantment.DAMAGE_ARTHROPODS.getName(), "Bane of Arthropods", new String[]{"18", "boa", "bane", "arthropods", "arthropod"}));
        list.add(getAlias(Enchantment.DAMAGE_UNDEAD.getName(), "Smite", new String[]{"17", "damageundead", "undead", "dmgundead"}));
        list.add(getAlias(Enchantment.DEPTH_STRIDER.getName(), "Depth Strider", new String[]{"8", "waterspeed", "watermove", "watermovement", "depths", "dstrider", "ds"}));
        list.add(getAlias(Enchantment.DIG_SPEED.getName(), "Efficiency", new String[]{"32", "eff", "efficient", "fasterdigging", "fasterdig", "digfast", "digfaster", "minespeed", "digspeed"}));
        list.add(getAlias(Enchantment.DURABILITY.getName(), "Unbreaking", new String[]{"34", "durability", "unbreakable", "dura", "dur"}));
        list.add(getAlias(Enchantment.FIRE_ASPECT.getName(), "Fire Aspect", new String[]{"20", "fire", "firesword", "flamesword", "fa"}));
        list.add(getAlias(Enchantment.KNOCKBACK.getName(), "Knockback", new String[]{"19", "kback", "knockb"}));
        list.add(getAlias(Enchantment.LOOT_BONUS_BLOCKS.getName(), "Fortune", new String[]{"35", "lootblock", "blockloot", "fort"}));
        list.add(getAlias(Enchantment.LOOT_BONUS_MOBS.getName(), "Looting", new String[]{"21", "lootmob", "mobloot", "loot"}));
        list.add(getAlias(Enchantment.LUCK.getName(), "Luck of the Sea", new String[]{"61", "luck", "treasures", "treasure", "lots"}));
        list.add(getAlias(Enchantment.LURE.getName(), "Lure", new String[]{"62", "luring", "bite", "bait", "fish"}));
        list.add(getAlias(Enchantment.OXYGEN.getName(), "Respiration", new String[]{"5", "oxygen", "oxy", "resp"}));
        list.add(getAlias(Enchantment.PROTECTION_ENVIRONMENTAL.getName(), "Protection", new String[]{"0", "prot", "p"}));
        list.add(getAlias(Enchantment.PROTECTION_EXPLOSIONS.getName(), "Blast Protection", new String[]{"3", "blastprot", "bprot", "bp", "explodeprot", "explosionprot", "eprot", "ep"}));
        list.add(getAlias(Enchantment.PROTECTION_FALL.getName(), "Feather Falling", new String[]{"2", "fallreduction", "feather", "falling", "ff", "fefa"}));
        list.add(getAlias(Enchantment.PROTECTION_FIRE.getName(), "Fire Protection", new String[]{"1", "fireprot", "fprot", "fp"}));
        list.add(getAlias(Enchantment.PROTECTION_PROJECTILE.getName(), "Projectile Protection", new String[]{"4", "projectileprot", "projprot", "projprotection", "pprot", "pp"}));
        list.add(getAlias(Enchantment.SILK_TOUCH.getName(), "Silk Touch", new String[]{"33", "silk", "st", "stouch", "silkt"}));
        list.add(getAlias(Enchantment.THORNS.getName(), "Thorns", new String[]{"7", "thorn", "spikes", "spike", "reflection", "reflect"}));
        list.add(getAlias(Enchantment.WATER_WORKER.getName(), "Aqua Affinity", new String[]{"6", "aquaaff", "aquaa", "aaffinity", "waterworker", "ww", "waterw", "wworker"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Potion effects
        list.clear();
        currentType = AliasType.POTION_EFFECT;
        list.add(getAlias(PotionEffectType.ABSORPTION.getName(), "Absorption", new String[] {"22", "absorb", "goldenhearts", "goldhearts", "goldhealth", "gold"}));
        list.add(getAlias(PotionEffectType.BLINDNESS.getName(), "Blindness", new String[] {"15", "blind", "black", "smoke", "cloud", "blinding"}));
        list.add(getAlias(PotionEffectType.CONFUSION.getName(), "Nausea", new String[] {"9", "confusion", "confuse", "sick", "sickness", "ill", "illness", "drunk"}));
        list.add(getAlias(PotionEffectType.DAMAGE_RESISTANCE.getName(), "Resistance", new String[] {"11", "res", "resist", "damagereduction", "damagereduc", "reducedamage", "reducedmg", "dmgreduction"}));
        list.add(getAlias(PotionEffectType.FAST_DIGGING.getName(), "Haste", new String[] {"3", "fastdig", "fastdigging", "digspeed", "minespeed", "diggingspeed", "miningspeed"}));
        list.add(getAlias(PotionEffectType.FIRE_RESISTANCE.getName(), "Fire resistance", new String[] {"12", "fireres", "fres", "fresistance", "firer"}));
        list.add(getAlias(PotionEffectType.HARM.getName(), "Harming", new String[] {"7", "harm", "damage", "dmg", "instantdamage", "instantdmg"}));
        list.add(getAlias(PotionEffectType.HEAL.getName(), "Instant health", new String[] {"6", "health", "heal", "ihealth", "life", "hp"}));
        list.add(getAlias(PotionEffectType.HEALTH_BOOST.getName(), "Health boost", new String[] {"21", "hboost", "healthb", "healboost", "healb", "extrahealth", "bonushealth", "hpboost", "hpbonus", "extrahp"}));
        list.add(getAlias(PotionEffectType.HUNGER.getName(), "Hunger", new String[] {"17", "starvation", "food", "hungry", "decreasehunger", "decreasefood", "dhunger", "dfood"}));
        list.add(getAlias(PotionEffectType.INCREASE_DAMAGE.getName(), "Strength", new String[] {"5", "power", "damageboost", "str", "extradmg", "dmgboost", "extradamage", "bonusdmg", "bonusdamage"}));
        list.add(getAlias(PotionEffectType.INVISIBILITY.getName(), "Invisibility", new String[] {"14", "invis", "inv", "hidden", "hide"}));
        list.add(getAlias(PotionEffectType.JUMP.getName(), "Jump boost", new String[] {"8", "bunny", "jump", "hop"}));
        list.add(getAlias(PotionEffectType.NIGHT_VISION.getName(), "Night vision", new String[] {"16", "vision", "nv", "nightv", "nvision", "sight", "brightness", "bright"}));
        list.add(getAlias(PotionEffectType.POISON.getName(), "Poison", new String[] {"19", "virus", "toxic", "toxin", "venom", "infection"}));
        list.add(getAlias(PotionEffectType.REGENERATION.getName(), "Regeneration", new String[] {"10", "regen", "re"}));
        list.add(getAlias(PotionEffectType.SATURATION.getName(), "Saturation", new String[] {"23", "sat", "saturate", "ihunger", "ifood", "increasehunger", "increasefood"}));
        list.add(getAlias(PotionEffectType.SLOW.getName(), "Slowness", new String[] {"2", "slow", "slowmove", "slowmovement", "smove", "smovement"}));
        list.add(getAlias(PotionEffectType.SLOW_DIGGING.getName(), "Mining fatigue", new String[] {"4", "mfatigue", "miningf", "miningfat", "mfat", "slowdig", "slowdigging", "digslow", "mineslow"}));
        list.add(getAlias(PotionEffectType.SPEED.getName(), "Speed", new String[] {"1", "rush", "fast", "fastmovement", "fastmove", "fmove", "fmovement", "smove", "smovement"}));
        list.add(getAlias(PotionEffectType.WATER_BREATHING.getName(), "Water breathing", new String[] {"13", "waterbreath", "waterb", "wbreathing", "wbreath", "oxygen", "oxy", "air"}));
        list.add(getAlias(PotionEffectType.WEAKNESS.getName(), "Weakness", new String[] {"18", "weak", "damageincrease", "increaseddamage", "dmgincrease", "increaseddmg", "moredmg"}));
        list.add(getAlias(PotionEffectType.WITHER.getName(), "Wither", new String[] {"20", "withering", "blackpoison", "blackvenom", "blackhearths"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Entities
        list.clear();
        currentType = AliasType.ENTITY;
        list.add(getAlias(EntityType.DROPPED_ITEM, "Dropped Item", new String[] {}));
        list.add(getAlias(EntityType.EXPERIENCE_ORB, "Experience Orb", new String[] {}));
        list.add(getAlias(EntityType.LEASH_HITCH, "Leash Knot", new String[] {}));
        list.add(getAlias(EntityType.PAINTING, "Painting", new String[] {}));
        list.add(getAlias(EntityType.ARROW, "Arrow", new String[] {}));
        list.add(getAlias(EntityType.SNOWBALL, "Snowball", new String[] {}));
        list.add(getAlias(EntityType.FIREBALL, "Fireball", new String[] {}));
        list.add(getAlias(EntityType.SMALL_FIREBALL, "Small Fireball", new String[] {}));
        list.add(getAlias(EntityType.ENDER_PEARL, "Ender Pearl", new String[] {}));
        list.add(getAlias(EntityType.ENDER_SIGNAL, "Ender Signal", new String[] {}));
        list.add(getAlias(EntityType.THROWN_EXP_BOTTLE, "Thrown Exp Bottle", new String[] {}));
        list.add(getAlias(EntityType.ITEM_FRAME, "Item Frame", new String[] {}));
        list.add(getAlias(EntityType.WITHER_SKULL, "Wither Skull", new String[] {}));
        list.add(getAlias(EntityType.PRIMED_TNT, "Primed TNT", new String[] {}));
        list.add(getAlias(EntityType.FALLING_BLOCK, "Falling Block", new String[] {}));
        list.add(getAlias(EntityType.FIREWORK, "Firework", new String[] {}));
        list.add(getAlias(EntityType.ARMOR_STAND, "Armor Stand", new String[] {}));
        list.add(getAlias(EntityType.MINECART_COMMAND, "Minecart Command Block", new String[] {}));
        list.add(getAlias(EntityType.BOAT, "Boat", new String[] {}));
        list.add(getAlias(EntityType.MINECART, "Minecart", new String[] {}));
        list.add(getAlias(EntityType.MINECART_CHEST, "Minecart Chest", new String[] {}));
        list.add(getAlias(EntityType.MINECART_FURNACE, "Minecart Furnace", new String[] {}));
        list.add(getAlias(EntityType.MINECART_TNT, "Minecart TNT", new String[] {}));
        list.add(getAlias(EntityType.MINECART_HOPPER, "Minecart Hopper", new String[] {}));
        list.add(getAlias(EntityType.MINECART_MOB_SPAWNER, "Minecart Mob Spawner", new String[] {}));
        list.add(getAlias(EntityType.CREEPER, "Creeper", new String[] {}));
        list.add(getAlias(EntityType.SKELETON, "Skeleton", new String[] {}));
        list.add(getAlias(EntityType.SPIDER, "Spider", new String[] {}));
        list.add(getAlias(EntityType.GIANT, "Giant", new String[] {}));
        list.add(getAlias(EntityType.ZOMBIE, "Zombie", new String[] {}));
        list.add(getAlias(EntityType.SLIME, "Slime", new String[] {}));
        list.add(getAlias(EntityType.GHAST, "Ghast", new String[] {}));
        list.add(getAlias(EntityType.PIG_ZOMBIE, "Pig Zombie", new String[] {}));
        list.add(getAlias(EntityType.ENDERMAN, "Enderman", new String[] {}));
        list.add(getAlias(EntityType.CAVE_SPIDER, "Cave Spider", new String[] {}));
        list.add(getAlias(EntityType.SILVERFISH, "Silverfish", new String[] {}));
        list.add(getAlias(EntityType.BLAZE, "Blaze", new String[] {}));
        list.add(getAlias(EntityType.MAGMA_CUBE, "Magma Cube", new String[] {}));
        list.add(getAlias(EntityType.ENDER_DRAGON, "Ender Dragon", new String[] {}));
        list.add(getAlias(EntityType.WITHER, "Wither", new String[] {}));
        list.add(getAlias(EntityType.BAT, "Bat", new String[] {}));
        list.add(getAlias(EntityType.WITCH, "Witch", new String[] {}));
        list.add(getAlias(EntityType.ENDERMITE, "Endermite", new String[] {}));
        list.add(getAlias(EntityType.GUARDIAN, "Guardian", new String[] {}));
        list.add(getAlias(EntityType.PIG, "Pig", new String[] {}));
        list.add(getAlias(EntityType.SHEEP, "Sheep", new String[] {}));
        list.add(getAlias(EntityType.COW, "Cow", new String[] {}));
        list.add(getAlias(EntityType.CHICKEN, "Chicken", new String[] {}));
        list.add(getAlias(EntityType.SQUID, "Squid", new String[] {}));
        list.add(getAlias(EntityType.WOLF, "Wolf", new String[] {}));
        list.add(getAlias(EntityType.MUSHROOM_COW, "Mushroom Cow", new String[] {}));
        list.add(getAlias(EntityType.SNOWMAN, "Snowman", new String[] {}));
        list.add(getAlias(EntityType.OCELOT, "Ocelot", new String[] {}));
        list.add(getAlias(EntityType.IRON_GOLEM, "Iron Golem", new String[] {}));
        list.add(getAlias(EntityType.HORSE, "Horse", new String[] {}));
        list.add(getAlias(EntityType.RABBIT, "Rabbit", new String[] {}));
        list.add(getAlias(EntityType.VILLAGER, "Villager", new String[] {}));
        list.add(getAlias(EntityType.ENDER_CRYSTAL, "Ender Crystal", new String[] {}));
        list.add(getAlias(EntityType.SPLASH_POTION, "Splash Potion", new String[] {}));
        list.add(getAlias(EntityType.EGG, "Egg", new String[] {}));
        list.add(getAlias(EntityType.FISHING_HOOK, "Fishing Hook", new String[] {}));
        list.add(getAlias(EntityType.LIGHTNING, "Lightning", new String[] {}));
        list.add(getAlias(EntityType.WEATHER, "Weather", new String[] {}));
        list.add(getAlias(EntityType.PLAYER, "Player", new String[] {}));
        list.add(getAlias(EntityType.COMPLEX_PART, "Complex Part", new String[] {}));

        /**
         * TODO: Some entities still need aliases.
         * I simply wrote all the entities in the EntityType class out in here with the name and aliases (which are at the moment just empty String[])
         */

        aliases.put(currentType, new ArrayList<Alias>(list));

        //Game modes
        list.clear();
        currentType = AliasType.GAME_MODE;
        list.add(getAlias(GameMode.SURVIVAL, "Survival", new String[] {"0", "sur", "s", "main", "survive"}));
        list.add(getAlias(GameMode.CREATIVE, "Creative", new String[]{"1", "cre", "c", "crea"}));
        list.add(getAlias(GameMode.ADVENTURE, "Adventure", new String[]{"2", "adv", "a"}));
        list.add(getAlias(GameMode.SPECTATOR, "Spectator", new String[]{"3", "spe", "sp", "spec"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Paintings
        list.clear();
        currentType = AliasType.PAINTING;
        list.add(getAlias(Art.ALBAN, "Alban", new String[] {"albanian", "albania"}));
        list.add(getAlias(Art.AZTEC, "Aztec", new String[] {"deaztec"}));
        list.add(getAlias(Art.AZTEC2, "Aztec2", new String[] {"deaztec2"}));
        list.add(getAlias(Art.BOMB, "Bomb", new String[] {"bombed", "dedust"}));
        list.add(getAlias(Art.BURNINGSKULL, "BurningSkull", new String[] {"fire", "skull", "burning"}));
        list.add(getAlias(Art.BUST, "Bust", new String[] {"statue"}));
        list.add(getAlias(Art.COURBET, "Courbet", new String[] {"hikers", "greeting"}));
        list.add(getAlias(Art.CREEBET, "Creebet", new String[] {"creeper"}));
        list.add(getAlias(Art.DONKEYKONG, "DonkeyKong", new String[] {"donkey", "game", "arcade"}));
        list.add(getAlias(Art.FIGHTERS, "Fighters", new String[] {"fight", "karate"}));
        list.add(getAlias(Art.GRAHAM, "Graham", new String[] {"king", "kingsquest"}));
        list.add(getAlias(Art.KEBAB, "Kebab", new String[] {"peppers", "food"}));
        list.add(getAlias(Art.MATCH, "Match", new String[] {"fireplace"}));
        list.add(getAlias(Art.PIGSCENE, "Pigscene", new String[] {"girl", "canvas", "rgb"}));
        list.add(getAlias(Art.PLANT, "Plant", new String[] {"pots", "pot"}));
        list.add(getAlias(Art.POINTER, "Pointer", new String[] {"hand", "pointing", "karateka"}));
        list.add(getAlias(Art.POOL, "Pool", new String[] {"swim", "swimming"}));
        list.add(getAlias(Art.SEA, "Sea", new String[] {"seaside", "lake", "flower"}));
        list.add(getAlias(Art.SKELETON, "Skeleton", new String[] {"skeli", "midget", "grim"}));
        list.add(getAlias(Art.SKULL_AND_ROSES, "SkullAndRoses", new String[] {"skull", "ocean"}));
        list.add(getAlias(Art.STAGE, "Stage", new String[] {"set", "spider"}));
        list.add(getAlias(Art.SUNSET, "Sunset", new String[] {"sun", "sunrise", "mountains", "mountain"}));
        list.add(getAlias(Art.VOID, "Void", new String[] {"angel", "praying", "pray"}));
        list.add(getAlias(Art.WANDERER, "Wanderer", new String[] {"wander", "man", "fog"}));
        list.add(getAlias(Art.WASTELAND, "Wasteland", new String[] {"rabbit", "desert"}));
        list.add(getAlias(Art.WITHER, "Wither", new String[] {"witherspawn"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Biomes
        list.clear();
        currentType = AliasType.BIOME;
        list.add(getAlias(Biome.BEACH, "Beach", new String[] {"16"}));
        list.add(getAlias(Biome.BIRCH_FOREST, "Birch Forest", new String[] {"27"}));
        list.add(getAlias(Biome.BIRCH_FOREST_HILLS, "Birch Forest Hills", new String[] {"28"}));
        list.add(getAlias(Biome.BIRCH_FOREST_HILLS_MOUNTAINS, "Birch Forest Hills M", new String[] {"156"}));
        list.add(getAlias(Biome.BIRCH_FOREST_MOUNTAINS, "Birch Forest M", new String[] {"155"}));
        list.add(getAlias(Biome.COLD_BEACH, "Cold Beach", new String[] {"26"}));
        list.add(getAlias(Biome.COLD_TAIGA, "Cold Taiga", new String[] {"30"}));
        list.add(getAlias(Biome.COLD_TAIGA_HILLS, "Cold Taiga Hills", new String[] {"31"}));
        list.add(getAlias(Biome.COLD_TAIGA_MOUNTAINS, "Cold Taiga M", new String[] {"158"}));
        list.add(getAlias(Biome.DEEP_OCEAN, "Deep Ocean", new String[] {"24"}));
        list.add(getAlias(Biome.DESERT, "Desert", new String[] {"2"}));
        list.add(getAlias(Biome.DESERT_HILLS, "Desert Hills", new String[] {"17"}));
        list.add(getAlias(Biome.DESERT_MOUNTAINS, "Desert M", new String[] {"130"}));
        list.add(getAlias(Biome.EXTREME_HILLS, "Extreme Hills", new String[] {"3"}));
        list.add(getAlias(Biome.EXTREME_HILLS_MOUNTAINS, "Extreme Hills M", new String[] {"131"}));
        list.add(getAlias(Biome.EXTREME_HILLS_PLUS, "Extreme Hills+", new String[] {"34"}));
        list.add(getAlias(Biome.EXTREME_HILLS_PLUS_MOUNTAINS, "Extreme Hills+ M", new String[] {"162"}));
        list.add(getAlias(Biome.FLOWER_FOREST, "Flower Forest", new String[] {"132"}));
        list.add(getAlias(Biome.FOREST, "Forest", new String[] {"4"}));
        list.add(getAlias(Biome.FOREST_HILLS, "Forest Hills", new String[] {"18"}));
        list.add(getAlias(Biome.FROZEN_OCEAN, "FrozenOcean", new String[] {"10"}));
        list.add(getAlias(Biome.FROZEN_RIVER, "Frozen River", new String[] {"11"}));
        list.add(getAlias(Biome.HELL, "Hell", new String[] {"8", "nether"}));
        list.add(getAlias(Biome.ICE_MOUNTAINS, "Ice Mountains", new String[] {"13"}));
        list.add(getAlias(Biome.ICE_PLAINS, "Ice Plains", new String[] {"12"}));
        list.add(getAlias(Biome.ICE_PLAINS_SPIKES, "Ice Plains Spikes", new String[] {"140"}));
        list.add(getAlias(Biome.JUNGLE, "Jungle", new String[] {"21"}));
        list.add(getAlias(Biome.JUNGLE_EDGE, "Jungle Edge", new String[] {"23"}));
        list.add(getAlias(Biome.JUNGLE_EDGE_MOUNTAINS, "Jungle Edge M", new String[] {"151"}));
        list.add(getAlias(Biome.JUNGLE_HILLS, "Jungle Hills", new String[] {"22"}));
        list.add(getAlias(Biome.JUNGLE_MOUNTAINS, "Jungle M", new String[] {"149"}));
        list.add(getAlias(Biome.MEGA_SPRUCE_TAIGA, "Mega Spruce Taiga", new String[] {"160"}));
        list.add(getAlias(Biome.MEGA_SPRUCE_TAIGA_HILLS, "Redwood Taiga Hills M", new String[] {"161"}));
        list.add(getAlias(Biome.MEGA_TAIGA, "Mega Taiga", new String[] {"32"}));
        list.add(getAlias(Biome.MEGA_TAIGA_HILLS, "Mega Taiga Hills", new String[] {"33"}));
        list.add(getAlias(Biome.MESA, "Mesa", new String[] {"37"}));
        list.add(getAlias(Biome.MESA_BRYCE, "Mesa (Bryce)", new String[] {"165"}));
        list.add(getAlias(Biome.MESA_PLATEAU, "Mesa Plateau", new String[] {"39"}));
        list.add(getAlias(Biome.MESA_PLATEAU_FOREST, "Mesa Plateau F", new String[] {"38"}));
        list.add(getAlias(Biome.MESA_PLATEAU_FOREST_MOUNTAINS, "Mesa Plateau F M", new String[] {"166"}));
        list.add(getAlias(Biome.MESA_PLATEAU_MOUNTAINS, "Mesa Plateau M", new String[] {"167"}));
        list.add(getAlias(Biome.MUSHROOM_ISLAND, "Mushroom Island", new String[] {"14"}));
        list.add(getAlias(Biome.MUSHROOM_SHORE, "Mushroom Island Shore", new String[] {"15"}));
        list.add(getAlias(Biome.OCEAN, "Ocean", new String[] {"0"}));
        list.add(getAlias(Biome.PLAINS, "Plains", new String[] {"1"}));
        list.add(getAlias(Biome.RIVER, "River", new String[] {"7"}));
        list.add(getAlias(Biome.ROOFED_FOREST, "Roofed Forest", new String[] {"29"}));
        list.add(getAlias(Biome.ROOFED_FOREST_MOUNTAINS, "Roofed Forest M", new String[] {"157"}));
        list.add(getAlias(Biome.SAVANNA, "Savanna", new String[] {"35"}));
        list.add(getAlias(Biome.SAVANNA_MOUNTAINS, "Savanna M", new String[] {"163"}));
        list.add(getAlias(Biome.SAVANNA_PLATEAU, "Savanna Plateau", new String[] {"36"}));
        list.add(getAlias(Biome.SAVANNA_PLATEAU_MOUNTAINS, "Savanna Plateau M", new String[] {"164"}));
        list.add(getAlias(Biome.SKY, "The End", new String[] {"9", "sky", "end"}));
        list.add(getAlias(Biome.SMALL_MOUNTAINS, "Plains M", new String[] {"128"}));
        list.add(getAlias(Biome.STONE_BEACH, "Stone Beach", new String[] {"25"}));
        list.add(getAlias(Biome.SUNFLOWER_PLAINS, "Sunflower Plains", new String[] {"129"}));
        list.add(getAlias(Biome.SWAMPLAND, "Swampland", new String[] {"6"}));
        list.add(getAlias(Biome.SWAMPLAND_MOUNTAINS, "Swampland M", new String[] {"134"}));
        list.add(getAlias(Biome.TAIGA, "Taiga", new String[] {"5"}));
        list.add(getAlias(Biome.TAIGA_HILLS, "Taiga Hills", new String[] {"19"}));
        list.add(getAlias(Biome.TAIGA_MOUNTAINS, "Taiga M", new String[] {"133"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Dye Colors
        list.clear();
        currentType = AliasType.DYE_COLOR;
        list.add(getAlias(DyeColor.BLACK, "Black", new String[] {"15", "bla", "bl"}));
        list.add(getAlias(DyeColor.BLUE, "Blue", new String[] {"11", "darkblue", "blu", "darkblue", "dblue"}));
        list.add(getAlias(DyeColor.BROWN, "Brown", new String[] {"12", "bro"}));
        list.add(getAlias(DyeColor.CYAN, "Cyan", new String[] {"9", "darkaqua", "cya", "daqua", "darkcyan", "dcyan"}));
        list.add(getAlias(DyeColor.GRAY, "Gray", new String[] {"7", "darkgray", "grey", "darkgrey", "dgray", "dgrey"}));
        list.add(getAlias(DyeColor.GREEN, "Green", new String[] {"13", "darkgreen", "dgreen", "dgr", "gre"}));
        list.add(getAlias(DyeColor.LIGHT_BLUE, "Light Blue", new String[] {"3", "aqua", "lblue", "lbl"}));
        list.add(getAlias(DyeColor.LIME, "Lime", new String[] {"5", "lightgreen", "lgreen", "lim", "lgr"}));
        list.add(getAlias(DyeColor.MAGENTA, "Magenta", new String[] {"2", "lightpurple", "lpurple", "mag", "magent", "lpurp", "lightpurp"}));
        list.add(getAlias(DyeColor.ORANGE, "Orange", new String[] {"1", "orng"}));
        list.add(getAlias(DyeColor.PINK, "Pink", new String[] {"6", "pin"}));
        list.add(getAlias(DyeColor.PURPLE, "Purple", new String[] {"10", "darkpurple", "dpurple", "purp", "darkpurp", "dpurp"}));
        list.add(getAlias(DyeColor.RED, "Red", new String[] {"14", "darkred", "dred"}));
        list.add(getAlias(DyeColor.SILVER, "Light Gray", new String[] {"8", "silver", "lightgrey", "lgray", "lgrey"}));
        list.add(getAlias(DyeColor.WHITE, "White", new String[] {"0", "whi"}));
        list.add(getAlias(DyeColor.YELLOW, "Yellow", new String[] {"4", "yel", "ylw"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Firework Effects
        list.clear();
        currentType = AliasType.FIREWORK_EFFECT;
        list.add(getAlias(FireworkEffect.Type.BALL, "Ball", new String[] {"ba", "sb", "smallball", "sball", "smallb", "sphere", "smallsphere", "ssphere", "smalls", "o"}));
        list.add(getAlias(FireworkEffect.Type.BALL_LARGE, "Large Ball", new String[] {"lb", "bb", "bigball", "bball", "bigb", "bigsphere", "bsphere", "bigs", "lball", "largeb", "largesphere", "lsphere", "larges", "O"}));
        list.add(getAlias(FireworkEffect.Type.BURST, "Burst", new String[] {"bur", "bu", "erupt", "flare", "explode", "gust", "@"}));
        list.add(getAlias(FireworkEffect.Type.CREEPER, "Creeper", new String[] {"creep", "cr", "creeperface", "face", "#"}));
        list.add(getAlias(FireworkEffect.Type.STAR, "Star", new String[] {"sta", "st", "*"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Horse Variants
        list.clear();
        currentType = AliasType.HORSE_VARIANT;
        list.add(getAlias(Horse.Variant.DONKEY, "Donkey", new String[] {"donk", "don", "do"}));
        list.add(getAlias(Horse.Variant.HORSE, "Horse", new String[] {"regular", "normal", "vanilla", "hor", "ho"}));
        list.add(getAlias(Horse.Variant.MULE, "Mule", new String[] {"mul", "mu"}));
        list.add(getAlias(Horse.Variant.SKELETON_HORSE, "Skeleton Horse", new String[] {"skeleton", "skeli", "skeletonh", "shorse", "sh"}));
        list.add(getAlias(Horse.Variant.UNDEAD_HORSE, "Undead Horse", new String[]{"zombie", "zombiehorse", "zombieh", "undeadh", "undead", "uhorse", "zhorse", "uh", "zh"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Horse Colors
        list.clear();
        currentType = AliasType.HORSE_COLOR;
        list.add(getAlias(Horse.Color.BLACK, "Black", new String[] {"bl", "b"}));
        list.add(getAlias(Horse.Color.BROWN, "Brown", new String[] {"br", "lightbrown", "lbrown", "lbr", "lb"}));
        list.add(getAlias(Horse.Color.CHESTNUT, "Chestnut", new String[] {"chest", "nut", "cnut", "chestn", "cn"}));
        list.add(getAlias(Horse.Color.CREAMY, "Creamy", new String[]{"cream", "cr"}));
        list.add(getAlias(Horse.Color.DARK_BROWN, "Dark Brown", new String[] {"db", "dbr", "darkb", "dbrown"}));
        list.add(getAlias(Horse.Color.GRAY, "Gray", new String[] {"grey", "gr"}));
        list.add(getAlias(Horse.Color.WHITE, "White", new String[] {"wh", "w"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Horse Styles
        list.clear();
        currentType = AliasType.HORSE_STYLE;
        list.add(getAlias(Horse.Style.BLACK_DOTS, "Black Dots", new String[] {"blackdot", "bd", "blackd", "bdots", "bdot"}));
        list.add(getAlias(Horse.Style.NONE, "None", new String[] {"no", "n"}));
        list.add(getAlias(Horse.Style.WHITE, "White", new String[] {"wh", "w"}));
        list.add(getAlias(Horse.Style.WHITE_DOTS, "White Dots", new String[] {"whitedot", "wd", "whited", "wdots", "wdot"}));
        list.add(getAlias(Horse.Style.WHITEFIELD, "Whitefield", new String[]{"wf", "whitef", "wfield"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Ocelot Types
        list.clear();
        currentType = AliasType.OCELOT_TYPES;
        list.add(getAlias(Ocelot.Type.BLACK_CAT, "Black", new String[] {"blackcat", "b"}));
        list.add(getAlias(Ocelot.Type.RED_CAT, "Red", new String[] {"redcat", "r"}));
        list.add(getAlias(Ocelot.Type.SIAMESE_CAT, "Siamese", new String[] {"siamesecat", "siam", "s"}));
        list.add(getAlias(Ocelot.Type.WILD_OCELOT, "Wild", new String[]{"wildocelot", "default", "classic", "natural", "w"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Rabbit Types
        list.clear();
        currentType = AliasType.RABBIT_TYPES;
        list.add(getAlias(Rabbit.Type.BLACK, "Black", new String[] {"bl", "b"}));
        list.add(getAlias(Rabbit.Type.BLACK_AND_WHITE, "Black and White", new String[] {"baw", "bw", "bandw", "whiteandblack", "wab", "wb", "wandb"}));
        list.add(getAlias(Rabbit.Type.BROWN, "Brown", new String[] {"bro", "br"}));
        list.add(getAlias(Rabbit.Type.GOLD, "Gold", new String[] {"gol", "go", "creamy", "cream"}));
        list.add(getAlias(Rabbit.Type.SALT_AND_PEPPER, "Salt and Pepper", new String[] {"sap", "sandp", "sp", "saltpepper", "peppersalt", "pepperandsalt"}));
        list.add(getAlias(Rabbit.Type.THE_KILLER_BUNNY, "Killer Bunny", new String[] {"kill", "thekillerbunny", "killer", "redeye", "reye", "kb", "tkb"}));
        list.add(getAlias(Rabbit.Type.WHITE, "White", new String[] {"wh", "w"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Villager Types/Professions
        list.clear();
        currentType = AliasType.VILLAGER_TYPES;
        list.add(getAlias(Villager.Profession.BLACKSMITH, "Blacksmith", new String[] {"bl", "blacks", "bsmith", "smith", "armorer", "gray", "grey"}));
        list.add(getAlias(Villager.Profession.BUTCHER, "Butcher", new String[] {"bu", "leatherworker", "lworker", "leatherw", "leather", "killer"}));
        list.add(getAlias(Villager.Profession.FARMER, "Farmer", new String[] {"fa", "farm", "regular", "vanilla", "normal", "brown", "fisherman", "shepherd", "fletcher"}));
        list.add(getAlias(Villager.Profession.LIBRARIAN, "Librarian", new String[] {"li", "library", "white"}));
        list.add(getAlias(Villager.Profession.PRIEST, "Priest", new String[] {"pr", "cleric", "purple", "magenta", "red"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Banner patterns
        list.clear();
        currentType = AliasType.BANNER_PATTERNS;
        list.add(getAlias(PatternType.BORDER, "Border", new String[] {"bordure", "bo"}));
        list.add(getAlias(PatternType.BRICKS, "Brick", new String[] {"fieldmasoned", "bri", "bricks"}));
        list.add(getAlias(PatternType.CIRCLE_MIDDLE, "Middle Circle", new String[] {"roundel", "mc", "circle", "round"}));
        list.add(getAlias(PatternType.CREEPER, "Creeper", new String[] {"creepercharge", "cre", "creeperface"}));
        list.add(getAlias(PatternType.CROSS, "Diagonal Cross", new String[] {"saltire", "dcross", "cr"}));
        list.add(getAlias(PatternType.CURLY_BORDER, "Curly Border", new String[] {"bordureindented", "cbo", "curlyb", "cborder"}));
        list.add(getAlias(PatternType.DIAGONAL_LEFT, "Left of Diagonal", new String[] {"perbendsinister", "ld", "ldiagonal"}));
        list.add(getAlias(PatternType.DIAGONAL_LEFT_MIRROR, "Left of upside-down Diagonal", new String[] {"perbendinverted", "lud", "ldiagonalud"}));
        list.add(getAlias(PatternType.DIAGONAL_RIGHT, "Right of Diagonal", new String[] {"perbendsinisterinverted", "rd", "rdiagonal"}));
        list.add(getAlias(PatternType.DIAGONAL_RIGHT_MIRROR, "Right of upside-down Diagonal", new String[] {"perbend", "rud", "rdiagonalud"}));
        list.add(getAlias(PatternType.FLOWER, "Flower", new String[] {"flowercharge", "flo", "sunflower"}));
        list.add(getAlias(PatternType.GRADIENT, "Gradient", new String[] {"gra", "grad", "gradientdown", "graddown"}));
        list.add(getAlias(PatternType.GRADIENT_UP, "Gradient upside-down", new String[] {"basegradient", "gru", "gradu", "gradup", "gradientup"}));
        list.add(getAlias(PatternType.HALF_HORIZONTAL, "Horizontal Half (top)", new String[] {"perfess", "hh", "tophalf", "halftop", "htop", "toph"}));
        list.add(getAlias(PatternType.HALF_HORIZONTAL_MIRROR, "Horizontal Half (bottom)", new String[] {"perfessinverted", "hhb", "bottomhalf", "halfbottom", "hbottom", "bottomh"}));
        list.add(getAlias(PatternType.HALF_VERTICAL, "Vertical Half (left)", new String[] {"perpale", "vh", "lefthalf", "halfleft", "hleft", "lefth"}));
        list.add(getAlias(PatternType.HALF_VERTICAL_MIRROR, "Vertical Half (right)", new String[] {"perpaleinverted", "vhr", "righthalf", "halfright", "hright", "righth"}));
        list.add(getAlias(PatternType.MOJANG, "Mojang", new String[] {"mojangcharge", "moj", "logo"}));
        list.add(getAlias(PatternType.RHOMBUS_MIDDLE, "Middle Rhombus", new String[] {"lozenge", "mr", "rhombus", "dsquare", "diagonalsquare", "rhomb"}));
        list.add(getAlias(PatternType.SKULL, "Skull", new String[] {"skullcharge", "sku", "wither", "witherskull", "bone", "bones"}));
        list.add(getAlias(PatternType.SQUARE_BOTTOM_LEFT, "Bottom Left Corner", new String[] {"basedextercanton", "bl", "blcorner", "cornerbl"}));
        list.add(getAlias(PatternType.SQUARE_BOTTOM_RIGHT, "Bottom Right Corner", new String[] {"basesinistercanton", "br", "brcorner", "cornerbr"}));
        list.add(getAlias(PatternType.SQUARE_TOP_LEFT, "Top Left Corner", new String[] {"chiefdextercanton", "tl", "tlcorner", "cornertl"}));
        list.add(getAlias(PatternType.SQUARE_TOP_RIGHT, "Top Right Corner", new String[] {"chiefsinistercanton", "tr", "trcorner", "cornertr"}));
        list.add(getAlias(PatternType.STRAIGHT_CROSS, "Square Cross", new String[] {"straightcross", "scross", "cross", "sc"}));
        list.add(getAlias(PatternType.STRIPE_BOTTOM, "Bottom Stripe", new String[] {"basefess", "bs", "bottoms"}));
        list.add(getAlias(PatternType.STRIPE_CENTER, "Center Stripe (Vertical)", new String[] {"pale", "cs", "centerstripe", "centers"}));
        list.add(getAlias(PatternType.STRIPE_DOWNLEFT, "Down Left Stripe", new String[] {"bendsinister", "dls", "downleft", "downlefts"}));
        list.add(getAlias(PatternType.STRIPE_DOWNRIGHT, "Down Right Stripe", new String[] {"bend", "drs", "downright", "downrights"}));
        list.add(getAlias(PatternType.STRIPE_LEFT, "Left Stripe", new String[] {"paledexter", "ls", "lefts"}));
        list.add(getAlias(PatternType.STRIPE_MIDDLE, "Middle Stripe (Horizontal)", new String[] {"fess", "ms", "middlestripe", "middles"}));
        list.add(getAlias(PatternType.STRIPE_RIGHT, "Right Stripe", new String[] {"palesinister", "rs", "rights"}));
        list.add(getAlias(PatternType.STRIPE_SMALL, "Small (Vertical) Stripes", new String[] {"paly", "ss", "smallstripes", "smalls", "stripes", "lines"}));
        list.add(getAlias(PatternType.STRIPE_TOP, "Top Stripe", new String[] {"chieffess", "ts", "tops"}));
        list.add(getAlias(PatternType.TRIANGLE_BOTTOM, "Bottom Triangle", new String[] {"chevron", "bt", "btriangle", "triangleb", "btri", "trib"}));
        list.add(getAlias(PatternType.TRIANGLE_TOP, "Top Triangle", new String[] {"invertedchevron", "tt", "ttriangle", "trianget", "ttri", "trit"}));
        list.add(getAlias(PatternType.TRIANGLES_BOTTOM, "Bottom Triangle Sawtooth", new String[] {"baseindented", "bts", "trianglesbottom", "sawtop", "sawtoothtop"}));
        list.add(getAlias(PatternType.TRIANGLES_TOP, "Top Triangle Sawtooth", new String[] {"chiefindented", "tts", "trianglestop", "sawbot", "sawtoothbot"}));
        aliases.put(currentType, new ArrayList<Alias>(list));

        //Trees
        list.clear();
        currentType = AliasType.TREES;
        aliases.put(currentType, new ArrayList<Alias>(list));


        //Sounds
        list.clear();
        currentType = AliasType.SOUNDS;
        aliases.put(currentType, new ArrayList<Alias>(list));

    }

    private static Alias getAlias(Object enumValue, String name, String[] aliases) {
        return new Alias(currentType, enumValue.toString(), name, aliases);
    }

    private static Alias getAlias(String key, String name, String[] aliases) {
        return new Alias(currentType, key, name, aliases);
    }

}
