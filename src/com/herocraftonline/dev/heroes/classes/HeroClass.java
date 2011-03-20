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

    // place holder
    public static enum Spells {
        SPELL_A,
        SPELL_B,
        SPELL_C
    }

    // place holder
    public static enum ExperienceType {
        KILLING_THINGS,
        MINING_THINGS,
        SELLING_THINGS
    }

    protected String name;
    protected ArmorType armorType;
    protected ExperienceType experienceType;
    protected Set<Spells> spells;
    protected HeroClass parent;
    protected Set<HeroClass> specializations;

    public HeroClass() {
        specializations = new HashSet<HeroClass>();
        spells = new HashSet<Spells>();
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

    public HeroClass getParent() {
        return parent == null ? null : parent;
    }

    public void setParent(HeroClass parent) {
        this.parent = parent;
    }

    public Set<HeroClass> getSpecializations() {
        return specializations;
    }

    public Set<Spells> getSpells() {
        return spells;
    }

    public ExperienceType getExperienceType() {
        return experienceType;
    }

    public void setExperienceType(ExperienceType experienceType) {
        this.experienceType = experienceType;
    }

}
