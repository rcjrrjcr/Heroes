package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillRevive extends TargettedSkill {
    public HashMap<Player, Location> deaths = new HashMap<Player, Location>();

    public SkillRevive(Heroes plugin) {
        super(plugin);
        name = "Revive";
        description = "Skill - Revive";
        usage = "/skill revive";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("skill revive");
    }
    
    @Override
    public void init() {
        maxDistance = config.getInt("max-distance", 15);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("max-distance", 15);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        if (!(target instanceof Player)) {
            player.sendMessage("You must target a player.");
            return false;
        }

        Player targetPlayer = (Player) target;
        if (deaths.containsKey(targetPlayer)) {
            Location loc = deaths.get(targetPlayer);
            double dx = player.getLocation().getX() - loc.getX();
            double dz = player.getLocation().getZ() - loc.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance < 50) {
                if (targetPlayer.isDead()) {
                    player.sendMessage("That player is still dead");
                } else {
                    targetPlayer.teleport(loc);
                }
            }
        }
        return true;
    }
}
