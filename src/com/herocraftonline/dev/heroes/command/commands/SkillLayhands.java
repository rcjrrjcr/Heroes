package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class SkillLayhands extends BaseCommand {

	public SkillLayhands(Heroes plugin) {
		super(plugin);
		name = "Layhands";
		description = "Paladin Skill - Layhands";
		usage = "/layhands <player>";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("layhands");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			// Check if cooldown if complete for that player
			if(!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("Layhands"))){
				
			}
			if(plugin.getServer().getPlayer(args[0]) != null){
				Player p = plugin.getServer().getPlayer(args[0]);
				p.setHealth(20);
			}
		}
	}

}
