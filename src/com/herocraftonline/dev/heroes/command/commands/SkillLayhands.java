package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class SkillLayhands extends BaseCommand {

    // TODO: Register this command in Heroes
    public SkillLayhands(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Layhands";
        usage = "/layhands <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("layhands");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            // TODO: Check for CD time left, if 0 execute.
            if (!(plugin.getClassManager().getClass(plugin.getHeroManager().getHero((Player) sender).getClass().toString()).getSpells().contains("Layhands"))) {
                plugin.getMessaging().send(sender, "Sorry, $1, that ability isn't for your class!", ((Player) sender).getName());
                return;
            }
            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player p = plugin.getServer().getPlayer(args[0]);
                p.setHealth(20);
            }
        }
    }

}
