package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.command.skill.skills.SkillOne.SkillPlayerListener;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillIronFist extends Skill{

    public SkillIronFist(Heroes plugin) {
        super(plugin);
        name = "Syphon";
        description = "Skill - Syphon";
        usage = "/syphon";
        minArgs = 1;
        maxArgs = 2;
        identifiers.add("syphon");
        configs.put("mana", "20");
        configs.put("level", "20"); 

        plugin.getServer().getPluginManager().registerEvent(Type.ENTITY_DAMAGE, new SkillPlayerListener(), Priority.Normal, plugin);
    }

    @Override
    public void use(Player player, String[] args) {
        // TODO Auto-generated method stub

    }
    public class SkillPlayerListener extends EntityListener {

        public void onPlayerMove(EntityDamageEvent event) {
            if(event instanceof EntityDamageByEntityEvent){
                EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
                if(subEvent.getDamager() instanceof Player){
                    Player player = (Player) subEvent.getDamager();
                    if(plugin.getHeroManager().getHero(player).getPlayerClass().getSkills().contains(getName())){
                        if(player.getItemInHand().getType() == Material.AIR){
                            event.setDamage(event.getDamage() * 2);
                        }
                    }
                }
            }
        }

    }
}