package com.herocraftonline.dev.heroes.command.skill;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.SkillSettings;
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
			if (!heroClass.hasSkill(name)) {
				plugin.getMessager().send(player, "$1s cannot use $2.", heroClass.getName(), name);
				return;
			}
			SkillSettings settings = heroClass.getSkillSettings(name);
			if (!meetsLevelRequirement(hero, settings.LevelRequirement)) {
				plugin.getMessager().send(player, "You are not high enough level to use $2.", name);
				return;
			}
			if (settings.ManaCost < hero.getMana()) {
				plugin.getMessager().send(player, "You don't have enough mana to use $1.", name);
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
