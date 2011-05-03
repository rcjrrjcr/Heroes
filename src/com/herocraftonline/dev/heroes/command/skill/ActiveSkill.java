package com.herocraftonline.dev.heroes.command.skill;

import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.SkillSettings;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public abstract class ActiveSkill extends Skill {

    public ActiveSkill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            if (hero == null) {
                Messaging.send(player, "You are not a hero.");
                return;
            }
            HeroClass heroClass = hero.getHeroClass();
            if (!heroClass.hasSkill(name) && !heroClass.hasSkill("*")) {
                Messaging.send(player, "$1s cannot use $2.", heroClass.getName(), name);
                return;
            }
            SkillSettings settings = heroClass.getSkillSettings(name);
            if (!meetsLevelRequirement(hero, settings.LevelRequirement)) {
                Messaging.send(player, "You must be level $1 to use $2.", String.valueOf(settings.LevelRequirement), name);
                return;
            }
            if (settings.ManaCost > hero.getMana()) {
                Messaging.send(player, "Not enough mana!");
                return;
            }
            Map<String, Long> cooldowns = hero.getCooldowns();
            long time = System.currentTimeMillis();
            int cooldown = settings.Cooldown;
            if (cooldown > 0) {
                Long timeUsed = cooldowns.get(name);
                if (timeUsed != null) {
                    if (time < (timeUsed + cooldown)) {
                        long remaining = (timeUsed + cooldown) - time;
                        Messaging.send(hero.getPlayer(), "Sorry, $1 still has $2 seconds left on cooldown!", name, Long.toString(remaining / 1000));
                        return;
                    }
                }
            }
            if (use(hero, args)) {
                if (cooldown > 0) {
                    cooldowns.put(name, time);
                }
                hero.setMana(hero.getMana() - settings.ManaCost);
                if (hero.isVerbose()) {
                    Messaging.send(hero.getPlayer(), Messaging.createManaBar(hero.getMana()));
                }
            }
        }
    }

    public abstract boolean use(Hero hero, String[] args);

}
