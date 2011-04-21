package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillInvuln extends Skill{

    public SkillInvuln(Heroes plugin) {
        super(plugin);
        name = "Invuln";
        description = "Skill - Invuln";
        usage = "/Invuln";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("invuln");
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
        
        hero.getEffects().put(getName(), System.currentTimeMillis() + 10000);
        
    }    
  
    
}
