package com.herocraftonline.dev.heroes.abilities.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillSummon extends BaseCommand {

    // TODO: Register this command in Heroes
    public SkillSummon(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Summon";
        usage = "/summon <monster>";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("summon");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Hero hero = plugin.getHeroManager().getHero((Player) sender);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());
            // TODO: Check for CD time left, if 0 execute.
            if (!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("Summon"))) {
                plugin.getMessaging().send(sender, "Sorry, $1, that ability isn't for your class!", ((Player) sender).getName());
                return;
            }
            
            if(CreatureType.valueOf(args[0]) != null){
                if(hero.getSummons().size() > heroClass.getSummonMax() && heroClass.getSummonable() == true){
                   Entity spawnedEntity = plugin.getServer().getWorld(((Player) sender).getWorld().toString()).spawnCreature(((Player) sender).getLocation(), CreatureType.valueOf(args[0]));
                   hero.getSummons().put(spawnedEntity, CreatureType.valueOf(args[0]));
                }
            }
        }
    }

}
