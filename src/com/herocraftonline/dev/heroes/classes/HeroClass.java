package com.herocraftonline.dev.heroes.classes;

import java.util.HashSet;
import java.util.Set;

public class HeroClass {

    public static enum ArmorType {
        LEATHER,
        IRON,
        GOLD,
        DIAMOND
    }
    
    public static enum WeaponType {
    	WOOD,
    	STONE,
    	IRON,
    	GOLD,
    	DIAMOND
    }

    // place holder
    public static enum Spells {
        SPELL_A,
        SPELL_B,
        SPELL_C
    }

    public static enum ExperienceType {
        KILLING,
        MINING,
        CRAFTING,
        LOGGING,
    }

    protected String name;
    protected HeroClass parent;
    protected ArmorType armorType;
    protected WeaponType weaponType;
    protected Set<ExperienceType> experienceSources;
    protected Set<Spells> spells;
    protected Set<HeroClass> specializations;
    protected boolean starterProfession;

    public HeroClass() {
        name = new String();
        armorType = ArmorType.LEATHER;
        weaponType = WeaponType.WOOD;
        experienceSources = new HashSet<ExperienceType>();
        specializations = new HashSet<HeroClass>();
        spells = new HashSet<Spells>();
        starterProfession = false;
    }
    
    public HeroClass(String name) {
        this();
        this.name = name;
    }

    public boolean isPrimary() {
        return parent == null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public void setArmorType(ArmorType armor) {
        this.armorType = armor;
    }
    
    public WeaponType getWeaponType() {
    	return weaponType;
    }
    
    public void setWeaponType(WeaponType weapon){
    	this.weaponType = weapon;
    }

    public HeroClass getParent() {
        return parent == null ? null : parent;
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

    public Set<Spells> getSpells() {
        return spells;
    }
    
    public void setSpells(Set<Spells> spells) {
        this.spells = spells;
    }
    
    public Set<ExperienceType> getExperienceSources() {
        return experienceSources;
    }

    public void setExperienceSources(Set<ExperienceType> experienceSources) {
        this.experienceSources = experienceSources;
    }

    public boolean isStarterProfession() {
        return starterProfession;
    }

    public void setStarterProfession(boolean starterProfession) {
        this.starterProfession = starterProfession;
    }

}
