package com.herocraftonline.dev.heroes.classes;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class HeroClass {

    public static enum ArmorType {
        LEATHER,
        IRON,
        GOLD,
        DIAMOND,
        CHAINMAIL
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
    private String description;
    private HeroClass parent;
    private Set<String> allowedArmor;
    private Set<String> allowedWeapons;
    private Set<ExperienceType> experienceSources;
    private Map<String, SkillSettings> skills;
    private Set<HeroClass> specializations;

    public HeroClass() {
        name = new String();
        description = new String();
        allowedArmor = new LinkedHashSet<String>();
        allowedWeapons = new LinkedHashSet<String>();
        experienceSources = new LinkedHashSet<ExperienceType>();
        specializations = new LinkedHashSet<HeroClass>();
        skills = new LinkedHashMap<String, SkillSettings>();
    }

    public HeroClass(String name) {
        this();
        this.name = name;
    }

    public boolean hasSkill(String name) {
        return skills.containsKey(name.toLowerCase());
    }

    public void addSkill(String name, int requiredLevel, int manaCost, int cooldown) {
        skills.put(name.toLowerCase(), new SkillSettings(requiredLevel, manaCost, cooldown));
    }

    public void removeSkill(String name) {
        skills.remove(name.toLowerCase());
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

    public void setParent(HeroClass parent) {
        this.parent = parent;
    }

    public Set<HeroClass> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<HeroClass> specializations) {
        this.specializations = specializations;
    }

    public SkillSettings getSkillSettings(String name) {
        return skills.get(name.toLowerCase());
    }

    public Set<ExperienceType> getExperienceSources() {
        return experienceSources;
    }

    public void setExperienceSources(Set<ExperienceType> experienceSources) {
        this.experienceSources = experienceSources;
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HeroClass other = (HeroClass) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
