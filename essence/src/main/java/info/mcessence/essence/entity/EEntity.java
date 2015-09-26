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

import info.mcessence.essence.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.*;

public class EEntity {

    private Entity entity;

    //region Constructor and static methods.
    /**
     * Create a new EEntity from a Bukkit Entity.
     * @param entity A Bukkit Entity.
     */
    public EEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Spawns an Entity at the given location.
     * @param type The base entity type to spawn.
     * @param location The location where the entity needs to be spawned.
     * @return EEntity instance.
     */
    public static EEntity create(EntityType type, Location location) {
        if (type.isSpawnable()) {
            return new EEntity(location.getWorld().spawnEntity(location, type));
        } else {
            return new EEntity(location.getWorld().spawn(location, type.getEntityClass()));
        }
    }

    /**
     * Spawns multiple entities at the given location.
     * @param type The base entity type to spawn.
     * @param location The location where the entity needs to be spawned.
     * @param amount The amount of entities to spawn.
     * @return List<EEntity> with all the EEntity instances.
     */
    public static List<EEntity> create(EntityType type, Location location, int amount) {
        List<EEntity> spawned = new ArrayList<EEntity>();
        for (int i = 0 ; i < amount; i++) {
            if (type.isSpawnable()) {
                spawned.add(new EEntity(location.getWorld().spawnEntity(location, type)));
            } else {
                spawned.add(new EEntity(location.getWorld().spawn(location, type.getEntityClass())));
            }
        }
        return spawned;
    }

    /**
     * Get the Bukkit Entity instance.
     * @return Entity instance.
     */
    public Entity bukkit() {
        return entity;
    }
    //endregion



    //region Main interfaces (Bukkit methods)
    // ##################################################
    // ################## ALL ENTITIES ##################
    // ##################################################
    //region All entities.

    //MISC
    /** @see Entity#getType()  */
    public EntityType getType() {
        return entity.getType();
    }

    /** @see Entity#getUniqueId()  */
    public UUID getUUID() {
        return entity.getUniqueId();
    }

    /** @see Entity#getUniqueId()  */
    public int getID() {
        return entity.getEntityId();
    }

    /** @see Entity#spigot() */
    public Entity.Spigot getSpigot() {
        return entity.spigot();
    }

    /** @see Entity#remove() */
    public void remove() {
        entity.remove();
    }

    //CHECKS
    /** @see Entity#isValid()  */
    public boolean isValid() {
        return entity.isValid();
    }

    /** @see Entity#isDead()  */
    public boolean isDead() {
        return entity.isDead();
    }

    /** @see Entity#isOnGround()  */
    public boolean isGrounded() {
        return entity.isOnGround();
    }

    //LOCATION
    /** @see Entity#getLocation() */
    public Location getLocation() {
        return entity.getLocation();
    }

    /** @see Entity#getLocation(Location) */
    public Location getLocation(Location loc) {
        return entity.getLocation(loc);
    }

    /** @see Entity#getWorld() */
    public World getWorld() {
        return entity.getWorld();
    }

    //TELEPORT
    /** @see Entity#teleport(Location) */
    public EEntity teleport(Location target) {
        entity.teleport(target);
        return this;
    }

    /** @see Entity#teleport(Location, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause) */
    public EEntity teleport(Location target, PlayerTeleportEvent.TeleportCause cause) {
        entity.teleport(target, cause);
        return this;
    }

    /** @see Entity#teleport(Entity) */
    public EEntity teleport(Entity target) {
        entity.teleport(target);
        return this;
    }

    /** @see Entity#teleport(Entity, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause) */
    public EEntity teleport(Entity target, PlayerTeleportEvent.TeleportCause cause) {
        entity.teleport(target, cause);
        return this;
    }

    /** @see Entity#teleport(Entity) */
    public EEntity teleport(EEntity target) {
        entity.teleport(target.bukkit());
        return this;
    }

    /** @see Entity#teleport(Entity, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause) */
    public EEntity teleport(EEntity target, PlayerTeleportEvent.TeleportCause cause) {
        entity.teleport(target.bukkit(), cause);
        return this;
    }

    //VELOCITY
    /** @see Entity#setVelocity(Vector) */
    public EEntity setVelocity(Vector velocity) {
        if (entity instanceof Vehicle) {
            ((Vehicle)entity).setVelocity(velocity);
        } else {
            entity.setVelocity(velocity);
        }
        return this;
    }

    /** @see Entity#setVelocity(Vector) */
    public EEntity setVelocity(float x, float y, float z) {
        if (entity instanceof Vehicle) {
            ((Vehicle)entity).setVelocity(new Vector(x,y,z));
        } else {
            entity.setVelocity(new Vector(x,y,z));
        }
        return this;
    }

    /** @see Entity#getVelocity() */
    public Vector getVelocity() {
        if (entity instanceof Vehicle) {
            return ((Vehicle)entity).getVelocity();
        }
        return entity.getVelocity();
    }

    //PASSENGER
    /** @see Entity#eject() */
    public boolean eject() {
        return entity.eject();
    }

    /** @see Entity#setPassenger(Entity) */
    public EEntity setPassenger(Entity passenger) {
        entity.setPassenger(passenger);
        return this;
    }

    /** @see Entity#setPassenger(Entity) */
    public EEntity setPassenger(EEntity passenger) {
        entity.setPassenger(passenger.bukkit());
        return this;
    }

    /** @see Entity#getPassenger() */
    public EEntity getPassenger() {
        return new EEntity(entity.getPassenger());
    }

    /** @see Entity#getVehicle() */
    public EEntity getVehicle() {
        return new EEntity(entity.getVehicle());
    }

    /** @see Entity#leaveVehicle() */
    public boolean leaveVehicle() {
        return entity.leaveVehicle();
    }

    /** @see Entity#isEmpty()  */
    public boolean isEmpty() {
        return entity.isEmpty();
    }

    //STATUS
    /** @see Entity#setFallDistance(float) */
    public EEntity setFallDistance(float distance) {
        entity.setFallDistance(distance);
        return this;
    }

    /** @see Entity#getFallDistance() */
    public float getFallDistance() {
        return entity.getFallDistance();
    }

    /** @see Entity#setFireTicks(int) */
    public EEntity setFireTicks(int ticks) {
        entity.setFireTicks(ticks);
        return this;
    }

    /** @see Entity#getFireTicks() */
    public int getFireTicks() {
        return entity.getFireTicks();
    }

    /** @see Entity#getMaxFireTicks() */
    public int getMaxFireTicks() {
        return entity.getMaxFireTicks();
    }

    /** @see Entity#setTicksLived(int) */
    public EEntity setTicksLived(int ticks) {
        entity.setTicksLived(ticks);
        return this;
    }

    /** @see Entity#getTicksLived() */
    public int getTicksLived() {
        return entity.getTicksLived();
    }

    //DAMAGE CAUSE
    /** @see Entity#setLastDamageCause(EntityDamageEvent) */
    public EEntity setLastDamageCause(EntityDamageEvent event) {
        entity.setLastDamageCause(event);
        return this;
    }

    /** @see Entity#getLastDamageCause() */
    public EntityDamageEvent getLastDamageCause() {
        return entity.getLastDamageCause();
    }

    //NAME
    /** @see Entity#setCustomName(String) */
    public EEntity setCustomName(String name) {
        entity.setCustomName(Util.color(name));
        return this;
    }

    /** @see Entity#getCustomName() */
    public String getCustomName() {
        return entity.getCustomName();
    }

    /** @see Entity#setCustomNameVisible(boolean) */
    public EEntity setCustomNameVisible(boolean flag) {
        entity.setCustomNameVisible(flag);
        return this;
    }

    /** @see Entity#isCustomNameVisible() */
    public boolean isCustomNameVisible() {
        return entity.isCustomNameVisible();
    }
    //endregion


    // ##################################################
    // ############## DAMAGEABLE ENTITIES ###############
    // ##################################################
    //region Damagable entities.

    //Health
    /** @see Damageable#setHealth(double) */
    public EEntity setHealth(double amount) {
        if (entity instanceof Damageable) {
            ((Damageable)entity).setHealth(amount);
        }
        return this;
    }

    /** @see Damageable#getHealth() */
    public Double getHealth() {
        if (entity instanceof Damageable) {
            return ((Damageable) entity).getHealth();
        }
        return null;
    }

    //Max health
    /** @see Damageable#setMaxHealth(double) */
    public EEntity setMaxHealth(double amount) {
        if (entity instanceof Damageable) {
            ((Damageable)entity).setMaxHealth(amount);
        }
        return this;
    }

    /** @see Damageable#getMaxHealth() */
    public Double getMaxHealth() {
        if (entity instanceof Damageable) {
            return ((Damageable)entity).getMaxHealth();
        }
        return null;
    }

    /** @see Damageable#resetMaxHealth() */
    public EEntity resetMaxHealth() {
        if (entity instanceof Damageable) {
            ((Damageable)entity).resetMaxHealth();
        }
        return this;
    }

    //Damage
    /** @see Damageable#damage(double, Entity) */
    public EEntity damage(double amount, Entity entity) {
        if (entity instanceof Damageable) {
            ((Damageable)entity).damage(amount, entity);
        }
        return this;
    }

    /** @see Damageable#damage(double, Entity) */
    public EEntity damage(double amount, EEntity entity) {
        if (entity instanceof Damageable) {
            ((Damageable)entity).damage(amount, entity.bukkit());
        }
        return this;
    }
    //endregion



    // ##################################################
    // ################ LIVING ENTITIES #################
    // ##################################################
    //region Living entities.

    //Potion effects
    /** @see LivingEntity#addPotionEffect(PotionEffect) */
    public EEntity addPotionEffect(PotionEffect effect) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).addPotionEffect(effect);
        }
        return this;
    }

    /** @see LivingEntity#addPotionEffect(PotionEffect, boolean) */
    public EEntity addPotionEffect(PotionEffect effect, boolean force) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).addPotionEffect(effect, force);
        }
        return this;
    }

    /** @see LivingEntity#addPotionEffects(Collection<PotionEffect>) */
    public EEntity addPotionEffects(Collection<PotionEffect> effects) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).addPotionEffects(effects);
        }
        return this;
    }

    /** @see LivingEntity#removePotionEffect(PotionEffectType) */
    public EEntity removePotionEffect(PotionEffectType type) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).removePotionEffect(type);
        }
        return this;
    }

    /** @see LivingEntity#hasPotionEffect(PotionEffectType) */
    public boolean hasPotionEffect(PotionEffectType type) {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).hasPotionEffect(type);
        }
        return false;
    }

    /** @see LivingEntity#getActivePotionEffects() */
    public Collection<PotionEffect> getActivePotionEffects() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getActivePotionEffects();
        }
        return Collections.EMPTY_LIST;
    }

    //Removal
    /** @see LivingEntity#getRemoveWhenFarAway() */
    public boolean getRemoveWhenFarAway(Entity other) {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getRemoveWhenFarAway();
        }
        return false;
    }

    /** @see LivingEntity#setRemoveWhenFarAway(boolean) */
    public EEntity setRemoveWhenFarAway(boolean remove) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setRemoveWhenFarAway(remove);
        }
        return this;
    }

    //Equipment
    /** @see LivingEntity#getEquipment() */
    public EntityEquipment getEquipment() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getEquipment();
        }
        return null;
    }

    /** @see LivingEntity#setCanPickupItems(boolean) */
    public EEntity setCanPickupItems(boolean pickup) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setCanPickupItems(pickup);
        }
        return this;
    }

    /** @see LivingEntity#getCanPickupItems() */
    public boolean getCanPickupItems() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getCanPickupItems();
        }
        return false;
    }

    //Leash
    /** @see LivingEntity#isLeashed() */
    public boolean isLeashed() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).isLeashed();
        }
        return false;
    }

    /** @see LivingEntity#getLeashHolder() */
    public EEntity getLeashHolder() {
        if (entity instanceof LivingEntity) {
            return new EEntity(((LivingEntity)entity).getLeashHolder());
        }
        return null;
    }

    /** @see LivingEntity#setLeashHolder(Entity) */
    public EEntity setLeashHolder(Entity holder) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setLeashHolder(holder);
        }
        return this;
    }

    /** @see LivingEntity#setLeashHolder(Entity) */
    public EEntity setLeashHolder(EEntity holder) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setLeashHolder(holder.bukkit());
        }
        return this;
    }

    //Damage
    /** @see LivingEntity#getKiller() */
    public Player getKiller() {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).getKiller();
        }
        return null;
    }

    /** @see LivingEntity#getNoDamageTicks() */
    public int getNoDamageTicks() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getNoDamageTicks();
        }
        return 0;
    }

    /** @see LivingEntity#getMaximumNoDamageTicks() */
    public int getMaxNoDamageTicks() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getMaximumNoDamageTicks();
        }
        return 0;
    }

    /** @see LivingEntity#setNoDamageTicks(int) */
    public EEntity setNoDamageTicks(int ticks) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setNoDamageTicks(ticks);
        }
        return this;
    }

    /** @see LivingEntity#setMaximumNoDamageTicks(int) */
    public EEntity setMaxNoDamageTicks(int ticks) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setMaximumNoDamageTicks(ticks);
        }
        return this;
    }

    /** @see LivingEntity#getLastDamage() */
    public double getLastDamage() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getLastDamage();
        }
        return 0;
    }

    /** @see LivingEntity#setLastDamage(double) */
    public EEntity setLastDamage(double amount) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setLastDamage(amount);
        }
        return this;
    }

    //Air
    /** @see LivingEntity#setRemainingAir(int) */
    public EEntity setAir(int ticks) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setRemainingAir(ticks);
        }
        return this;
    }

    /** @see LivingEntity#getRemainingAir() */
    public Integer getAir() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity) entity).getRemainingAir();
        }
        return null;
    }

    //Max health
    /** @see LivingEntity#setMaximumAir(int) */
    public EEntity setMaxAir(int ticks) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).setMaximumAir(ticks);
        }
        return this;
    }

    /** @see LivingEntity#getMaximumAir() */
    public Integer getMaxAir() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getMaximumAir();
        }
        return null;
    }

    //LOS
    /** @see LivingEntity#getLineOfSight(Set, int) */
    public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance) {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getLineOfSight(transparent, maxDistance);
        }
        return Collections.emptyList();
    }

    /** @see LivingEntity#hasLineOfSight(Entity) */
    public boolean hasLineOfSight(Entity other) {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).hasLineOfSight(other);
        }
        return false;
    }

    //TODO: Method to check line of sight with location.

    /** @see LivingEntity#getTargetBlock(Set, int) */
    public Block getTargetBlock(Set<Material> transparent, int maxDistance) {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getTargetBlock(transparent, maxDistance);
        }
        return null;
    }

    /** @see LivingEntity#getLastTwoTargetBlocks(Set, int) */
    public List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance) {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getLastTwoTargetBlocks(transparent, maxDistance);
        }
        return Collections.emptyList();
    }

    //EYE HEIGHT
    /** @see LivingEntity#getEyeHeight() */
    public Double getEyeHeight() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getEyeHeight();
        }
        return null;
    }

    /** @see LivingEntity#getEyeLocation() */
    public Location getEyeLocation() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity)entity).getEyeLocation();
        }
        return null;
    }
    //endregion


    // ##################################################
    // ################ AGEABLE ENTITIES ################
    // ##################################################
    //region Agable entities

    //AGE
    /** @see Ageable#getAge() */
    public Integer getAge() {
        if (entity instanceof Ageable) {
            return ((Ageable)entity).getAge();
        }
        return null;
    }

    /** @see Ageable#setAge(int) */
    public EEntity setAge(int age) {
        if (entity instanceof Ageable) {
            ((Ageable)entity).setAge(age);
        }
        return this;
    }

    /** @see Ageable#getAgeLock() */
    public boolean isAgeLocked() {
        if (entity instanceof Ageable) {
            return ((Ageable)entity).getAgeLock();
        }
        return false;
    }

    /** @see Ageable#setAgeLock(boolean) */
    public EEntity setAgeLock(boolean lock) {
        if (entity instanceof Ageable) {
            ((Ageable)entity).setAgeLock(lock);
        }
        return this;
    }

    /** @see Ageable#setBaby() */
    public EEntity setBaby() {
        if (entity instanceof Ageable) {
            ((Ageable)entity).setBaby();
        } else if (entity instanceof Zombie) {
            ((Zombie)entity).setBaby(true);
        } else if (entity instanceof PigZombie) {
            ((PigZombie)entity).setBaby(true);
        }
        return this;
    }

    /** @see Ageable#setAdult() */
    public EEntity setAdult() {
        if (entity instanceof Ageable) {
            ((Ageable)entity).setAdult();
        } else if (entity instanceof Zombie) {
            ((Zombie)entity).setBaby(false);
        } else if (entity instanceof PigZombie) {
            ((PigZombie)entity).setBaby(false);
        }
        return this;
    }

    /** @see Ageable#isAdult() */
    public boolean isAdult() {
        if (entity instanceof Ageable) {
            return ((Ageable)entity).isAdult();
        } else if (entity instanceof Zombie) {
            return !((Zombie)entity).isBaby();
        } else if (entity instanceof Zombie) {
            return !((PigZombie)entity).isBaby();
        }
        return false;
    }

    //BREED
    /** @see Ageable#canBreed() */
    public boolean canBreed() {
        if (entity instanceof Ageable) {
            return ((Ageable)entity).canBreed();
        }
        return false;
    }

    /** @see Ageable#setBreed(boolean) */
    public EEntity setBreed(boolean breed) {
        if (entity instanceof Ageable) {
            ((Ageable)entity).setBreed(breed);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################### CREATURES ####################
    // ##################################################
    //region Creatures

    /** @see Creature#setTarget(LivingEntity) */
    public EEntity setTarget(LivingEntity target) {
        if (entity instanceof Creature) {
            ((Creature)entity).setTarget(target);
        }
        return this;
    }

    /** @see Creature#getTarget() */
    public LivingEntity getTarget() {
        if (entity instanceof Creature) {
            return ((Creature)entity).getTarget();
        }
        return null;
    }
    //endregion


    // ##################################################
    // ################## PROJECTILES ###################
    // ##################################################
    //region Projectiles

    //PROJECTILE
    /** @see Projectile#getShooter() */
    public ProjectileSource getShooter() {
        if (entity instanceof Projectile) {
            return ((Projectile)entity).getShooter();
        }
        return null;
    }

    /** @see Projectile#setShooter(ProjectileSource) */
    public EEntity setShooter(ProjectileSource source) {
        if (entity instanceof Projectile) {
            ((Projectile)entity).setShooter(source);
        }
        return this;
    }

    /** @see Projectile#doesBounce() */
    public boolean doesBounce() {
        if (entity instanceof Projectile) {
            return ((Projectile)entity).doesBounce();
        }
        return false;
    }

    /** @see Projectile#setBounce(boolean) */
    public EEntity setBounce(boolean bounce) {
        if (entity instanceof Projectile) {
            ((Projectile)entity).setBounce(bounce);
        }
        return this;
    }

    //PROJECTILE SOURCE
    /** @see ProjectileSource#launchProjectile(Class)  */
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        if (entity instanceof ProjectileSource) {
            return ((ProjectileSource)entity).launchProjectile(projectile);
        }
        return null;
    }

    /** @see ProjectileSource#launchProjectile(Class, Vector)  */
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
        if (entity instanceof ProjectileSource) {
            return ((ProjectileSource)entity).launchProjectile(projectile, velocity);
        }
        return null;
    }
    //endregion


    // ##################################################
    // #################### HANGING #####################
    // ##################################################
    //region Hanging entities

    /** @see Hanging#setFacingDirection(BlockFace, boolean) */
    public EEntity setFacingDirection(BlockFace face, boolean force) {
        if (entity instanceof Hanging) {
            ((Hanging)entity).setFacingDirection(face, force);
        }
        return this;
    }
    //endregion
    //endregion



    //region Non creature entities. (Bukkit methods)
    // ##################################################
    // ################## ARMOR STAND ###################
    // ##################################################
    //region Armor stands

    //ITEMS
    /** @see ArmorStand#getItemInHand() */
    public ItemStack getItemInHand() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getItemInHand();
        }
        return null;
    }

    /** @see ArmorStand#setItemInHand(ItemStack) */
    public EEntity setItemInHand(ItemStack item) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setItemInHand(item);
        }
        return this;
    }

    /** @see ArmorStand#getBoots() */
    public ItemStack getBoots() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getBoots();
        }
        return null;
    }

    /** @see ArmorStand#setBoots(ItemStack) */
    public EEntity setBoots(ItemStack item) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setBoots(item);
        }
        return this;
    }

    /** @see ArmorStand#getLeggings() */
    public ItemStack getLeggings() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getLeggings();
        }
        return null;
    }

    /** @see ArmorStand#setLeggings(ItemStack) */
    public EEntity setLeggings(ItemStack item) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setLeggings(item);
        }
        return this;
    }

    /** @see ArmorStand#getChestplate() */
    public ItemStack getChestplate() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getChestplate();
        }
        return null;
    }

    /** @see ArmorStand#setChestplate(ItemStack) */
    public EEntity setChestplate(ItemStack item) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setChestplate(item);
        }
        return this;
    }

    /** @see ArmorStand#getHelmet() */
    public ItemStack getHelmet() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getHelmet();
        }
        return null;
    }

    /** @see ArmorStand#setHelmet(ItemStack) */
    public EEntity setHelmet(ItemStack item) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setHelmet(item);
        }
        return this;
    }

    //POSE
    /** @see ArmorStand#getBodyPose() */
    public EulerAngle getBodyPose() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getBodyPose();
        }
        return null;
    }

    /** @see ArmorStand#setBodyPose(EulerAngle) */
    public EEntity setBodyPose(EulerAngle pose) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setBodyPose(pose);
        }
        return this;
    }

    /** @see ArmorStand#getLeftArmPose() */
    public EulerAngle getLeftArmPose() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getLeftArmPose();
        }
        return null;
    }

    /** @see ArmorStand#setLeftArmPose(EulerAngle) */
    public EEntity setLeftArmPose(EulerAngle pose) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setLeftArmPose(pose);
        }
        return this;
    }

    /** @see ArmorStand#getRightArmPose() */
    public EulerAngle getRightArmPose() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getRightArmPose();
        }
        return null;
    }

    /** @see ArmorStand#setRightArmPose(EulerAngle) */
    public EEntity setRightArmPose(EulerAngle pose) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setRightArmPose(pose);
        }
        return this;
    }

    /** @see ArmorStand#getLeftLegPose() */
    public EulerAngle getLeftLegPose() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getLeftLegPose();
        }
        return null;
    }

    /** @see ArmorStand#setLeftLegPose(EulerAngle) */
    public EEntity setLeftLegPose(EulerAngle pose) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setLeftLegPose(pose);
        }
        return this;
    }

    /** @see ArmorStand#getRightLegPose() */
    public EulerAngle getRightLegPose() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getRightLegPose();
        }
        return null;
    }

    /** @see ArmorStand#setRightLegPose(EulerAngle) */
    public EEntity setRightLegPose(EulerAngle pose) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setRightLegPose(pose);
        }
        return this;
    }

    /** @see ArmorStand#getHeadPose() */
    public EulerAngle getHeadPose() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).getHeadPose();
        }
        return null;
    }

    /** @see ArmorStand#setHeadPose(EulerAngle) */
    public EEntity setHeadPose(EulerAngle pose) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setHeadPose(pose);
        }
        return this;
    }

    //BASEPLATE
    /** @see ArmorStand#hasBasePlate() */
    public boolean hasBasePlate() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).hasBasePlate();
        }
        return false;
    }

    /** @see ArmorStand#setBasePlate(boolean) */
    public EEntity setBasePlate(boolean plate) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setBasePlate(plate);
        }
        return this;
    }

    //GRAVITY
    /** @see ArmorStand#hasGravity() */
    public boolean hasGravity() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).hasGravity();
        }
        return false;
    }

    /** @see ArmorStand#setGravity(boolean) */
    public EEntity setGravity(boolean gravity) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setGravity(gravity);
        }
        return this;
    }

    //VISIBILITY
    /** @see ArmorStand#isVisible() */
    public boolean isVisible() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).isVisible();
        }
        return false;
    }

    /** @see ArmorStand#setVisible(boolean) */
    public EEntity setVisible(boolean visible) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setVisible(visible);
        }
        return this;
    }

    //ARMS
    /** @see ArmorStand#hasArms() */
    public boolean hasArms() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).hasArms();
        }
        return false;
    }

    /** @see ArmorStand#setArms(boolean) */
    public EEntity setArms(boolean arms) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setArms(arms);
        }
        return this;
    }

    //SMALL
    /** @see ArmorStand#isSmall() */
    public boolean isSmall() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).isSmall();
        }
        return false;
    }

    /** @see ArmorStand#setSmall(boolean) */
    public EEntity setSmall(boolean small) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setSmall(small);
        }
        return this;
    }

    //MARKER
    /** @see ArmorStand#isSmall() */
    public boolean isMarker() {
        if (entity instanceof ArmorStand) {
            return ((ArmorStand)entity).isMarker();
        }
        return false;
    }

    /** @see ArmorStand#setMarker(boolean) */
    public EEntity setMarker(boolean marker) {
        if (entity instanceof ArmorStand) {
            ((ArmorStand)entity).setMarker(marker);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ###################### ARROW #####################
    // ##################################################
    //region Arrows

    /** @see Arrow#isCritical() */
    public boolean isCritical() {
        if (entity instanceof Arrow) {
            return ((Arrow)entity).isCritical();
        }
        return false;
    }

    /** @see Arrow#setCritical(boolean) */
    public EEntity setCritical(boolean critical) {
        if (entity instanceof Arrow) {
            ((Arrow)entity).setCritical(critical);
        }
        return this;
    }

    /** @see Arrow#getKnockbackStrength() */
    public Integer getKnockbackStrength() {
        if (entity instanceof Arrow) {
            return ((Arrow)entity).getKnockbackStrength();
        }
        return null;
    }

    /** @see Arrow#setKnockbackStrength(int) */
    public EEntity setKnockbackStrength(int knockback) {
        if (entity instanceof Arrow) {
            ((Arrow)entity).setKnockbackStrength(knockback);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ###################### BOAT ######################
    // ##################################################
    //region Boats

    /** @see Boat#getWorkOnLand() */
    public boolean getWorkOnLand() {
        if (entity instanceof Boat) {
            return ((Boat)entity).getWorkOnLand();
        }
        return false;
    }

    /** @see Boat#setWorkOnLand(boolean) */
    public EEntity setWorkOnLand(boolean workOnLand) {
        if (entity instanceof Boat) {
            ((Boat)entity).setWorkOnLand(workOnLand);
        }
        return this;
    }

    /** @see Boat#getUnoccupiedDeceleration() */
    public Double getUnoccupiedDeceleration() {
        if (entity instanceof Boat) {
            return ((Boat)entity).getUnoccupiedDeceleration();
        }
        return null;
    }

    /** @see Boat#setUnoccupiedDeceleration(double) */
    public EEntity setUnoccupiedDeceleration(double rate) {
        if (entity instanceof Boat) {
            ((Boat)entity).setUnoccupiedDeceleration(rate);
        }
        return this;
    }

    /** @see Boat#getOccupiedDeceleration() */
    public Double getOccupiedDeceleration() {
        if (entity instanceof Boat) {
            return ((Boat)entity).getOccupiedDeceleration();
        }
        return null;
    }

    /** @see Boat#setOccupiedDeceleration(double) */
    public EEntity setOccupiedDeceleration(double rate) {
        if (entity instanceof Boat) {
            ((Boat)entity).setOccupiedDeceleration(rate);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################ COMMAND MINECART ################
    // ##################################################
    //region Command minecarts

    /** @see CommandMinecart#getCommand() */
    public String getCommand() {
        if (entity instanceof CommandMinecart) {
            return ((CommandMinecart)entity).getCommand();
        }
        return "";
    }

    /** @see CommandMinecart#setCommand(String) */
    public EEntity setCommand(String cmd) {
        if (entity instanceof CommandMinecart) {
            ((CommandMinecart)entity).setCommand(cmd);
        }
        return this;
    }

    /** @see CommandMinecart#setName(String) */
    public EEntity setName(String name) {
        if (entity instanceof CommandMinecart) {
            ((CommandMinecart)entity).setName(name);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################### EXPERIENCE ###################
    // ##################################################
    //region Experience orbs

    /** @see ExperienceOrb#getExperience() */
    public Integer getExperience() {
        if (entity instanceof ExperienceOrb) {
            return ((ExperienceOrb)entity).getExperience();
        }
        return null;
    }

    /** @see ExperienceOrb#setExperience(int) */
    public EEntity setExperience(int knockback) {
        if (entity instanceof ExperienceOrb) {
            ((ExperienceOrb)entity).setExperience(knockback);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################## FALLING BLOCK #################
    // ##################################################
    //region Falling blocks

    /** @see FallingBlock#getDropItem() */
    public boolean getDropItem() {
        if (entity instanceof FallingBlock) {
            return ((FallingBlock)entity).getDropItem();
        }
        return false;
    }

    /** @see FallingBlock#setDropItem(boolean) */
    public EEntity setDropItem(boolean drop) {
        if (entity instanceof FallingBlock) {
            ((FallingBlock)entity).setDropItem(drop);
        }
        return this;
    }

    /** @see FallingBlock#canHurtEntities() */
    public boolean canHurtEntities() {
        if (entity instanceof FallingBlock) {
            return ((FallingBlock)entity).canHurtEntities();
        }
        return false;
    }

    /** @see FallingBlock#setHurtEntities(boolean) */
    public EEntity setHurtEntities(boolean hurtEntities) {
        if (entity instanceof FallingBlock) {
            ((FallingBlock)entity).setHurtEntities(hurtEntities);
        }
        return this;
    }

    /** @see FallingBlock#getMaterial() */
    public Material getMaterial() {
        if (entity instanceof FallingBlock) {
            return ((FallingBlock)entity).getMaterial();
        }
        return null;
    }

    /** @see FallingBlock#getBlockData() */
    public Byte getData() {
        if (entity instanceof FallingBlock) {
            return ((FallingBlock)entity).getBlockData();
        }
        return null;
    }
    //endregion


    // ##################################################
    // #################### FIREBALL ####################
    // ##################################################
    //region Fireballs

    /** @see Fireball#getDirection() */
    public Vector getDirection() {
        if (entity instanceof Fireball) {
            return ((Fireball)entity).getDirection();
        }
        return null;
    }

    /** @see Fireball#setDirection(Vector) */
    public EEntity setDirection(Vector direction) {
        if (entity instanceof Fireball) {
            ((Fireball)entity).setDirection(direction);
        }
        return this;
    }

    /** @see Fireball#setDirection(Vector) */
    public EEntity setDirection(float x, float y, float z) {
        if (entity instanceof Fireball) {
            ((Fireball)entity).setDirection(new Vector(x,y,z));
        }
        return this;
    }
    //endregion


    // ##################################################
    // #################### FIREWORK ####################
    // ##################################################
    //region Firework rockets

    /** @see Firework#getFireworkMeta() */
    public FireworkMeta getFireworkMeta() {
        if (entity instanceof FireworkMeta) {
            return ((Firework)entity).getFireworkMeta();
        }
        return null;
    }

    /** @see Firework#setFireworkMeta(FireworkMeta) */
    public EEntity setFireworkMeta(FireworkMeta meta) {
        if (entity instanceof Firework) {
            ((Firework)entity).setFireworkMeta(meta);
        }
        return this;
    }

    /** @see Firework#detonate() */
    public EEntity detonate() {
        if (entity instanceof Firework) {
            ((Firework)entity).detonate();
        }
        return this;
    }
    //endregion


    // ##################################################
    // ###################### ITEM ######################
    // ##################################################
    //region Items

    /** @see Item#getPickupDelay() */
    public Integer getPickupDelay() {
        if (entity instanceof Item) {
            return ((Item)entity).getPickupDelay();
        }
        return null;
    }

    /** @see Item#setPickupDelay(int) */
    public EEntity setPickupDelay(int ticks) {
        if (entity instanceof Item) {
            ((Item)entity).setPickupDelay(ticks);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################### ITEM FRAME ###################
    // ##################################################
    //region Item frames

    /** @see ItemFrame#getRotation() */
    public Rotation getRotation() {
        if (entity instanceof ItemFrame) {
            return ((ItemFrame)entity).getRotation();
        }
        return null;
    }

    /** @see ItemFrame#setRotation(Rotation) */
    public EEntity setRotation(Rotation rotation) {
        if (entity instanceof ItemFrame) {
            ((ItemFrame)entity).setRotation(rotation);
        }
        return this;
    }
    //endregion


    // ##################################################
    // #################### LIGHTNING ###################
    // ##################################################
    //region Lightning strikes

    /** @see LightningStrike#isEffect() */
    public boolean isEffect() {
        if (entity instanceof LightningStrike) {
            return ((LightningStrike)entity).isEffect();
        }
        return false;
    }
    //endregion


    // ##################################################
    // ################### MINECART ###################
    // ##################################################
    //region Minecarts

    /** @see Minecart#isSlowWhenEmpty() */
    public boolean isSlowWhenEmpty() {
        if (entity instanceof Minecart) {
            return ((Minecart)entity).isSlowWhenEmpty();
        }
        return false;
    }

    /** @see Minecart#setSlowWhenEmpty(boolean) */
    public EEntity setSlowWhenEmpty(boolean critical) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setSlowWhenEmpty(critical);
        }
        return this;
    }

    /** @see Minecart#getDamage() */
    public Double getDamage() {
        if (entity instanceof Minecart) {
            return ((Minecart)entity).getDamage();
        }
        return null;
    }

    /** @see Minecart#setDamage(double) */
    public EEntity setDamage(double damage) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setDamage(damage);
        }
        return this;
    }

    /** @see Minecart#getFlyingVelocityMod() */
    public Vector getFlyingVelocityMod() {
        if (entity instanceof Minecart) {
            return ((Minecart)entity).getFlyingVelocityMod();
        }
        return null;
    }

    /** @see Minecart#setFlyingVelocityMod(Vector) */
    public EEntity setFlyingVelocityMod(Vector velocity) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setFlyingVelocityMod(velocity);
        }
        return this;
    }

    /** @see Minecart#setFlyingVelocityMod(Vector) */
    public EEntity setFlyingVelocityMod(float x, float y, float z) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setFlyingVelocityMod(new Vector(x,y,z));
        }
        return this;
    }

    /** @see Minecart#getDerailedVelocityMod() */
    public Vector getDerailedVelocityMod() {
        if (entity instanceof Minecart) {
            return ((Minecart)entity).getDerailedVelocityMod();
        }
        return null;
    }

    /** @see Minecart#setDerailedVelocityMod(Vector) */
    public EEntity setDerailedVelocityMod(Vector velocity) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setDerailedVelocityMod(velocity);
        }
        return this;
    }

    /** @see Minecart#setDerailedVelocityMod(Vector) */
    public EEntity setDerailedVelocityMod(float x, float y, float z) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setDerailedVelocityMod(new Vector(x, y, z));
        }
        return this;
    }

    /** @see Minecart#getDisplayBlock() */
    public MaterialData getDisplayBlock() {
        if (entity instanceof Minecart) {
            return ((Minecart)entity).getDisplayBlock();
        }
        return null;
    }

    /** @see Minecart#setDisplayBlock(MaterialData) */
    public EEntity setDisplayBlock(MaterialData material) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setDisplayBlock(material);
        }
        return this;
    }

    /** @see Minecart#getDisplayBlockOffset() */
    public Integer getDisplayBlockOffset() {
        if (entity instanceof Minecart) {
            return ((Minecart)entity).getDisplayBlockOffset();
        }
        return null;
    }

    /** @see Minecart#setDisplayBlockOffset(int) */
    public EEntity setDisplayBlockOffset(int offset) {
        if (entity instanceof Minecart) {
            ((Minecart)entity).setDisplayBlockOffset(offset);
        }
        return this;
    }
    //endregion


    // ##################################################
    // #################### PAINTING ####################
    // ##################################################
    //region Paintings

    /** @see Painting#getArt() */
    public Art getArt() {
        if (entity instanceof ItemFrame) {
            return ((Painting)entity).getArt();
        }
        return null;
    }

    /** @see Painting#setArt(Art) */
    public EEntity setArt(Art art) {
        if (entity instanceof Painting) {
            ((Painting)entity).setArt(art);
        }
        return this;
    }

    /** @see Painting#setArt(Art, boolean) */
    public EEntity setArt(Art art, boolean force) {
        if (entity instanceof Painting) {
            ((Painting)entity).setArt(art, force);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################# THROWN POTION ##################
    // ##################################################
    //region Thrown potions

    /** @see ThrownPotion#getEffects() */
    public Collection<PotionEffect> getEffects() {
        if (entity instanceof ThrownPotion) {
            return ((ThrownPotion)entity).getEffects();
        }
        return null;
    }
    //endregion


    // ##################################################
    // ################### TNT PRIMED ###################
    // ##################################################
    //region Primted TnT

    /** @see TNTPrimed#getFuseTicks() */
    public Integer getFuseTicks() {
        if (entity instanceof TNTPrimed) {
            return ((TNTPrimed)entity).getFuseTicks();
        }
        return null;
    }

    /** @see TNTPrimed#setFuseTicks(int) */
    public EEntity setFuseTicks(int ticks) {
        if (entity instanceof TNTPrimed) {
            ((TNTPrimed)entity).setFuseTicks(ticks);
        }
        return this;
    }

    /** @see TNTPrimed#getSource() */
    public EEntity getSource() {
        if (entity instanceof TNTPrimed) {
            return new EEntity(((TNTPrimed)entity).getSource());
        }
        return null;
    }
    //endregion


    // ##################################################
    // ################## WITHER SKULL ##################
    // ##################################################
    //region Wither skulls

    /** @see WitherSkull#isCharged() */
    public boolean isCharged() {
        if (entity instanceof WitherSkull) {
            return ((WitherSkull)entity).isCharged();
        }
        return false;
    }

    /** @see WitherSkull#setCharged(boolean) */
    public EEntity setCharged(boolean powered) {
        if (entity instanceof WitherSkull) {
            ((WitherSkull)entity).setCharged(powered);
        }
        return this;
    }
    //endregion
    //endregion



    //region Creature entities. (Bukkit methods)
    // ##################################################
    // ###################### BAT #######################
    // ##################################################
    //region Bats

    /** @see Bat#isAwake() */
    public boolean isAwake() {
        if (entity instanceof Bat) {
            return ((Bat)entity).isAwake();
        }
        return false;
    }

    /** @see Bat#setAwake(boolean) */
    public EEntity setAwake(boolean awake) {
        if (entity instanceof Bat) {
            ((Bat)entity).setAwake(awake);
        }
        return this;
    }
    //endregion


    // ##################################################
    // #################### CREEPER #####################
    // ##################################################
    //region Creepers

    /** @see Creeper#isPowered() */
    public boolean isPowered() {
        if (entity instanceof Creeper) {
            return ((Creeper)entity).isPowered();
        }
        return false;
    }

    /** @see Creeper#setPowered(boolean) */
    public EEntity setPowered(boolean powered) {
        if (entity instanceof Creeper) {
            ((Creeper)entity).setPowered(powered);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################### ENDERMAN #####################
    // ##################################################
    //region Enderman

    /** @see Enderman#getCarriedMaterial() */
    public MaterialData getCarriedMaterial() {
        if (entity instanceof Enderman) {
            return ((Enderman)entity).getCarriedMaterial();
        }
        return null;
    }

    /** @see Enderman#setCarriedMaterial(MaterialData) */
    public EEntity setCarriedMaterial(MaterialData material) {
        if (entity instanceof Enderman) {
            ((Enderman)entity).setCarriedMaterial(material);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################### GUARDIAN #####################
    // ##################################################
    //region Guardians

    /** @see Guardian#isElder() */
    public boolean isElder() {
        if (entity instanceof Guardian) {
            return ((Guardian)entity).isElder();
        }
        return false;
    }

    /** @see Guardian#setElder(boolean) */
    public EEntity setElder(boolean eldar) {
        if (entity instanceof Guardian) {
            ((Guardian)entity).setElder(eldar);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ###################### HORSE #####################
    // ##################################################
    //region Horses

    /** @see Horse#getVariant() */
    public Horse.Variant getVariant() {
        if (entity instanceof Horse) {
            return ((Horse)entity).getVariant();
        }
        return null;
    }

    /** @see Horse#setVariant(Horse.Variant) */
    public EEntity setVariant(Horse.Variant variant) {
        if (entity instanceof Horse) {
            ((Horse)entity).setVariant(variant);
        }
        return this;
    }

    /** @see Horse#getColor() */
    public Horse.Color getColor() {
        if (entity instanceof Horse) {
            return ((Horse)entity).getColor();
        }
        return null;
    }

    /** @see Horse#setColor(Horse.Color) */
    public EEntity setColor(Horse.Color color) {
        if (entity instanceof Horse) {
            ((Horse)entity).setColor(color);
        }
        return this;
    }

    /** @see Horse#getStyle() */
    public Horse.Style getStyle() {
        if (entity instanceof Horse) {
            return ((Horse)entity).getStyle();
        }
        return null;
    }

    /** @see Horse#setStyle(Horse.Style) */
    public EEntity setStyle(Horse.Style color) {
        if (entity instanceof Horse) {
            ((Horse)entity).setStyle(color);
        }
        return this;
    }

    /** @see Horse#isCarryingChest() */
    public boolean isCarryingChest() {
        if (entity instanceof Horse) {
            return ((Horse)entity).isCarryingChest();
        }
        return false;
    }

    /** @see Horse#setCarryingChest(boolean) */
    public EEntity setCarryingChest(boolean chest) {
        if (entity instanceof Horse) {
            ((Horse)entity).setCarryingChest(chest);
        }
        return this;
    }

    /** @see Horse#getDomestication() */
    public Integer getDomestication() {
        if (entity instanceof Horse) {
            return ((Horse)entity).getDomestication();
        }
        return null;
    }

    /** @see Horse#setDomestication(int) */
    public EEntity setDomestication(int knockback) {
        if (entity instanceof Horse) {
            ((Horse)entity).setDomestication(knockback);
        }
        return this;
    }

    /** @see Horse#getMaxDomestication() */
    public Integer getMaxDomestication() {
        if (entity instanceof Horse) {
            return ((Horse)entity).getMaxDomestication();
        }
        return null;
    }

    /** @see Horse#setMaxDomestication(int) */
    public EEntity setMaxDomestication(int knockback) {
        if (entity instanceof Horse) {
            ((Horse)entity).setMaxDomestication(knockback);
        }
        return this;
    }

    /** @see Horse#getJumpStrength() */
    public Double getJumpStrength() {
        if (entity instanceof Horse) {
            return ((Horse)entity).getJumpStrength();
        }
        return null;
    }

    /** @see Horse#setJumpStrength(double) */
    public EEntity setJumpStrength(double strength) {
        if (entity instanceof Horse) {
            ((Horse)entity).setJumpStrength(strength);
        }
        return this;
    }

    /** @see Horse#getInventory() */
    public HorseInventory getInventory() {
        if (entity instanceof Horse) {
            return ((Horse)entity).getInventory();
        }
        return null;
    }

    /**
     * Equips the given item in the horse armor slot.
     * @param item The item to equip.
     * @return EEntity instance.
     */
    public EEntity setArmor(EItem item) {
        if (entity instanceof Horse) {
            ((Horse)entity).getInventory().setArmor(item);
        }
        return this;
    }

    /**
     * Get the equipped item in the armor slot from the horse.
     * @return EItem from the armor slot.
     */
    public EItem getArmor() {
        if (entity instanceof Horse) {
            return new EItem(((Horse)entity).getInventory().getArmor());
        }
        return null;
    }

    /**
     * Equips the given item in the horse saddle slot.
     * @param item The item to equip.
     * @return EEntity instance.
     */
    public EEntity setSaddle(EItem item) {
        if (entity instanceof Horse) {
            ((Horse)entity).getInventory().setSaddle(item);
        }
        return this;
    }

    /**
     * Get the equipped item in the saddle slot from the horse.
     * @return EItem from the saddle slot.
     */
    public EItem getSaddle() {
        if (entity instanceof Horse) {
            return new EItem(((Horse)entity).getInventory().getSaddle());
        }
        return null;
    }
    //endregion


    // ##################################################
    // ################### IRON GOLEM ###################
    // ##################################################
    //region Iron golems

    /** @see IronGolem#isPlayerCreated() */
    public boolean isPlayerCreated() {
        if (entity instanceof IronGolem) {
            return ((IronGolem)entity).isPlayerCreated();
        }
        return false;
    }

    /** @see IronGolem#setPlayerCreated(boolean) */
    public EEntity setPlayerCreated(boolean eldar) {
        if (entity instanceof IronGolem) {
            ((IronGolem)entity).setPlayerCreated(eldar);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ##################### OCELOT #####################
    // ##################################################
    //region Ocelots

    /** @see Ocelot#getCatType() */
    public Ocelot.Type getCatType() {
        if (entity instanceof Ocelot) {
            return ((Ocelot)entity).getCatType();
        }
        return null;
    }

    /** @see Ocelot#setCatType(Ocelot.Type) */
    public EEntity setCatType(Ocelot.Type type) {
        if (entity instanceof Ocelot) {
            ((Ocelot)entity).setCatType(type);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ###################### PIG #######################
    // ##################################################
    //region Pigs

    /** @see Pig#hasSaddle() */
    public boolean hasSaddle() {
        if (entity instanceof Pig) {
            return ((Pig)entity).hasSaddle();
        }
        return false;
    }

    /** @see Pig#setSaddle(boolean) */
    public EEntity setSaddle(boolean saddle) {
        if (entity instanceof Pig) {
            ((Pig)entity).setSaddle(saddle);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ################### PIG ZOMBIE ###################
    // ##################################################
    //region Pig zombies

    /** @see PigZombie#getAnger() */
    public Integer getAnger() {
        if (entity instanceof PigZombie) {
            return ((PigZombie)entity).getAnger();
        }
        return null;
    }

    /** @see PigZombie#setAnger(int) */
    public EEntity setAnger(int anger) {
        if (entity instanceof PigZombie) {
            ((PigZombie)entity).setAnger(anger);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ##################### RABBIT #####################
    // ##################################################
    //region Rabbits

    /** @see Rabbit#getRabbitType() */
    public Rabbit.Type getRabbitType() {
        if (entity instanceof Rabbit) {
            return ((Rabbit)entity).getRabbitType();
        }
        return null;
    }

    /** @see Rabbit#setRabbitType(Rabbit.Type) */
    public EEntity setRabbitType(Rabbit.Type type) {
        if (entity instanceof Rabbit) {
            ((Rabbit)entity).setRabbitType(type);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ##################### SHEEP ######################
    // ##################################################
    //region Sheeps

    /** @see Sheep#isSheared() */
    public boolean isSheared() {
        if (entity instanceof Sheep) {
            return ((Sheep)entity).isSheared();
        }
        return false;
    }

    /** @see Sheep#setSheared(boolean) */
    public EEntity setSheared(boolean saddle) {
        if (entity instanceof Sheep) {
            ((Sheep)entity).setSheared(saddle);
        }
        return this;
    }
    //endregion


    // ##################################################
    // #################### SKELETON ####################
    // ##################################################
    //region Skeletons

    /** @see Skeleton#getSkeletonType() */
    public Skeleton.SkeletonType getSkeletonType() {
        if (entity instanceof Skeleton) {
            return ((Skeleton)entity).getSkeletonType();
        }
        return null;
    }

    /** @see Skeleton#setSkeletonType(Skeleton.SkeletonType) */
    public EEntity setSkeletonType(Skeleton.SkeletonType type) {
        if (entity instanceof Skeleton) {
            ((Skeleton)entity).setSkeletonType(type);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ##################### SLIME ######################
    // ##################################################
    //region Slimes

    /** @see Slime#getSize() */
    public Integer getSize() {
        if (entity instanceof Slime) {
            return ((Slime)entity).getSize();
        }
        return null;
    }

    /** @see Slime#setSize(int) */
    public EEntity setSize(int anger) {
        if (entity instanceof Slime) {
            ((Slime)entity).setSize(anger);
        }
        return this;
    }
    //endregion


    // ##################################################
    // #################### VILLAGER ####################
    // ##################################################
    //region Villagers

    /** @see Villager#getProfession() */
    public Villager.Profession getProfession() {
        if (entity instanceof Villager) {
            return ((Villager)entity).getProfession();
        }
        return null;
    }

    /** @see Villager#setProfession(Villager.Profession) */
    public EEntity setProfession(Villager.Profession type) {
        if (entity instanceof Villager) {
            ((Villager)entity).setProfession(type);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ###################### WOLF ######################
    // ##################################################
    //region Wolves

    /** @see Wolf#getCollarColor() */
    public DyeColor getCollarColor() {
        if (entity instanceof Wolf) {
            return ((Wolf)entity).getCollarColor();
        }
        return null;
    }

    /** @see Wolf#setCollarColor(DyeColor) */
    public EEntity setCollarColor(DyeColor color) {
        if (entity instanceof Wolf) {
            ((Wolf)entity).setCollarColor(color);
        }
        return this;
    }
    //endregion


    // ##################################################
    // ##################### ZOMBIE #####################
    // ##################################################
    //region Zombies

    /** @see Zombie#isVillager() */
    public boolean isVillager() {
        if (entity instanceof Zombie) {
            return ((Zombie)entity).isVillager();
        }
        return false;
    }

    /** @see Zombie#setVillager(boolean) */
    public EEntity setVillager(boolean villager) {
        if (entity instanceof Zombie) {
            ((Zombie)entity).setVillager(villager);
        }
        return this;
    }
    //endregion
    //endregion



    //region Mixed entity methods. (Methods that are used for multiple entities like setItemInHand)

    /**
     * @see PigZombie#isAngry()
     * @see Wolf#isAngry()
     */
    public boolean isAngry() {
        if (entity instanceof PigZombie) {
            return ((PigZombie)entity).isAngry();
        } else if (entity instanceof Wolf) {
            return ((Wolf)entity).isAngry();
        }
        return false;
    }

    /**
     * @see PigZombie#setAngry(boolean)
     * @see Wolf#setAngry(boolean)
     */
    public EEntity setAngry(boolean angry) {
        if (entity instanceof PigZombie) {
            ((PigZombie)entity).setAngry(angry);
        } else if (entity instanceof Wolf) {
            ((Wolf)entity).setAngry(angry);
        }
        return this;
    }

    /**
     * @see Ocelot#isSitting()
     * @see Wolf#isSitting()
     */
    public boolean isSitting() {
        if (entity instanceof Ocelot) {
            return ((Ocelot)entity).isSitting();
        } else if (entity instanceof Wolf) {
            return ((Wolf)entity).isSitting();
        }
        return false;
    }

    /**
     * @see Ocelot#setSitting(boolean)
     * @see Wolf#setSitting(boolean)
     */
    public EEntity setSitting(boolean sitting) {
        if (entity instanceof Ocelot) {
            ((Ocelot)entity).setSitting(sitting);
        } else if (entity instanceof Wolf) {
            ((Wolf)entity).setSitting(sitting);
        }
        return this;
    }

    /**
     * @see Boat#getMaxSpeed()
     * @see Minecart#getMaxSpeed()
     */
    public Double getMaxSpeed() {
        if (entity instanceof Boat) {
            return ((Boat)entity).getMaxSpeed();
        } else if (entity instanceof Minecart) {
            return ((Boat)entity).getMaxSpeed();
        }
        return null;
    }

    /**
     * @see Boat#setMaxSpeed(double)
     * @see Minecart#setMaxSpeed(double)
     */
    public EEntity setMaxSpeed(double rate) {
        if (entity instanceof Boat) {
            ((Boat)entity).setMaxSpeed(rate);
        } else if (entity instanceof Minecart) {
            ((Boat)entity).setMaxSpeed(rate);
        }
        return this;
    }

    /** @see ThrownPotion#getItem() */
    public EItem getItem() {
        if (entity instanceof ThrownPotion) {
            return new EItem(((ThrownPotion)entity).getItem());
        }
        return null;
    }

    /**
     * @see Item#setItemStack(ItemStack)
     * @see ThrownPotion#setItem(ItemStack)
     * @see ItemFrame#setItem(ItemStack)
     */
    public EEntity setItem(ItemStack item) {
        if (entity instanceof Item) {
            ((Item)entity).setItemStack(item);
        } else if (entity instanceof ThrownPotion) {
            ((ThrownPotion)entity).setItem(item);
        } else if (entity instanceof ItemFrame) {
            ((ItemFrame)entity).setItem(item);
        }
        return this;
    }

    /**
     * @see Item#setItemStack(ItemStack)
     * @see ThrownPotion#setItem(ItemStack)
     * @see ItemFrame#setItem(ItemStack)
     */
    public EEntity setItem(EItem item) {
        if (entity instanceof Item) {
            ((Item)entity).setItemStack(item);
        } else if (entity instanceof ThrownPotion) {
            ((ThrownPotion)entity).setItem(item);
        } else if (entity instanceof ItemFrame) {
            ((ItemFrame)entity).setItem(item);
        }
        return this;
    }


    //TODO: Equipment etc.

    //TODO: Set baby
    //endregion



    //region Custom methods. (Mostly Util)
    //TODO: Set visibility (Packets)
    //TODO: Set entity tags (Noai, Invulnerable, Silent etc) (NMS)
    //TODO: Pathfinding goals etc (NMS)
    //TODO: Util methods.
    //endregion

}
