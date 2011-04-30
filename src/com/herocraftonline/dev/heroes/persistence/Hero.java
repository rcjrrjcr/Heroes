package com.herocraftonline.dev.heroes.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.party.HeroParty;

public class Hero {

    protected final Heroes plugin;
    protected Player player;
    protected HeroClass playerClass;
    protected int experience;
    protected int mana;
    protected boolean verbose;
    protected List<String> masteries;
    protected Map<String, Long> cooldowns;
    protected Map<Entity, CreatureType> summons;
    protected Map<String, Double> effects;
    protected Map<Material, String[]> binds;
    protected HeroParty party;
    protected Map<Player, HeroParty> invites;
    protected List<ItemStack> itemRecovery;

    public Hero(Heroes plugin, Player player, HeroClass playerClass, int experience, int mana, boolean verbose, List<String> masteries, List<ItemStack> itemRecovery) {
        this.plugin = plugin;
        this.player = player;
        this.playerClass = playerClass;
        this.experience = experience;
        this.mana = mana;
        this.masteries = masteries;
        this.itemRecovery = itemRecovery;
        this.cooldowns = new HashMap<String, Long>();
        this.summons = new HashMap<Entity, CreatureType>();
        this.binds = new HashMap<Material, String[]>();
        this.party = null;
        this.invites = new HashMap<Player, HeroParty>();
        this.effects = new HashMap<String, Double>();
        this.verbose = verbose;
    }

    public void addItem(ItemStack item) {
        this.itemRecovery.add(item);
    }

    public void setItems(List<ItemStack> items) {
        this.itemRecovery = items;
    }

    public List<ItemStack> getItems() {
        return this.itemRecovery;
    }

    public Player getPlayer() {
        Player servPlayer = plugin.getServer().getPlayer(player.getName());
        if (servPlayer != null && player != servPlayer) {
            player = servPlayer;
        }
        return player;
    }

    public HeroClass getPlayerClass() {
        return playerClass;
    }

    public int getExperience() {
        return experience;
    }

    public int getMana() {
        return mana;
    }

    public List<String> getMasteries() {
        return masteries;
    }

    public void setPlayerClass(HeroClass playerClass) {
        this.playerClass = playerClass;
        // Check the Players inventory now that they have changed class.
        this.plugin.inventoryCheck(getPlayer());
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMasteries(ArrayList<String> masterys) {
        this.masteries = masterys;
    }

    public Map<String, Long> getCooldowns() {
        return cooldowns;
    }

    public Map<Entity, CreatureType> getSummons() {
        return summons;
    }

    public Map<String, Double> getEffects() {
        return effects;
    }

    public Map<Material, String[]> getBinds() {
        return binds;
    }

    public void bind(Material material, String[] skillName) {
        binds.put(material, skillName);
    }

    public void unbind(Material material) {
        binds.remove(material);
    }

    public HeroParty getParty() {
        return party;
    }

    public void setParty(HeroParty party) {
        this.party = party;
    }

    public Map<Player, HeroParty> getInvites() {
        return invites;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Hero) {
            return player.getName().equalsIgnoreCase(((Hero) o).getPlayer().getName());
        } else {
            return false;
        }
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
