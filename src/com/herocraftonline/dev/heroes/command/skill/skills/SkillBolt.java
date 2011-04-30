package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBolt extends TargettedSkill {

    public SkillBolt(Heroes plugin) {
        super(plugin);
        name = "Bolt";
        description = "Skill - Bolt";
        usage = "/skill bolt <target>";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("skill bolt");
        maxDistance = 20;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        List<Entity> entityList = target.getNearbyEntities(10,10,10);
        for(Entity n : entityList){
            target.getWorld().strikeLightning(n.getLocation());
        }
        target.getWorld().strikeLightning(target.getLocation());
        return false;
    }
}
