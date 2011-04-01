package com.herocraftonline.dev.heroes.abilities.skills;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class SkillHarmtouch extends BaseCommand {
	protected String skillName = "Harmtouch";
	protected int cooldown = 3000;
    // TODO: Register this command in Heroes
    public SkillHarmtouch(Heroes plugin) {
        super(plugin);
        name = "Harmtouch";
        description = "Skill - Harmtouch";
        usage = "/harmtouch <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("harmtouch");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {	
        	// Cooldown - This is just a mockup for it. Change it if you want. Just trying this out for now.
        	if(plugin.getHeroManager().getHero((Player) sender).getCooldowns().containsKey(skillName)){
        		if(plugin.getHeroManager().getHero((Player) sender).getCooldowns().get(skillName) - System.currentTimeMillis() >= cooldown){
        			plugin.getHeroManager().getHero((Player) sender).getCooldowns().remove(skillName);
        		}else{
                    plugin.getMessaging().send(sender, "Sorry, $1, that skill is still on cooldown!!", ((Player) sender).getName());
                    return;
        		}
        	}
        	
        	// Ability checker
        	if (!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("Harmtouch"))) {
                plugin.getMessaging().send(sender, "Sorry, $1, that ability isn't for your class!", ((Player) sender).getName());
                return;
            }
        	
        	// Spell Stuff
            if (plugin.getServer().getPlayer(args[0]) != null) {
                double x1 = plugin.getServer().getPlayer(args[0]).getLocation().getX();
                double z1 = plugin.getServer().getPlayer(args[0]).getLocation().getZ();
                double x2 = ((Player) sender).getLocation().getX();
                double z2 = ((Player) sender).getLocation().getZ();
                double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (z2 - z1) * (z2 - z1));
                if (distance > 20) {
                    Player p = plugin.getServer().getPlayer(args[0]);
                    p.setHealth((int) (p.getHealth() - (plugin.getConfigManager().getProperties().getLevel(plugin.getHeroManager().getHero((Player) sender).getExperience()) * 0.5)));
                    plugin.getHeroManager().getHero((Player) sender).getCooldowns().put(skillName, System.currentTimeMillis());
                }else{
                    plugin.getMessaging().send(sender, "Sorry, $1, that person isn't close enough!", ((Player) sender).getName());
                }
            }
        }
    }

}
