package com.herocraftonline.dev.heroes.util;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.CreatureType;

public class Properties {

    // Debug Mode //
    public boolean debug;

    // Leveling//
    public double power;
    public int baseExp;
    public int maxExp;
    public int maxLevel;
    public int[] levels;
    // Experience//
    public int playerKillingExp = 0;
    public HashMap<CreatureType, Integer> creatureKillingExp = new HashMap<CreatureType, Integer>();
    public HashMap<Material, Integer> miningExp = new HashMap<Material, Integer>();
    public int loggingExp = 0;
    // Default//
    public String defClass;
    public int defLevel;
    // Properties//
    public boolean iConomy;
    public ChatColor cColor;
    public String prefix;
    public int swapcost;

    /**
     * Generate experience for the level ArrayList<Integer>
     */
    public void calcExp() {
        levels = new int[maxLevel];
        double A = 2 * (maxExp - baseExp) * Math.pow(maxLevel, (power * -1));
        for (int n = 0; n < maxLevel; n++) {
            levels[n] = (int) (A / 2 * Math.pow(n, power) + baseExp);
        }
    }

}
