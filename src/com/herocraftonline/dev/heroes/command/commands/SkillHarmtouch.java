package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class SkillHarmtouch extends BaseCommand {

	// TODO: Register this command in Heroes
	public SkillHarmtouch(Heroes plugin) {
		super(plugin);
		name = "Harmtouch";
		description = "Dreadknight Skill - Harmtouch";
		usage = "/harmtouch <player>";
		minArgs = 1;
		maxArgs = 1;
		identifiers.add("harmtouch");
	}

	@Override
	public void execute(CommandSender sender, String[] args){
		if (sender instanceof Player) {
			// TODO: Check for CD time left, if 0 execute.
			if(!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("Harmtouch"))){
				plugin.getMessaging().send(sender, "Sorry, $1, that ability isn't for your class!", ((Player)sender).getName());
				return;
			}
			if(plugin.getServer().getPlayer(args[0]) != null){
				double x1 = plugin.getServer().getPlayer(args[0]).getLocation().getX();
				double z1 = plugin.getServer().getPlayer(args[0]).getLocation().getZ();
				double x2 = ((Player) sender).getLocation().getX();
				double z2 = ((Player) sender).getLocation().getZ();
				double distance = Math.sqrt((x2-x1)*(x2-x1) + (z2-z1)*(z2-z1));
				if(distance > 20){
					Player p = plugin.getServer().getPlayer(args[0]);
					p.setHealth((int) (p.getHealth() - (plugin.getConfigManager().getProperties().getLevel(plugin.getHeroManager().getHero((Player) sender).getExperience())*0.5)));
				}
			}
		}
	}
	
	
	

}
