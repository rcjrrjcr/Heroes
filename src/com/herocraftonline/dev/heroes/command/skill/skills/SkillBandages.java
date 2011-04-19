package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillBandages extends TargettedSkill{

    public SkillBandages(Heroes plugin) {
        super(plugin);
        name = "Bandage";
        description = "Skill - Bandage";
        usage = "/cast bandage";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("cast bandage");
    }

    @Override
    public void use(Player player, LivingEntity target, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());
        Properties properties = plugin.getConfigManager().getProperties();

        if (!(heroClass.getSkills().contains(getName()))) {
            plugin.getMessager().send(player, "Sorry, that ability isn't for your class!");
            return;
        }
        
        if(!player.getItemInHand().equals(Material.PAPER)){
            return;
        }
        
        if(hero.getMana() == properties.skillInfo.get(getName() + "mana")){
            target.setHealth(target.getHealth() + 4);
        }
    }

}
