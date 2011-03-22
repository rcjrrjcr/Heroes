package com.herocraftonline.dev.heroes.util;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;

public class Properties {
    
    // Leveling//
    public static double power;
    public static int baseExp;
    public static int maxExp;
    public static int maxLevel;
    public static ArrayList<Integer> level = new ArrayList<Integer>();
    // Default//
    public static String defClass;
    public static int defLevel;
    // Properties//
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
    
}
