package com.herocraftonline.dev.heroes.command.skills;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillTrack extends Skill {

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
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

            
            if (!(heroClass.getSpells().contains(Spells.TRACK))) {
                plugin.getMessager().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }
            
            if(hero.getCooldowns().containsKey(getName())){
                if(hero.getCooldowns().get(getName()) > 3000){
                    return;
                }
            }
            
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player target = plugin.getServer().getPlayer(args[0]);
                Location location = target.getLocation();
                sender.sendMessage(location.toString());
                player.setCompassTarget(location);
            }
        }
    }

    @Override
    public void use(Player user, String[] args) {
        // TODO Auto-generated method stub
        
    }
}
