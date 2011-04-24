package com.herocraftonline.dev.heroes.command.skill.skills;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillInvuln extends ActiveSkill {

    public SkillInvuln(Heroes plugin) {
        super(plugin);
        name = "Invuln";
        description = "Skill - Invuln";
        usage = "/invuln";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("invuln");
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        hero.getEffects().put(name, System.currentTimeMillis() + 10000.0);
        return true;
    }

}
