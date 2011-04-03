package com.herocraftonline.dev.heroes.command.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBladegrasp extends BaseCommand {

    // TODO: Register this command in Heroes
    public SkillBladegrasp(Heroes plugin) {
        super(plugin);
        name = "Bladegrasp";
        description = "Skill - Bladegrasp";
        usage = "/bladegrasp";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("bladegrasp");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Hero hero = plugin.getHeroManager().getHero((Player) sender);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

            // TODO: Check for CD time left, if 0 execute.
            if (!(heroClass.getSpells().contains(Spells.BLADEGRASP))) {
                plugin.getMessaging().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }

            if (!(hero.getEffects().containsKey("bladegrasp"))) {
                hero.getEffects().put("bladegrasp", System.currentTimeMillis());
            } else {
                if (hero.getEffects().get("bladegrasp") > 60000) {
                    hero.getEffects().put("bladegrasp", System.currentTimeMillis());
                }
            }
        }
    }
}
