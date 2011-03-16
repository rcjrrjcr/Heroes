package com.herocraftonline.dev.heroes.command.commands;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.util.Updater;




public class updateCommand extends BaseCommand {

	public updateCommand(Heroes plugin) {
		super(plugin);
		name = "Update";
		description = "Updates the plugin";
		usage = "/heroes admin update";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("heroes admin update");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Updater.updateLatest();
		}
	}


}