package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class SkillTrack extends TargettedSkill {

    public SkillTrack(Heroes plugin) {
        super(plugin);
        name = "Track";
        description = "Skill - Track";
        usage = "/skill track <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("skill track");
    }
    
    @Override
    public void init() {
        maxDistance = config.getInt("max-distance", 10000);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("max-distance", 10000);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Location location = target.getLocation();
        Player player = hero.getPlayer();
        Messaging.send(player, "Location: $1", location.toString());
        player.setCompassTarget(location);
        return true;
    }
}
