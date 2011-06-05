package com.scswc.shadrxninga.skillbarrage;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;


public class SkillBarrage extends ActiveSkill{

	public SkillBarrage(Heroes plugin) {
		super(plugin);
		name = "Barrage";
		description = "Fire a Barrage of Arrows from you";
		usage = "/skill barrgae";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("skill barrage");
	}
	
	public boolean use(Hero hero, String[] args){
		Player player = hero.getPlayer();
				double diff = (2 * Math.PI) / 24.0;
	            for (double a = 0; a < 2 * Math.PI; a += diff) {
	                Vector vel = new Vector(Math.cos(a), 0, Math.sin(a));
	                player.shootArrow().setVelocity(vel);
	            }
				return false;

}
}
