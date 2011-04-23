package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillRevive extends Skill {
    //TODO: Cooldowns
    public SkillRevive(Heroes plugin) {
        super(plugin);
        name = "Revive";
        description = "Skill - Revive";
        usage = "/revive";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("revive");
        configs.put("mana", "20");
        configs.put("level", "20");
        configs.put("cooldown", "20");
    }

    @Override
    public void use(Player player, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = hero.getPlayerClass();
        Properties p = plugin.getConfigManager().getProperties();
        Player t = null;

        if (!heroClass.getSkills().contains(getName())) {
            if (!(p.getLevel(hero.getExperience()) > Integer.parseInt(p.skillInfo.get(getName() + "level")))) {
                if (!(hero.getMana() > Integer.parseInt(p.skillInfo.get(getName() + "mana")))) {
                    return;
                }
            }
        }
        if(plugin.getServer().getPlayer(args[0]) != null){
            t = plugin.getServer().getPlayer(args[0]);
        }else{

        }

        if(p.playerDeaths.containsKey(t)){
            Location target = p.playerDeaths.get(t);
            double dx = player.getLocation().getX() - target.getX();
            double dz = player.getLocation().getZ() - target.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            if(hero.getParty().getMembers().contains(t)){
                if (distance < 50) {
                    if(t.isDead()){
                        player.sendMessage(ChatColor.RED + "That player is still dead");
                    }else{
                        t.teleport(target);
                    }
                }
            }
        }
    }
}
