package com.herocraftonline.dev.heroes.command.skill.skills;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBlackjack extends ActiveSkill {

	public SkillBlackjack(Heroes plugin) {
		super(plugin);
		name = "Blackjack";
		description = "Skill - blackjack";
		usage = "/blackjack";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("blackjack");
	}

	@Override
	public boolean use(Hero hero, String[] args) {
		hero.getEffects().put(name, System.currentTimeMillis() + 60000.0);
		return true;
	}
}
