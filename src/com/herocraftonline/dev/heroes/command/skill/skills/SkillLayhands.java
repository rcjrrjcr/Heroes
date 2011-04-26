package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillLayhands extends TargettedSkill {

    public SkillLayhands(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Layhands";
        usage = "/skill layhands <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("skill layhands");
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        target.setHealth(20);
        return true;
    }
}
