package com.herocraftonline.dev.heroes.abilities.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBlackjack extends BaseCommand {

    // TODO: Register this command in Heroes
    public SkillBlackjack(Heroes plugin) {
        super(plugin);
        name = "Blackjack";
        description = "Skill - blackjack";
        usage = "/blackjack <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("blackjack");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Hero hero = plugin.getHeroManager().getHero((Player) sender);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());
            
            // TODO: Check for CD time left, if 0 execute.
            if (!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("blackjack"))) {
                plugin.getMessaging().send(sender, "Sorry, $1, that ability isn't for your class!", ((Player) sender).getName());
                return;
            }
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player p = plugin.getServer().getPlayer(args[0]);
                for(Entity entity : p.getNearbyEntities(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ())){
                    if(entity instanceof Player){
                        Player surroundingPlayer = (Player) entity;
                        if(surroundingPlayer.getName() == p.getName()){
                            
                            if (!(hero.getEffects().containsKey("blackjack"))) {
                                hero.getEffects().put("blackjack", System.currentTimeMillis());
                            }else{
                                if (hero.getEffects().get("blackjack") > 60000){
                                    hero.getEffects().put("blackjack", System.currentTimeMillis());
                                }
                            }
                            
                        }
                    }
                }
                p.setHealth(20);
            }
        }
    }
}