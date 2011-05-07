package com.herocraftonline.dev.heroes.command.skill.skills;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillTame extends ActiveSkill {

    public SkillTame(Heroes plugin) {
        super(plugin);
        name = "Tame";
        description = "Skill - Tame";
        usage = "/skill tame";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("skill tame");
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        if (useText != null) {
            notifyNearbyPlayers(hero.getPlayer().getLocation(), useText, hero.getPlayer().getName(), name);
        }
        return true;
    }

}
