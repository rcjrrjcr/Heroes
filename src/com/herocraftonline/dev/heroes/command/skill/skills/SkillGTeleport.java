package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillGTeleport extends Skill {
//TODO: Cooldowns
    public SkillGTeleport(Heroes plugin) {
        super(plugin);
        name = "Group Teleport";
        description = "Skill - Group Teleport";
        usage = "/gteleport";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("gteleport");
        configs.put("mana", "20");
        configs.put("level", "20");
        configs.put("cooldown", "20");
    }

    @Override
    public void use(Player player, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = hero.getPlayerClass();
        Properties p = plugin.getConfigManager().getProperties();
        
        if (!heroClass.getSkills().contains(getName())) {
            if (!(p.getLevel(hero.getExperience()) > Integer.parseInt(p.skillInfo.get(getName() + "level")))) {
                if (!(hero.getMana() > Integer.parseInt(p.skillInfo.get(getName() + "mana")))) {
                    return;
                }
            }
        }
        
        if(hero.getParty() != null && hero.getParty().getMembers().size() != 1){
            for(Player n : hero.getParty().getMembers()){
                n.teleport(player);
            }
            
            hero.getCooldowns().put(getName(), System.currentTimeMillis() + Long.parseLong(p.skillInfo.get(getName() + "cooldown")));
        }
    }
}
