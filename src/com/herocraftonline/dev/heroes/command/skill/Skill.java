package com.herocraftonline.dev.heroes.command.skill;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class Skill extends BaseCommand {

    protected ConfigurationNode config;
    protected HashMap<Material, Integer> cost = new HashMap<Material, Integer>();

    public Skill(Heroes plugin) {
        super(plugin);
    }

    protected void registerEvent(Type type, Listener listener, Priority priority) {
        plugin.getServer().getPluginManager().registerEvent(type, listener, priority, plugin);
    }

    protected boolean meetsLevelRequirement(Hero hero, int reqLevel) {
        return plugin.getConfigManager().getProperties().getLevel(hero.getExperience()) >= reqLevel;
    }

    public ConfigurationNode getDefaultConfig() {
        return Configuration.getEmptyNode();
    }

    public HashMap<Material, Integer> getCost() {
        return cost;
    }

    public ConfigurationNode getConfig() {
        return config;
    }

    public void setConfig(ConfigurationNode config) {
        this.config = config;
    }

}
