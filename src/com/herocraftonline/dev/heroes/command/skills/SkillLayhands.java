package com.herocraftonline.dev.heroes.command.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.command.BaseSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillLayhands extends BaseSkill {

    // TODO: Register this command in Heroes
    public SkillLayhands(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Layhands";
        usage = "/layhands <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("layhands");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());
            
            // TODO: Check for CD time left, if 0 execute.
            if (!(heroClass.getSpells().contains(Spells.LAYHANDS))) {
                plugin.getMessager().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }
            
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                target.setHealth(20);
            }
        }
    }
}
