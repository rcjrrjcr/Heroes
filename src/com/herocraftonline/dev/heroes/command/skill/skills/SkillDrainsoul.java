package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillDrainsoul extends TargettedSkill {

    public SkillDrainsoul(Heroes plugin) {
        super(plugin);
        name = "Drainsoul";
        description = "Absorb health from target";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill drainsoul");
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = super.getDefaultConfig();
        node.setProperty("absorb-amount", 4);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        int absorbamount = getSetting(hero.getHeroClass(), "absorb-amount", 4);
<<<<<<< HEAD
        if((hero.getPlayer().getHealth() + absorbamount) > 100){
            absorbamount = (100 - hero.getPlayer().getHealth());
        }
        if(target.getHealth() < absorbamount){
            player.setHealth(player.getHealth() + target.getHealth());
            target.damage(target.getHealth());
            notifyNearbyPlayers(hero.getPlayer().getLocation(), useText, hero.getPlayer().getName(), name);
        }else{
            player.setHealth(player.getHealth() + absorbamount);
            target.damage(absorbamount);  
            notifyNearbyPlayers(hero.getPlayer().getLocation(), useText, hero.getPlayer().getName(), name);
=======
        if (target.getHealth() < absorbamount) {
            player.setHealth(player.getHealth() + target.getHealth());
            target.damage(target.getHealth());
        } else {
            player.setHealth(player.getHealth() + absorbamount);
            target.damage(absorbamount);
>>>>>>> register
        }
        return true;
    }

}
