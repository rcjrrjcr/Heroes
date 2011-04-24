package com.herocraftonline.dev.heroes.command.skill;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class Skill extends BaseCommand {

	protected Map<String, String> config = new HashMap<String, String>();
    protected HashMap<Material, Integer> cost = new HashMap<Material, Integer>();

    public Skill(Heroes plugin) {
        super(plugin);
    }
    
    protected void registerEvent(Type type, Listener listener, Priority priority) {
    	plugin.getServer().getPluginManager().registerEvent(type, listener, priority, plugin);
    }
    
    protected boolean meetsLevelRequirement(Hero hero, int reqLevel) {
    	return reqLevel < plugin.getConfigManager().getProperties().getLevel(hero.getExperience());
    }

    public HashMap<Material, Integer> getCost() {
        return cost;
    }
    
    public Map<String, String> getConfig() {
    	return config;
    }
    
    public void setConfig(Map<String, String> config) {
    	this.config = config;
    }

}
