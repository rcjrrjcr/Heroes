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

    private String name;
    private HeroClass parent;
    private ArmorType armorType;
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

}
