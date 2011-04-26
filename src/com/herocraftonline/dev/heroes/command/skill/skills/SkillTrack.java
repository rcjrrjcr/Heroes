package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

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
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Location location = target.getLocation();
        Player player = hero.getPlayer();
        plugin.getMessager().send(player, "Location: $1", location.toString());
        player.setCompassTarget(location);
        return true;
    }
}
