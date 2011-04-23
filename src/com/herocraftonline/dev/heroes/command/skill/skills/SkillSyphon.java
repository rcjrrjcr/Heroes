package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillSyphon extends Skill{

    public SkillSyphon(Heroes plugin) {
        super(plugin);
        name = "Syphon";
        description = "Skill - Syphon";
        usage = "/syphon";
        minArgs = 1;
        maxArgs = 2;
        identifiers.add("syphon");
        configs.put("mana", "20");
        configs.put("level", "20");
    }

    @Override
    public void use(Player player, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());
        Properties p = plugin.getConfigManager().getProperties();
        
        if(!heroClass.getSkills().contains(getName()) || 
                !(p.getLevel(hero.getExperience()) > Integer.parseInt(p.skillInfo.get(getName() + "level")))
                || !(hero.getMana() > Integer.parseInt(p.skillInfo.get(getName() + "mana")))){
            return;
        }
        if(plugin.getServer().getPlayer(args[0]) != null){
            Player t = plugin.getServer().getPlayer(args[0]);
            if(args.length == 1){
               t.setHealth(t.getHealth() + 4);
               player.setHealth(player.getHealth() - 4);
            }else{
                try{
                    t.setHealth(t.getHealth() + Integer.parseInt(args[1]));
                    player.setHealth(player.getHealth() - Integer.parseInt(args[1])*-1);
                }catch(NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "Heroes: Sorry, that's an incorrect number!");
                }
            }
        }

        
    }    
  
    
}
