package com.herocraftonline.dev.heroes.abilities.skill;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
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
            Hero hero = plugin.getHeroManager().getHero((Player) sender);
            
            // TODO: Check for CD time left, if 0 execute.
            if (!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("Jump"))) {
                plugin.getMessager().send(sender, "Sorry, $1, that ability isn't for your class!", ((Player) sender).getName());
                return;
            }
            
            hero.getPlayer().setVelocity(hero.getPlayer().getVelocity().setY((hero.getPlayer().getVelocity().getY()*2)));
        }
    }
}
