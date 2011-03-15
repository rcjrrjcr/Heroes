package com.herocraftonline.dev.tmp.util;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.util.config.Configuration;

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
        Proxy
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
        Proxy,
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
        Carpenter
    }

    public static double power;
    public static int baseExp;
    public static int maxExp;
    public static int maxLevel;
    public static ArrayList<Integer> level = new ArrayList<Integer>();
    public static final File dataFolder = new File("plugins" + File.separator + "Heroes");
    public static final Configuration ConfigFile = new Configuration(dataFolder);

    public static void calcExp() {
        level.clear();
        double A = 2 * (maxExp - baseExp) * Math.pow(maxLevel, (power * -1));
        for (int n = 1; n <= maxLevel; n++) {
            level.add((int) (A / 2 * Math.pow(n - 1, power) + baseExp));
            System.out.println((int) (A / 2 * Math.pow(n - 1, power) + baseExp));
        }
    }
}
