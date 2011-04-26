package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class LevelInformationCommand extends BaseCommand {

    public LevelInformationCommand(Heroes plugin) {
        super(plugin);
        name = "LevelInformation";
        description = "Player Level information";
        usage = "/hlevel";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("heroes level");
        identifiers.add("h level");
        identifiers.add("hlevel");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // TODO Auto-generated method stub

    }

}
