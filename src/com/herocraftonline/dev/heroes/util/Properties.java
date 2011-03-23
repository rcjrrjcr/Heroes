package com.herocraftonline.dev.heroes.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.CreatureType;

public class Properties {
    
    // Leveling//
    public static double power;
    public static int baseExp;
    public static int maxExp;
    public static int maxLevel;
    public static ArrayList<Integer> level = new ArrayList<Integer>();
    // Experience//
    public static int playerKillingExp = 0;
    public static HashMap<CreatureType, Integer> creatureKillingExp = new HashMap<CreatureType, Integer>();
    public static HashMap<Material, Integer> miningExp = new HashMap<Material, Integer>();
    public static int loggingExp = 0;
    // Default//
    public static String defClass;
    public static int defLevel;
    // Properties//
    public static boolean iConomy;
    public static ChatColor cColor;
    public static String prefix;
    public static int swapcost;

    public static void calcExp() {
        level.clear();
        double A = 2 * (maxExp - baseExp) * Math.pow(maxLevel, (power * -1));
        for (int n = 1; n <= maxLevel; n++) {
            level.add((int) (A / 2 * Math.pow(n - 1, power) + baseExp));
        }
    }
    
}
