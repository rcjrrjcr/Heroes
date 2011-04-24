package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillSyphon extends TargettedSkill {

	public SkillSyphon(Heroes plugin) {
		super(plugin);
		name = "Syphon";
		description = "Skill - Syphon";
		usage = "/syphon";
		minArgs = 1;
		maxArgs = 2;
		identifiers.add("syphon");
	}

	@Override
	public boolean use(Hero hero, LivingEntity target, String[] args) {
		Player player = (Player) hero.getPlayer();

		if (args.length == 1) {
			target.setHealth(target.getHealth() + 4);
			player.setHealth(player.getHealth() - 4);
		} else {
			try {
				int health = Integer.parseInt(args[1]);
				target.setHealth(target.getHealth() + health);
				player.setHealth(player.getHealth() - health);
			} catch (NumberFormatException e) {
				player.sendMessage("Sorry, that's an incorrect health value!");
				return false;
			}
		}
		return true;
	}

}
