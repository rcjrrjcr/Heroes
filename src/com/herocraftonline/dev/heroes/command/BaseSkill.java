package com.herocraftonline.dev.heroes.command;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;

public abstract class BaseSkill extends BaseCommand {

    protected int cooldown;
    protected boolean nocommand;

    public BaseSkill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Returns the first LivingEntity in the line of sight of a Player.
     * 
     * @param player the player being checked
     * @param maxDistance the maximum distance to search for a target
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
