package com.herocraftonline.dev.heroes.command.skill.skills;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillTame extends ActiveSkill {

    // TODO: Register this command in Heroes
    public SkillTame(Heroes plugin) {
        super(plugin);
        name = "Tame";
        description = "Skill - Tame";
        usage = "/tame";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("tame");
    }

    @Override
    public void use(Hero hero, String[] args) {

    }

}
