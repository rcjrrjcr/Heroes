package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class ClassCommand extends BaseCommand {

	public ClassCommand(Heroes plugin) {
		super(plugin);
		name = "Class";
		description = "Changes a persons professions";
		usage = "/class change";
		minArgs = 1;
		maxArgs = 1;
		identifiers.add("class change");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
	}

}
