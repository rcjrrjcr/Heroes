package com.herocraftonline.dev.heroes.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.CreatureType;

public class Properties {

	// Leveling//
	public double power;
	public int baseExp;
	public int maxExp;
	public int maxLevel;
	public ArrayList<Integer> level = new ArrayList<Integer>();
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
		level.clear();
		double A = 2 * (maxExp - baseExp) * Math.pow(maxLevel, (power * -1));
		for (int n = 1; n <= maxLevel; n++) {
			level.add((int) (A / 2 * Math.pow(n - 1, power) + baseExp));
		}
	}

}
