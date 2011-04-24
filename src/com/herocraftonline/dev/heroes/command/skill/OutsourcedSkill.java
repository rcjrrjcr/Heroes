package com.herocraftonline.dev.heroes.command.skill;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.api.ClassChangeEvent;
import com.herocraftonline.dev.heroes.api.LevelEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.SkillSettings;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class OutsourcedSkill extends Skill {

    protected String permission;

    public OutsourcedSkill(Heroes plugin) {
        super(plugin);

        registerEvent(Type.CUSTOM_EVENT, new SkillCustomListener(), Priority.Normal);
    }

    public class SkillCustomListener extends CustomEventListener {

        @Override
        public void onCustomEvent(Event event) {
            if (event instanceof ClassChangeEvent) {
                tryLearningSkill(((ClassChangeEvent) event).getPlayer());
            } else if (event instanceof LevelEvent) {
                tryLearningSkill(((LevelEvent) event).getPlayer());
            }
        }

        private void tryLearningSkill(Player player) {
            if (Heroes.Permissions == null) {
                plugin.log(Level.WARNING, "Attempt to use an OutsourcedSkill (" + name + ") failed - Permissions not found.");
                return;
            }

            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = hero.getPlayerClass();

            String world = player.getWorld().getName();
            SkillSettings settings = heroClass.getSkillSettings(name);
            if (settings != null && meetsLevelRequirement(hero, settings.LevelRequirement)) {
                Heroes.Permissions.addUserPermission(world, player.getName(), permission);
            } else {
                Heroes.Permissions.removeUserPermission(world, player.getName(), permission);
            }
            Heroes.Permissions.save(world);
        }

    }

}
