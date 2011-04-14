package com.herocraftonline.dev.heroes.command.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillSummon extends Skill {

    // TODO: Register this command in Heroes
    public SkillSummon(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Summon";
        usage = "/summon <monster>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("summon");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

            // TODO: Check for CD time left, if 0 execute.
            if (!(heroClass.getSpells().contains(Spells.SUMMON))) {
                plugin.getMessager().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }
            
            CreatureType creatureType = CreatureType.valueOf(args[0]);
            if (creatureType != null) {
                if (hero.getSummons().size() > heroClass.getSummonMax()) {
                    Entity spawnedEntity = player.getWorld().spawnCreature(player.getLocation(), creatureType);
                    hero.getSummons().put(spawnedEntity, creatureType);
                }
            }
        }
    }

    @Override
    public void use(Player user, String[] args) {
        // TODO Auto-generated method stub
        
    }
}
