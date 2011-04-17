package com.herocraftonline.dev.heroes;

import org.bukkit.ChatColor;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

import com.herocraftonline.dev.heroes.api.BlockBreakExperienceEvent;
import com.herocraftonline.dev.heroes.api.CustomPlayerEvent;
import com.herocraftonline.dev.heroes.api.KillExperienceEvent;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class HCustomEventListener extends CustomEventListener{
    protected Heroes plugin;
    
    public HCustomEventListener(Heroes plugin) {
        this.plugin = plugin;
    }
    
    public void onCustomEvent(Event event){
        if(event instanceof BlockBreakExperienceEvent){
            BlockBreakExperienceEvent e = (BlockBreakExperienceEvent) event;
            Properties prop = plugin.getConfigManager().getProperties();
            Hero hero = plugin.getHeroManager().getHero(e.getPlayer());
            if(prop.getLevel(hero.getExperience()) != prop.getLevel(hero.getExperience() + e.getExp())){
                e.getPlayer().sendMessage(ChatColor.RED + "You just reached level" + ChatColor.BLUE + prop.getLevel(hero.getExperience() + e.getExp()));
            }
        }
        if(event instanceof KillExperienceEvent){
            KillExperienceEvent e = (KillExperienceEvent) event;
            Properties prop = plugin.getConfigManager().getProperties();
            Hero hero = plugin.getHeroManager().getHero(e.getPlayer());
            if(prop.getLevel(hero.getExperience()) != prop.getLevel(hero.getExperience() + e.getExp())){
                e.getPlayer().sendMessage(ChatColor.RED + "You just reached level" + ChatColor.BLUE + prop.getLevel(hero.getExperience() + e.getExp()));
            }
        }
    }
}
