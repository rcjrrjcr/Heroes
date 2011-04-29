package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBandage extends TargettedSkill {
    
    protected HashMap<Player, Integer> playerSchedulers = new HashMap<Player, Integer>();
    protected int tickHealth;
    
    public SkillBandage(Heroes plugin) {
        super(plugin);
        name = "Bandage";
        description = "Skill - Bandage";
        usage = "/skill bandage";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill bandage");
    }
    
    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("tick-health", 1);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        if (target instanceof Player) {
            final Player tPlayer = (Player) target;
            if (!player.getItemInHand().equals(Material.PAPER)) {
                plugin.getMessager().send(player, "You need paper to perform this.");
                return false;
            }

            tickHealth = config.getInt("tick-health", 1);
            playerSchedulers.put(tPlayer, plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                public int timesRan = 0;

                @Override
                public void run() {
                    if (timesRan == 10) {
                        int id = playerSchedulers.remove(tPlayer);
                        plugin.getServer().getScheduler().cancelTask(id);
                    } else {
                        timesRan++;
                        tPlayer.setHealth(tPlayer.getHealth() + tickHealth);
                    }
                }
            }, 20L, 20L));
            
            notifyNearbyPlayers(player.getLocation().toVector(), "$1 is bandaging $2.", player.getName(), tPlayer.getName());
            return true;
        }
        return false;
    }
}
