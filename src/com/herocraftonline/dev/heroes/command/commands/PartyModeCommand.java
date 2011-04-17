package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class PartyModeCommand extends BaseCommand {

    public PartyModeCommand(Heroes plugin) {
        super(plugin);
        name = "Party Mode";
        description = "Changes the party modes.";
        usage = "/mode <mode>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("mode");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            
            if(!plugin.getHeroManager().getHero(p).getParty().getLeader().equals(p)){
                return;
            }
            
            if(args[0].contains("exp")){
                if(args[0].contains("+")){
                    plugin.getHeroManager().getHero(p).getParty().setPvp(true);
                }else if(args[0].contains("-")){
                    plugin.getHeroManager().getHero(p).getParty().setPvp(false);
                }
            }else if(args[0].contains("pvp")){
                if(args[0].contains("+")){
                    plugin.getHeroManager().getHero(p).getParty().setExp(true);
                }else if(args[0].contains("-")){
                    plugin.getHeroManager().getHero(p).getParty().setExp(false);
                }  
            }
        }
    }

}
