package com.herocraftonline.dev.heroes.command.skill;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class ActiveSkill extends Skill {

	protected int manaCost;
	
	public ActiveSkill(Heroes plugin) {
		super(plugin);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Hero hero = plugin.getHeroManager().getHero(player);
			if (hero == null) {
				plugin.getMessager().send(player, "You are not a hero.");
				return;
			}
			HeroClass heroClass = hero.getPlayerClass();
			Integer reqLevel = heroClass.getSkillLevelRequirements().get(this.name);
			if (reqLevel == null) {
				plugin.getMessager().send(player, "$1s cannot use $2.", heroClass.getName(), this.name);
				return;
			}
			if (!meetsLevelRequirement(hero, reqLevel.intValue())) {
				plugin.getMessager().send(player, "You are not high enough level to use $2.", this.name);
				return;
			}
			Integer manaCost = heroClass.getSkillManaCosts().get(this.name);
			if (manaCost.intValue() < hero.getMana()) {
				plugin.getMessager().send(player, "You don't have enough mana to use $1.", this.name);
				return;
			}
			use(hero, args);
		}
	}
	
	public abstract void use(Hero hero, String[] args);
	
	public int getManaCost() {
		return manaCost;
	}
	
}
