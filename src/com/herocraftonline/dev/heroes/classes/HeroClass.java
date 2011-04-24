package com.herocraftonline.dev.heroes.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HeroClass {

    public static enum ArmorType {
        LEATHER,
        IRON,
        GOLD,
        DIAMOND
    }

    public static enum ArmorItems {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS
    }

    public static enum WeaponType {
        WOOD,
        STONE,
        IRON,
        GOLD,
        DIAMOND
    }

    public static enum WeaponItems {
        PICKAXE,
        AXE,
        HOE,
        SPADE,
        SWORD
    }

    public static enum ExperienceType {
        KILLING,
        MINING,
        CRAFTING,
        LOGGING,
    }

    private String name;
    private HeroClass parent;
    private boolean tameWolves;
    private int tameMax;
    private boolean summonCreatures;
    private int summonMax;
    private Set<String> allowedArmor;
    private Set<String> allowedWeapons;
    private Set<ExperienceType> experienceSources;
    private Map<String, Integer> skillLevelRequirements;
    private Map<String, Integer> skillManaCosts;
    private Set<HeroClass> specializations;

    public HeroClass() {
        name = new String();
        allowedArmor = new HashSet<String>();
        allowedWeapons = new HashSet<String>();
        experienceSources = new HashSet<ExperienceType>();
        specializations = new HashSet<HeroClass>();
        skillLevelRequirements = new HashMap<String, Integer>();
        tameWolves = false;
        tameMax = 0;
        summonCreatures = false;
        summonMax = 0;
    }
    
    public HeroClass(String name) {
        this();
        this.name = name;
    }
    
    public boolean hasSkill(String name) {
    	return skillLevelRequirements.containsKey(name);
    }
    
    public void addSkill(String name, int requiredLevel, int manaCost) {
    	skillLevelRequirements.put(name, requiredLevel);
    	skillManaCosts.put(name, manaCost);
    }
    
    public void removeSkill(String name) {
    	skillLevelRequirements.remove(name);
    	skillManaCosts.remove(name);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HeroClass) {
            return name.equalsIgnoreCase(((HeroClass) o).getName());
        } else {
            return false;
        }
    }

    public boolean isPrimary() {
        return parent == null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAllowedArmor() {
        return this.allowedArmor;
    }

    public void addAllowedArmor(String armor) {
        this.allowedArmor.add(armor);
    }

    public Set<String> getAllowedWeapons() {
        return this.allowedWeapons;
    }

    public void addAllowedWeapon(String weapon) {
        this.allowedWeapons.add(weapon);
    }

    public HeroClass getParent() {
        return parent == null ? null : parent;
    }

    public boolean getTamable() {
        return tameWolves;
    }

    public int getTameMax() {
        return tameMax;
    }

    public boolean getSummonable() {
        return summonCreatures;
    }

    public int getSummonMax() {
        return summonMax;
    }

    public void setParent(HeroClass parent) {
        this.parent = parent;
    }

    public Set<HeroClass> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<HeroClass> specializations) {
        this.specializations = specializations;
    }

    public Map<String, Integer> getSkillLevelRequirements() {
        return skillLevelRequirements;
    }

    public void setSkillLevelRequirements(Map<String, Integer> skillLevelRequirements) {
        this.skillLevelRequirements = skillLevelRequirements;
    }

    public Set<ExperienceType> getExperienceSources() {
        return experienceSources;
    }

    public void setExperienceSources(Set<ExperienceType> experienceSources) {
        this.experienceSources = experienceSources;
    }

    public void setTamable(Boolean tameWolves) {
        this.tameWolves = tameWolves;
    }

    public void setTameMax(int tameMax) {
        this.tameMax = tameMax;
    }

    public void setSummonable(Boolean summonCreatures) {
        this.summonCreatures = summonCreatures;
    }

    public void setSummonMax(int summonMax) {
        this.summonMax = summonMax;
    }

	public void setSkillManaCosts(Map<String, Integer> skillManaCosts) {
		this.skillManaCosts = skillManaCosts;
	}

	public Map<String, Integer> getSkillManaCosts() {
		return skillManaCosts;
	}

}
