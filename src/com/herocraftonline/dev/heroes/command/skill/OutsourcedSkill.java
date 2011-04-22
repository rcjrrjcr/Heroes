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
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public abstract class OutsourcedSkill extends Skill {

    protected String permission;

    public OutsourcedSkill(Heroes plugin) {
        super(plugin);

        plugin.getServer().getPluginManager().registerEvent(Type.CUSTOM_EVENT, new SkillCustomListener(), Priority.Normal, plugin);
    }

    @Override
    public final void use(Player player, String[] args) {}

    public class SkillCustomListener extends CustomEventListener {

        @Override
        public void onCustomEvent(Event event) {
            if (event instanceof ClassChangeEvent) {
                ClassChangeEvent subEvent = (ClassChangeEvent) event;
                tryLearningSkill(subEvent.getPlayer());
            } else if (event instanceof LevelEvent) {
                LevelEvent subEvent = (LevelEvent) event;
                tryLearningSkill(subEvent.getPlayer());
            }
        }

        private void tryLearningSkill(Player player) {
            if (Heroes.Permissions == null) {
                plugin.log(Level.WARNING, "Attempt to use an OutsourcedSkill (" + name + ") failed - Permissions not found.");
                return;
            }

            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = hero.getPlayerClass();
            Properties p = plugin.getConfigManager().getProperties();

            boolean learn = false;
            if (heroClass.getSkills().contains(name)) {
                if (!(p.getLevel(hero.getExperience()) > Integer.parseInt(p.skillInfo.get(getName() + "level")))) {
                    learn = true;
                }
            }

            String world = player.getWorld().getName();
            if (learn) {
                Heroes.Permissions.addUserPermission(world, player.getName(), permission);
            } else {
                Heroes.Permissions.removeUserPermission(world, player.getName(), permission);
            }
            Heroes.Permissions.save(world);
        }

    }

}
