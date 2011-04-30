package com.herocraftonline.dev.heroes.command.skill;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public abstract class Skill extends BaseCommand {

    protected ConfigurationNode config;
    protected HashMap<Material, Integer> cost = new HashMap<Material, Integer>();

    public Skill(Heroes plugin) {
        super(plugin);
    }

    protected void notifyNearbyPlayers(Vector source, String message, String... args) {
        Player[] players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            Location playerLocation = player.getLocation();
            if (playerLocation.toVector().distance(source) < 30) {
                Messaging.send(player, message, args);
            }
        }
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
