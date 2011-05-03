package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillLayhands extends TargettedSkill {

    public SkillLayhands(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Layhands";
        usage = "/skill layhands <player>";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("skill layhands");
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
        target.setHealth(20);
        String targetName = (target instanceof Player) ? ((Player) target).getName() : target.getClass().getSimpleName().substring(5);
        notifyNearbyPlayers(player.getLocation().toVector(), "$1 used $2 on $3!", player.getName(), name, target == player ? "himself" : targetName);
        return true;
    }
}
