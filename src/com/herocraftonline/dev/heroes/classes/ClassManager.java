package com.herocraftonline.dev.heroes.classes;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.util.config.Configuration;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass.ArmorType;
import com.herocraftonline.dev.heroes.classes.HeroClass.ExperienceType;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.classes.HeroClass.WeaponType;

public class ClassManager {

    private final Heroes plugin;
    private Set<HeroClass> classes;
    private HeroClass defaultClass;

    public ClassManager(Heroes plugin) {
        this.plugin = plugin;
        this.classes = new HashSet<HeroClass>();
    }

    public HeroClass getClass(String name) {
        for (HeroClass c : classes) {
            if (name.equalsIgnoreCase(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public boolean addClass(HeroClass c) {
        return classes.add(c);
    }

    public boolean removeClass(HeroClass c) {
        return classes.remove(c);
    }

    public Set<HeroClass> getClasses() {
        return classes;
    }

    public void loadClasses(File file) {
        Configuration config = new Configuration(file);
        config.load();
        List<String> classNames = config.getKeys("classes");
        for (String className : classNames) {
            HeroClass newClass = new HeroClass(className);

            String armor = config.getString("classes." + className + ".permitted-armor", "DIAMOND");
            try {
                newClass.setArmorType(ArmorType.valueOf(armor));
            } catch (IllegalArgumentException e) {
                plugin.log(Level.WARNING, "Invalid armor type (" + armor + ") defined for " + className + ". Using default armor type instead.");
                newClass.setArmorType(ArmorType.DIAMOND);
            }

            String weapon = config.getString("classes." + className + ".permitted-weapon", "DIAMOND");
            try {
                newClass.setWeaponType(WeaponType.valueOf(weapon));
            } catch (IllegalArgumentException e) {
                plugin.log(Level.WARNING, "Invalid weapon type (" + weapon + ") defined for " + className + ". Using default weapon type instead.");
                newClass.setWeaponType(WeaponType.DIAMOND);
            }

            List<String> spellNames = config.getStringList("classes." + className + ".permitted-spells", null);
            Set<Spells> spells = new HashSet<Spells>();
            for (String spell : spellNames) {
                try {
                    ;
                    boolean added = spells.add(Spells.valueOf(spell));
                    if (!added) {
                        plugin.log(Level.WARNING, "Duplicate spell (" + weapon + ") defined for " + className + ".");
                    }
                } catch (IllegalArgumentException e) {
                    plugin.log(Level.WARNING, "Invalid spell (" + spell + ") defined for " + className + ". Skipping this spell.");
                }
            }
            newClass.setSpells(spells);

            List<String> experienceNames = config.getStringList("classes." + className + ".experience-sources", null);
            Set<ExperienceType> experienceSources = new HashSet<ExperienceType>();
            for (String experience : experienceNames) {
                try {
                    boolean added = experienceSources.add(ExperienceType.valueOf(experience));
                    if (!added) {
                        plugin.log(Level.WARNING, "Duplicate experience source (" + experience + ") defined for " + className + ".");
                    }
                } catch (IllegalArgumentException e) {
                    plugin.log(Level.WARNING, "Invalid experience source (" + experience + ") defined for " + className + ". Skipping this source.");
                }
            }
            newClass.setExperienceSources(experienceSources);
            
            boolean Tame = config.getBoolean("classes." + className + ".tame", false);
            try{
            	newClass.setTamable(Tame);
            }catch(Exception e){
            	plugin.log(Level.WARNING, "tame not set correctly -  (" + Tame + ") is not true or false for " + className);
            }
            
            int maxTame = config.getInt("classes." + className + ".tameMax", 0);
            try{
            	newClass.setTameMax(maxTame);
            }catch(Exception e){
            	plugin.log(Level.WARNING, "tame-max not set correctly -  (" + maxTame + ") is not a number for - " + className);
            }
            
            boolean summon = config.getBoolean("classes." + className + ".summon", false);
            try{
            	newClass.setTamable(summon);
            }catch(Exception e){
            	plugin.log(Level.WARNING, "summon not set correctly -  (" + summon + ") is not true or false for " + className);
            }
            
            int maxSummon = config.getInt("classes." + className + ".tameMax", 0);
            try{
            	newClass.setTameMax(maxSummon);
            }catch(Exception e){
            	plugin.log(Level.WARNING, "summon-max not set correctly -  (" + maxSummon + ") is not a number for - " + className);
            }
            
            
            boolean added = addClass(newClass);
            if (!added) {
                plugin.log(Level.WARNING, "Duplicate class (" + className + ") found. Skipping this class.");
            } else {
                plugin.log(Level.INFO, "Loaded class: " + className);
                if (config.getBoolean("classes." + className + ".default", false)) {
                    plugin.log(Level.INFO, "Default class found: " + className);
                    defaultClass = newClass;
                }
            }
        }

        for (HeroClass unlinkedClass : classes) {
            String className = unlinkedClass.getName();
            String parentName = config.getString("classes." + className + "parent");
            if (parentName != null && (!parentName.isEmpty() || parentName.equals("null"))) {
                HeroClass parent = getClass(parentName);
                parent.getSpecializations().add(unlinkedClass);
                unlinkedClass.setParent(parent);
            }
        }
    }

    public void setDefaultClass(HeroClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public HeroClass getDefaultClass() {
        return defaultClass;
    }

}
