package com.herocraftonline.dev.Heroes;

import java.util.ArrayList;

public class Properties {

	public enum Class { 
		Warrior,
		Rogue,
		Mage,
		Healer,
		Diplomat,
		Crafter
	}
	public enum Warrior{
		Paladin,
		Dreadknight,
		Gladiator,
		Proxy
	}
	public enum Rogue{
		Ninja,
		Ranger,
		Thief,
		Treasure_Hunter
	}
	public enum Mage{
		Pyro,
		Icemage,
		Necromancer,
		Enchanter
	}
	public enum Healer{
		Cleric,
		Priest,
		Monk,
		Proxy,
	}
	public enum Diplomat{
		Ambassador,
		Noble,
		Lord,
		Duke,
		King
	}
	public enum Crafter{
		Architect,
		Smith,
		Farmer,
		Carpenter
	}

	public static double inflation = 1.03;
	public static double baseExp = 100;
	public static ArrayList<Integer> levelexp = new ArrayList<Integer>();

	public Properties(Heroes heroes) {
	}

	public static void expCalc(){
		levelexp.clear();
		double last = baseExp;
		for (int n = 1; n <= 99; n++) {
			levelexp.add((int) ((int) (last * n) * inflation) - 3);
			last = (baseExp * n) * inflation;
		}
	}
}
