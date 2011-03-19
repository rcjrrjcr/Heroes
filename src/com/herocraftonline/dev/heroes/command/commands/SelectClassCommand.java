package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistance.player;
import com.herocraftonline.dev.heroes.util.Misc;
import com.herocraftonline.dev.heroes.util.Properties;
import com.herocraftonline.dev.heroes.util.Properties.Class;

public class SelectClassCommand extends BaseCommand {

	public SelectClassCommand(Heroes plugin) {
		super(plugin);
		name = "";
		description = "Allows you to advance from a primary class to it's secondary";
		usage = "/class select";
		minArgs = 1;
		maxArgs = 1;
		identifiers.add("class select");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		String s = Properties.validateClass(args[0]);
		if(s == ""){
			return;
		}
		if(sender instanceof Player){
			if(Properties.primaryClass(player.getClass(p)) && player.getLevel(player.getExp(p)) > 20){
				Class eClass = Properties.Class.valueOf(player.getClass(p));
				switch(eClass){
				case Warrior:
					if(Properties.Warrior.valueOf(s) != null){
						player.setClass(p, s);
						Misc.sendMessage(p, "Well Done! " + s);
					}else{
						Misc.sendMessage(p, "Sorry, that class doens't belong to " + eClass.toString());
					}
					break;
				case Rogue:
					if(Properties.Rogue.valueOf(s) != null){
						player.setClass(p, s);
						Misc.sendMessage(p, "Well Done! " + s);
					}else{
						Misc.sendMessage(p, "Sorry, that class doens't belong to " + eClass.toString());
					}
					break;

				case Mage:
					if(Properties.Mage.valueOf(s) != null){
						player.setClass(p, s);
						Misc.sendMessage(p, "Well Done! " + s);
					}else{
						Misc.sendMessage(p, "Sorry, that class doens't belong to " + eClass.toString());
					}
					break;
				case Healer:
					if(Properties.Healer.valueOf(s) != null){
						player.setClass(p, s);
						Misc.sendMessage(p, "Well Done! " + s);
					}else{
						Misc.sendMessage(p, "Sorry, that class doens't belong to " + eClass.toString());
					}
					break;
				case Diplomat:
					if(Properties.Diplomat.valueOf(s) != null){
						player.setClass(p, s);
						Misc.sendMessage(p, "Well Done! " + s);
					}else{
						Misc.sendMessage(p, "Sorry, that class doens't belong to " + eClass.toString());
					}
					break;
				case Crafter:
					if(Properties.Crafter.valueOf(s) != null){
						player.setClass(p, s);
						Misc.sendMessage(p, "Well Done! " + s);
					}else{
						Misc.sendMessage(p, "Sorry, that class doens't belong to " + eClass.toString());
					}
					break;
				}
			}else{
				Misc.sendMessage(p, "Sorry, you don't meet the requirements to advance");
			}
		}

	}
}
