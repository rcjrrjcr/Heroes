package com.herocraftonline.dev.heroes.command.skill;

import org.bukkit.command.CommandSender;
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

public abstract class PassiveSkill extends Skill {

    public PassiveSkill(Heroes plugin) {
        super(plugin);

        registerEvent(Type.CUSTOM_EVENT, new SkillCustomEventListener(), Priority.Monitor);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {}

    private void apply(Hero hero) {
        hero.getEffects().put(name.toLowerCase(), Double.POSITIVE_INFINITY);
    }

    public void unapply(Hero hero) {
        hero.getEffects().remove(name.toLowerCase());
    }

    public class SkillCustomEventListener extends CustomEventListener {

        @Override
        public void onCustomEvent(Event event) {
            Hero hero = null;
            if (event instanceof LevelEvent) {
                LevelEvent subEvent = (LevelEvent) event;
                if (subEvent.isCancelled()) {
                    return;
                }
                hero = subEvent.getHero();
            } else if (event instanceof ClassChangeEvent) {
                ClassChangeEvent subEvent = (ClassChangeEvent) event;
                if (subEvent.isCancelled()) {
                    return;
                }
                hero = subEvent.getHero();
            }
            if (hero != null) {
                HeroClass heroClass = hero.getHeroClass();
                SkillSettings settings = heroClass.getSkillSettings(name);
                if (settings == null || !meetsLevelRequirement(hero, settings.LevelRequirement)) {
                    unapply(hero);
                } else {
                    apply(hero);
                }
            }
        }

    }

}
