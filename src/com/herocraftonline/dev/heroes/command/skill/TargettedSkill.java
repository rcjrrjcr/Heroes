package com.herocraftonline.dev.heroes.command.skill;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public abstract class TargettedSkill extends ActiveSkill {

    protected int maxDistance;

    public TargettedSkill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        Player player = hero.getPlayer();
        LivingEntity target = null;
        if (args.length > 0) {
            target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                Messaging.send(player, "Target not found.");
                return false;
            }
            if (target.getLocation().toVector().distance(player.getLocation().toVector()) > maxDistance) {
                Messaging.send(player, "Target is too far away.");
                return false;
            }
            if (!inLineOfSight(player, (Player) target)) {
                Messaging.send(player, "Sorry, target is not in your line of sight!");
                return false;
            }
        }
        if (target == null) {
            target = getPlayerTarget(player, maxDistance);
        } else {
            if (args.length > 1) {
                args = Arrays.copyOfRange(args, 1, args.length);
            }
        }
        if (target == null) {
            target = player;
        }
        return use(hero, target, args);
    }

    public abstract boolean use(Hero hero, LivingEntity target, String[] args);

    public int getMaxDistance() {
        return maxDistance;
    }

    /**
     * Returns the first LivingEntity in the line of sight of a Player.
     * 
     * @param player
     *            the player being checked
     * @param maxDistance
     *            the maximum distance to search for a target
     * @return the player's target or null if no target is found
     */
    public static LivingEntity getPlayerTarget(Player player, int maxDistance) {
        HashSet<Byte> transparent = new HashSet<Byte>();
        transparent.add((byte)Material.AIR.getId());
        transparent.add((byte)Material.WATER.getId());
        List<Block> lineOfSight = player.getLineOfSight(transparent, maxDistance);
        double halfDistance = maxDistance / 2.0;
        List<Entity> nearbyEntities = player.getNearbyEntities(halfDistance, halfDistance, halfDistance);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity) {
                Location entityLocation = entity.getLocation();
                int entityX = entityLocation.getBlockX();
                int entityZ = entityLocation.getBlockZ();
                for (Block block : lineOfSight) {
                    Location blockLocation = block.getLocation();
                    if (entityX == blockLocation.getBlockX() && entityZ == blockLocation.getBlockZ()) {
                        return (LivingEntity) entity;
                    }
                }
            }
        }
        return null;
    }

    public static boolean inLineOfSight(Player a, Player b) {
        if (a == b) {
            return true;
        }

        Location aLoc = a.getEyeLocation();
        Location bLoc = b.getEyeLocation();
        int distance = Location.locToBlock(aLoc.toVector().distance(bLoc.toVector())) - 1;
        if (distance > 120) {
            return false;
        }
        Vector ab = new Vector(bLoc.getX() - aLoc.getX(), bLoc.getY() - aLoc.getY(), bLoc.getZ() - aLoc.getZ());
        Iterator<Block> iterator = new BlockIterator(a.getWorld(), aLoc.toVector(), ab, 0, distance + 1);
        while (iterator.hasNext()) {
            Block block = iterator.next();
            Material type = block.getType();
            if (type != Material.AIR && type != Material.WATER) {
                return false;
            }
        }
        return true;
    }

}
