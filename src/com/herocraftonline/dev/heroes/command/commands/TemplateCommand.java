package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class TemplateCommand extends BaseCommand {

    public TemplateCommand(Heroes plugin) {
        super(plugin);
        name = "";
        description = "Reloads the heroes config file";
        usage = "/heroes admin reload";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("heroes admin reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {

        }
    }

}
