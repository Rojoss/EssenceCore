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

package info.mcessence.essence.entity;

import info.mcessence.essence.aliases.AliasType;
import info.mcessence.essence.aliases.Aliases;
import info.mcessence.essence.arguments.*;
import info.mcessence.essence.arguments.internal.Argument;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.CommandMinecart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum EntityTag {
    //All entities
    VELOCITY(new VectorArg("velocity"), "setVelocity", Entity.class),
    FIRETICKS(new IntArg("fireticks"), "setFireTicks", Entity.class),
    TICKSLIVED(new IntArg("tickslived"), "setTicksLived", Entity.class),
    NAME(new StringArg("name"), "setCustomName", Entity.class),
    NAMEVISIBLE(new BoolArg("namevisible", true), "setCustomNameVisible", Entity.class),

    //Damagable/Living
    HEALTH(new DoubleArg("health"), "setHealth", Damageable.class),
    MAXHEALTH(new DoubleArg("maxhealth"), "setMaxHealth", Damageable.class),
    REMOVEFAR(new BoolArg("removefar", true), "setRemoveWhenFarAway", LivingEntity.class),
    PICKUPITEMS(new BoolArg("pickupitems", true), "setCanPickupItems", LivingEntity.class),
    AIR(new IntArg("air"), "setAir", LivingEntity.class),

    //Ageable
    AGE(new IntArg("age"), "setAge", Ageable.class),
    AGELOCK(new BoolArg("agelock", true), "setAgeLock", Ageable.class),
    BABY(new BoolArg("baby", true), "setBaby", Ageable.class),
    BREED(new BoolArg("breed", true), "setBreed", Ageable.class),

    //Projectile
    BOUNCE(new BoolArg("bounce", true), "setBounce", Projectile.class),

    //Hanging
    DIR(new ListArg("dir", Arrays.asList("north","east","south","west","up","down")), "CUSTOM", Hanging.class),

    //ArmorStand
    POSE(new VectorArg("pose"), "setBodyPose", ArmorStand.class),
    HEAD(new VectorArg("head"), "setHeadPose", ArmorStand.class),
    LARM(new VectorArg("larm"), "setLeftArmPose", ArmorStand.class),
    RARM(new VectorArg("rarm"), "setRightArmPose", ArmorStand.class),
    LLEG(new VectorArg("lleg"), "setLeftLegPose", ArmorStand.class),
    RLEG(new VectorArg("rleg"), "setRightLegPose", ArmorStand.class),
    BASEPLATE(new BoolArg("baseplate", true), "setBasePlate", ArmorStand.class),
    GRAVITY(new BoolArg("gravity", true), "setGravity", ArmorStand.class),
    VISIBLE(new BoolArg("visible", true), "setVisible", ArmorStand.class),
    ARMS(new BoolArg("arms", true), "setArms", ArmorStand.class),
    SMALL(new BoolArg("small", true), "setSmall", ArmorStand.class),
    MARKER(new BoolArg("marker", true), "setMarker", ArmorStand.class),

    //Arrow
    CRITICAL(new BoolArg("critical", true), "setCritical", Arrow.class),
    KNOCKBACK(new IntArg("knockback"), "setKnockbackStrength", Arrow.class),

    //Boat
    HOVER(new BoolArg("hover", true), "setWorkOnLand", Boat.class),
    EMPTYDECEL(new DoubleArg("emptydecel"), "setUnoccupiedDeceleration", Boat.class),
    DECEL(new DoubleArg("decel"), "setOccupiedDeceleration", Boat.class),

    //Cmdblock minecart
    CMD(new StringArg("cmd"), "setCommand", CommandMinecart.class),
    SENDER(new StringArg("sender"), "setName", CommandMinecart.class),

    //Experience
    EXP(new IntArg("exp"), "setExperience", ExperienceOrb.class),

    //Falling block
    DROPITEM(new BoolArg("dropitem", true), "setDropItem", FallingBlock.class),
    HURTENTITIES(new BoolArg("hurtentities", true), "setHurtEntities", FallingBlock.class),

    //Firework
    DETONATE(new BoolArg("detonate", true), "detonate", Firework.class),

    //Item
    PICKUPDELAY(new IntArg("pickupdelay"), "setPickupDelay", Item.class),

    //Itemframe
    ROTATION(new ListArg("rotation", Arrays.asList("0","45","90","135","180","225","270","315","360")), "setRotation", ItemFrame.class),

    //Minecart
    SLOWEMPTY(new BoolArg("slowempty", true), "setSlowWhenEmpty", Minecart.class),
    FLYVELOCITY(new VectorArg("flyvelocity"), "setFlyingVelocityMod", Minecart.class),
    DERAILVELOCITY(new VectorArg("derailvelocity"), "setDerailedVelocityMod", Minecart.class),
    DISPLAYBLOCK(new MaterialArg("displayblock"), "setDisplayBlock", Minecart.class),
    BLOCKOFFSET(new IntArg("blockoffset"), "setDisplayBlockOffset", Minecart.class),

    //Painting
    ART(new MappedListArg("art", Aliases.getAliasesMap(AliasType.PAINTING)), "setArt", Painting.class),

    //Primed TnT
    FUSETICKS(new IntArg("fuseticks"), "setFuseTicks", TNTPrimed.class),

    //Witherskull
    CHARGED(new BoolArg("charged", true), "setCharged", WitherSkull.class),

    //Bat
    AWAKE(new BoolArg("awake", true), "setAwake", Bat.class),

    //Creeper
    POWERED(new BoolArg("powered", true), "setPowered", Creeper.class),

    //Enderman
    HOLDING(new MaterialArg("holding"), "setCarriedMaterial", Enderman.class),

    //Guardian
    ELDER(new BoolArg("elder", true), "setElder", Guardian.class),

    //Horse
    VARIANT(new MappedListArg("variant", Aliases.getAliasesMap(AliasType.HORSE_VARIANT)), "setVariant", Horse.class),
    COLOR(new MappedListArg("color", Aliases.getAliasesMap(AliasType.HORSE_COLOR)), "setColor", Horse.class),
    STYLE(new MappedListArg("style", Aliases.getAliasesMap(AliasType.HORSE_STYLE)), "setStyle", Horse.class),
    CHEST(new BoolArg("chest", true), "setCarryingChest", Horse.class),
    DOMESTICATION(new IntArg("domestication"), "setDomestication", Horse.class),
    MAXDOMESTICATION(new IntArg("maxdomestication"), "setMaxDomestication", Horse.class),
    JUMPSTRENGTH(new DoubleArg("jumpstrength"), "setJumpStrength", Horse.class),
    ARMOR(new ItemArg("armor"), "setArmor", Horse.class),
    SADDLEITEM(new ItemArg("saddleitem"), "setSaddle", Horse.class),

    //Ocelot
    CATTYPE(new MappedListArg("cattype", Aliases.getAliasesMap(AliasType.OCELOT_TYPES)), "setCatType", Ocelot.class),

    //Rabbit
    RABITTYPE(new MappedListArg("rabbittype", Aliases.getAliasesMap(AliasType.RABBIT_TYPES)), "setRabbitType", Rabbit.class),

    //Pigman
    ANGER(new IntArg("anger"), "setAnger", PigZombie.class),

    //Sheep
    SHEARED(new BoolArg("sheared", true), "setSheared", Sheep.class),

    //Skeleton
    WITHER(new BoolArg("wither", true), "makeWitherSkeleton", Wither.class),

    //Slime
    SIZE(new IntArg("size"), "setSize", Slime.class),

    //Villager
    PROFESSION(new MappedListArg("profession", Aliases.getAliasesMap(AliasType.VILLAGER_TYPES)), "setProfession", Villager.class),

    //Wolf
    COLLAR(new MappedListArg("collar", Aliases.getAliasesMap(AliasType.DYE_COLOR)), "setCollarColor", Wolf.class),

    //Zombie
    VILLAGER(new BoolArg("villager", true), "setVillager", Zombie.class),

    //Mixed
    SADDLE(new BoolArg("saddle", true), "setSaddle", Horse.class, Pig.class),
    ANGRY(new BoolArg("angry", true), "setAngry", Wolf.class, PigZombie.class),
    SITTING(new BoolArg("sitting", true), "setSitting", Ocelot.class, Wolf.class),
    MAXSPEED(new DoubleArg("maxspeed"), "setMaxSpeed", Minecart.class, Boat.class),
    ITEM(new ItemArg("item"), "setItem", Item.class, ItemFrame.class, ThrownPotion.class),
    HAND(new ItemArg("hand"), "setItemInHand", ArmorStand.class, LivingEntity.class),
    HELMET(new ItemArg("helmet"), "setHelmet", ArmorStand.class, LivingEntity.class),
    CHESTPLATE(new ItemArg("chestplate"), "setChestplate", ArmorStand.class, LivingEntity.class),
    LEGGINGS(new ItemArg("leggings"), "setLeggings", ArmorStand.class, LivingEntity.class),
    BOOTS(new ItemArg("boots"), "setBoots", ArmorStand.class, LivingEntity.class),

    //Tags
    NOAI(new BoolArg("noai", true), "setAI", Entity.class),
    INVULNERABLE(new BoolArg("setInvulnerable", true), "invulnerable", Entity.class),
    SILENT(new BoolArg("silent", true), "setSilent", Entity.class),
    INVISIBLE(new BoolArg("invisible", true), "setInvisible", Entity.class),
    ;

    private Argument argument;
    private String method;
    private Class<? extends Entity>[] classes;

    EntityTag(Argument argument, String method, Class<? extends Entity>... classes) {
        this.argument = argument;
        this.method = method;
        this.classes = classes;
    }

    public Class<? extends Entity>[] getClasses() {
        return classes;
    }

    public Argument getArg() {
        return argument;
    }

    public String getMethod() {
        return method;
    }

    public static EntityTag fromString(String name) {
        name = name.toLowerCase().replace("_","");
        for (EntityTag et : values()) {
            if (et.toString().toLowerCase().replace("_", "").equals(name)) {
                return et;
            }
        }
        return null;
    }

    public static List<EntityTag> getTags(EntityType type) {
        List<EntityTag> tags = new ArrayList<EntityTag>();
        for (EntityTag tag : EntityTag.values()) {
            for (Class<? extends Entity> clazz : tag.getClasses()) {
                if (clazz.isAssignableFrom(type.getEntityClass())) {
                    tags.add(tag);
                }
            }
        }
        return tags;
    }

}
