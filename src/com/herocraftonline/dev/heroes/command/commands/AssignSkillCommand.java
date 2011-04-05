package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class AssignSkillCommand extends BaseCommand {

    public AssignSkillCommand(Heroes plugin) {
        super(plugin);
        name = "AssignSkill";
        description = "Assigns a skill to an item";
        usage = "/assign <spell>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("assign");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if(Spells.valueOf(args[0]) != null){
                if(plugin.getHeroManager().getHero((Player) sender).getPlayerClass().getSpells().contains(Spells.valueOf(args[0]))){

                    plugin.getHeroManager().getHero((Player) sender).setSkill(Spells.valueOf(args[0]));
                    plugin.getMessaging().send(sender, "That has been assigned as your skill", null);
                }else{
                    plugin.getMessaging().send(sender, "Yuou haven't got that skill!", null);

                }
            }else{
                plugin.getMessaging().send(sender, "That isn't a skill", null);       
            }
        }
    }
}
