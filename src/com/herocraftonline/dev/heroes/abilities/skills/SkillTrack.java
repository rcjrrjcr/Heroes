package com.herocraftonline.dev.heroes.abilities.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillTrack extends BaseCommand {

    // TODO: Register this command in Heroes
    public SkillTrack(Heroes plugin) {
        super(plugin);
        name = "Track";
        description = "Skill - Track";
        usage = "/track <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("track");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Hero hero = plugin.getHeroManager().getHero((Player) sender);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

            if (!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("Track"))) {
                plugin.getMessaging().send(sender, "Sorry, $1, that ability isn't for your class!", ((Player) sender).getName());
                return;
            }
            
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player p = plugin.getServer().getPlayer(args[0]);
                sender.sendMessage(p.getLocation().toString());
                ((Player) sender).setCompassTarget(p.getLocation());
            }
        }
    }
}
