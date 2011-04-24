package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.PassiveSkill;
import com.herocraftonline.dev.heroes.command.skill.Skill;

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
					if (plugin.getHeroManager().getHero(player).getPlayerClass().getSkills().contains(getName())) {
						if (player.getItemInHand().getType() == Material.AIR) {
							event.setDamage(event.getDamage() * 2);
						}
					}
				}
			}
		}

	}
}