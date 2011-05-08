package com.herocraftonline.dev.heroes.command.skill;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public abstract class Skill extends BaseCommand {

    private ConfigurationNode config;

    public Skill(Heroes plugin) {
        super(plugin);
    }

    public void init() {}

    protected void notifyNearbyPlayers(Location source, String message, String... args) {
        Player[] players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            Location playerLocation = player.getLocation();
            if (source.getWorld().equals(playerLocation.getWorld())) {
                if (playerLocation.toVector().distance(source.toVector()) < 30) {
                    Messaging.send(player, message, args);
                }
            }
        }
    }

    protected void registerEvent(Type type, Listener listener, Priority priority) {
        plugin.getServer().getPluginManager().registerEvent(type, listener, priority, plugin);
    }

    protected boolean meetsLevelRequirement(Hero hero, int reqLevel) {
        return plugin.getConfigManager().getProperties().getLevel(hero.getExp()) >= reqLevel;
    }

    public ConfigurationNode getDefaultConfig() {
        return Configuration.getEmptyNode();
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getSetting(HeroClass heroClass, String setting, T def) {
        Object value = null;
        if (heroClass != null) {
            value = heroClass.getSkillSettings(name).getProperty(setting);
        }
        if (value == null) {
            value = config.getProperty(setting);
        }
        try {
            return value == null ? def : (T) value;
        } catch (ClassCastException e) {
            return def;
        }
    }

    public void setConfig(ConfigurationNode config) {
        this.config = config;
    }

}
