package com.herocraftonline.dev.heroes.util;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;

public class Properties {

    public enum Class {
        Warrior,
        Rogue,
        Mage,
        Healer,
        Diplomat,
        Crafter
    }

    public enum Warrior {
        Paladin,
        Dreadknight,
        Gladiator,
        Samurai
    }

    public enum Rogue {
        Ninja,
        Ranger,
        Thief,
        Treasure_Hunter
    }

    public enum Mage {
        Pyro,
        Icemage,
        Necromancer,
        Enchanter
    }

    public enum Healer {
        Cleric,
        Priest,
        Monk,
        Bloodmage
    }

    public enum Diplomat {
        Ambassador,
        Noble,
        Lord,
        Duke,
        King
    }

    public enum Crafter {
        Architect,
        Smith,
        Farmer,
        Carpenter,
        Engineer
    }
    
    public static enum allClasses {
        Warrior,
        Rogue,
        Mage,
        Healer,
        Diplomat,
        Crafter,
        Paladin,
        Dreadknight,
        Gladiator,
        Samurai,
        Ninja,
        Ranger,
        Thief,
        Treasure_Hunter,
        Pyro,
        Icemage,
        Necromancer,
        Enchanter,
        Cleric,
        Priest,
        Monk,
        Bloodmage,
        Ambassador,
        Noble,
        Lord,
        Duke,
        King,
        Architect,
        Smith,
        Farmer,
        Carpenter,
        Engineer
    }
    //Leveling//
    public static double power;
    public static int baseExp;
    public static int maxExp;
    public static int maxLevel;
    public static ArrayList<Integer> level = new ArrayList<Integer>();
    //Default//
    public static String defClass;
    public static int defLevel;
    //Properties//
    public static boolean iConomy;
    public static ChatColor cColor;
    public static String prefix;
    public static int swapcost;
    
    public static final File dataFolder = new File("plugins" + File.separator + "Heroes");

    public static void calcExp() {
        level.clear();
        double A = 2 * (maxExp - baseExp) * Math.pow(maxLevel, (power * -1));
        for (int n = 1; n <= maxLevel; n++) {
            level.add((int) (A / 2 * Math.pow(n - 1, power) + baseExp));
        }
    }
    
    public static String validateClass(String c){
    	for(allClasses n : allClasses.values()){
    		if(c.equalsIgnoreCase(n.toString())){
    			return n.toString();
    		}
    	}
		return "";
    }
   
    public static Boolean primaryClass(String c){
        if(c==null) return false;
        
    	for(Class n : Class.values()){
    		if(c.equalsIgnoreCase(n.toString())){
    			return true;
    		}
    	}
		return false;
    }
    
}
