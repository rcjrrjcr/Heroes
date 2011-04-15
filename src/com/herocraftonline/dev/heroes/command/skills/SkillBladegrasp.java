package com.herocraftonline.dev.heroes.command.skills;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBladegrasp extends Skill {

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

            
            if (!(heroClass.getSpells().contains(Spells.BLADEGRASP))) {
                plugin.getMessager().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }
            
            HashMap<String, Long> cooldowns = hero.getCooldowns();
            if (cooldowns.containsKey(getName())) {
                if (cooldowns.get(getName()) - System.currentTimeMillis() >= 3000) {
                    cooldowns.put(getName(), System.currentTimeMillis());
                } else {
                    plugin.getMessager().send(sender, "Sorry, that skill is still on cooldown!");
                    return;
                }
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

    @Override
    public void use(Player user, String[] args) {
        // TODO Auto-generated method stub
        
    }
}
