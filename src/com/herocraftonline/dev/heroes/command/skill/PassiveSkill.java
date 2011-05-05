package com.herocraftonline.dev.heroes.command.skill;

import org.bukkit.command.CommandSender;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.api.ClassChangeEvent;
import com.herocraftonline.dev.heroes.api.LevelEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.SkillSettings;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class PassiveSkill extends Skill {

    protected String applyText = null;
    protected String unapplyText = null;

    public PassiveSkill(Heroes plugin) {
        super(plugin);

        registerEvent(Type.CUSTOM_EVENT, new SkillCustomEventListener(), Priority.Monitor);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {}

    protected void apply(Hero hero) {
        hero.getEffects().putEffect(name.toLowerCase(), Double.POSITIVE_INFINITY);
        if (applyText != null) {
            notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), applyText, hero.getPlayer().getName(), name);
        }
    }

    protected void unapply(Hero hero) {
        hero.getEffects().removeEffect(name.toLowerCase());
        if (unapplyText != null) {
            notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), unapplyText, hero.getPlayer().getName(), name);
        }
    }

    @Override
    public void init() {
        applyText = config.getString("apply-text", "%hero% gained %skill%!");
        if (applyText != null) {
            applyText = applyText.replace("%hero%", "$1").replace("%skill%", "$2");
        }
        unapplyText = config.getString("unapply-text", "%hero% lost %skill%!");
        if (unapplyText != null) {
            unapplyText = unapplyText.replace("%hero%", "$1").replace("%skill%", "$2");
        }
    }
    
    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("apply-text", "%hero% gained %skill%!");
        node.setProperty("unapply-text", "%hero% lost %skill%!");
        return node;
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
