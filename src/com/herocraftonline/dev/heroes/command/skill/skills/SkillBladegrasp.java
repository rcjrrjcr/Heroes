package com.herocraftonline.dev.heroes.command.skill.skills;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBladegrasp extends ActiveSkill {

    public SkillBladegrasp(Heroes plugin) {
        super(plugin);
        name = "Bladegrasp";
        description = "Skill - Bladegrasp";
        usage = "/bladegrasp";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("bladegrasp");
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        hero.getEffects().put(name, System.currentTimeMillis() + 60000.0);
        return true;
    }
}
