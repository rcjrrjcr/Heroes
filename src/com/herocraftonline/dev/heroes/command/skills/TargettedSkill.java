package com.herocraftonline.dev.heroes.command.skills;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;

public abstract class TargettedSkill extends Skill {

    protected int maxDistance;

    public TargettedSkill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public void use(Player player, String[] args) {
        LivingEntity target = null;
        if (args.length > 0) {
            target = plugin.getServer().getPlayer(args[0]);
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
        use(player, target, args);
    }

    public abstract void use(Player player, LivingEntity target, String[] args);

    public int getMaxDistance() {
        return maxDistance;
    }

    /**
     * Returns the first LivingEntity in the line of sight of a Player.
     * 
     * @param player
     *        the player being checked
     * @param maxDistance
     *        the maximum distance to search for a target
     * @return the player's target or null if no target is found
     */
    public static LivingEntity getPlayerTarget(Player player, int maxDistance) {
        List<Block> lineOfSight = player.getLineOfSight(null, maxDistance);
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

}
