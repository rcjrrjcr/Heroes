package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBandages extends TargettedSkill {

	public SkillBandages(Heroes plugin) {
		super(plugin);
		name = "Bandage";
		description = "Skill - Bandage";
		usage = "/cast bandage";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("cast bandage");
	}

	@Override
	public boolean use(Hero hero, LivingEntity target, String[] args) {
		Player player = hero.getPlayer();
		if (!player.getItemInHand().equals(Material.PAPER)) {
			return false;
		}

		target.setHealth(target.getHealth() + 4);
		return true;
	}

}
