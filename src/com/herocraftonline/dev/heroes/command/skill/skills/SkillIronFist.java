package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.PassiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillIronFist extends PassiveSkill {

	public SkillIronFist(Heroes plugin) {
		super(plugin);
		name = "Iron Fist";
		description = "Skill - Iron Fist";
		usage = "/ironfist";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("ironfist");

		registerEvent(Type.ENTITY_DAMAGE, new SkillPlayerListener(), Priority.Normal);
	}

	public class SkillPlayerListener extends EntityListener {

		public void onPlayerMove(EntityDamageEvent event) {
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
				if (subEvent.getDamager() instanceof Player) {
					Player player = (Player) subEvent.getDamager();
					Hero hero = plugin.getHeroManager().getHero(player);
					if (hero.getEffects().containsKey(name)) {
						if (player.getItemInHand().getType() == Material.AIR) {
							event.setDamage(event.getDamage() * 2);
						}
					}
				}
			}
		}

	}
}