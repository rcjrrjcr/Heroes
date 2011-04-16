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
        BLACKJACK,
        BLADEGRASP,
        HARMTOUCH,
        LAYHANDS,
        SUMMON,
        TAME,
        TRACK,
        JUMP
    }

    public static enum ExperienceType {
        KILLING,
        MINING,
        CRAFTING,
        LOGGING,
    }

    private String name;
    private HeroClass parent;
    private ArmorType armorType;
    private boolean tameWolves;
    private int tameMax;
    private boolean summonCreatures;
    private int summonMax;
    private WeaponType weaponType;
    private Set<ExperienceType> experienceSources;
    private Set<Spells> spells;
    private Set<HeroClass> specializations;

    public HeroClass() {
        name = new String();
        armorType = ArmorType.LEATHER;
        weaponType = WeaponType.WOOD;
        experienceSources = new HashSet<ExperienceType>();
        specializations = new HashSet<HeroClass>();
        spells = new HashSet<Spells>();
        tameWolves = false;
        tameMax = 0;
        summonCreatures = false;
        summonMax = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HeroClass) {
            return name.equalsIgnoreCase(((HeroClass) o).getName());
        } else {
            return false;
        }
    }

    public HeroClass(String name) {
        this();
        this.name = name;
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

    public ArmorType getArmorType() {
        return armorType;
    }

    public void setArmorType(ArmorType armor) {
        this.armorType = armor;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weapon) {
        this.weaponType = weapon;
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

}
