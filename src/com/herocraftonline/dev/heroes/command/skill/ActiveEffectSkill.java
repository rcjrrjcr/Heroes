package com.herocraftonline.dev.heroes.command.skill;

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class ActiveEffectSkill extends ActiveSkill {

    protected String expiryText = null;

    public ActiveEffectSkill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        useText = config.getString("use-text", "%hero% gained %skill%!");
        if (useText != null) {
            useText = useText.replace("%hero%", "$1").replace("%skill%", "$2");
        }
        expiryText = config.getString("expire-text", "%hero% lost %skill%!");
        if (expiryText != null) {
            expiryText = expiryText.replace("%hero%", "$1").replace("%skill%", "$2");
        }
    }
    
    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("use-text", "%hero% gained %skill%!");
        node.setProperty("expire-text", "%hero% lost %skill%!");
        return node;
    }

    public void onExpire(Hero hero) {
        if (expiryText != null) {
            notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), expiryText, hero.getPlayer().getName(), name);
        }
    }
}
