package com.herocraftonline.dev.heroes.command.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillJump extends BaseCommand {

    // TODO: Register this command in Heroes
    public SkillJump(Heroes plugin) {
        super(plugin);
        name = "Jump";
        description = "Skill - Jump";
        usage = "/jump";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("jump");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {            
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = hero.getPlayerClass();

            // This spells will have no CD, as it has a max limit and will take mana.
            if (!(heroClass.getSpells().contains(Spells.JUMP))) {
                plugin.getMessager().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }
            
            hero.getPlayer().setVelocity(hero.getPlayer().getVelocity().setY((hero.getPlayer().getVelocity().getY()*2)));
        }
    }
}
