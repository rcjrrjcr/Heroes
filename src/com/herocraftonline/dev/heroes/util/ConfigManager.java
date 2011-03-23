package com.herocraftonline.dev.heroes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.CreatureType;
import org.bukkit.util.config.Configuration;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.ClassManager;

public class ConfigManager {
    protected Heroes plugin;
    protected File primaryConfigFile;
    protected File classConfigFile;
    protected File expConfigFile;

    public ConfigManager(Heroes plugin) {
        this.plugin = plugin;
        this.primaryConfigFile = new File(plugin.getDataFolder(), "config.yml");
        this.classConfigFile = new File(plugin.getDataFolder(), "classes.yml");
        this.expConfigFile = new File(plugin.getDataFolder(), "experience.yml");
    }

    public void reload() throws Exception {
        load();
        plugin.log(Level.INFO, "Reloaded Configuration");
    }

    public void load() {
        try {
            checkForConfig(primaryConfigFile);
            checkForConfig(classConfigFile);
            checkForConfig(expConfigFile);

            Configuration primaryConfig = new Configuration(primaryConfigFile);
            primaryConfig.load();
            loadLevelConfig(primaryConfig);
            loadDefaultConfig(primaryConfig);
            loadProperties(primaryConfig);

            Configuration expConfig = new Configuration(expConfigFile);
            expConfig.load();
            loadExperience(expConfig);

            ClassManager classManager = new ClassManager(plugin);
            classManager.loadClasses(classConfigFile);
            plugin.setClassManager(new ClassManager(plugin));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForConfig(File config) {
        if (!config.exists()) {
            try {
                plugin.log(Level.WARNING, "File " + config.getName() + " not found - generating defaults.");
                config.getParentFile().mkdir();
                config.createNewFile();
                OutputStream output = new FileOutputStream(config, false);
                InputStream input = ConfigManager.class.getResourceAsStream(config.getName());
                byte[] buf = new byte[8192];
                while (true) {
                    int length = input.read(buf);
                    if (length < 0) {
                        break;
                    }
                    output.write(buf, 0, length);
                }
                input.close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLevelConfig(Configuration config) {
        String root = "leveling.";
        Properties.power = config.getDouble(root + "power", 1.03);
        Properties.baseExp = config.getInt(root + "baseExperience", 100);
        Properties.maxExp = config.getInt(root + "maxExperience", 90000);
        Properties.maxLevel = config.getInt(root + "maxLevel", 99);
    }

    private void loadDefaultConfig(Configuration config) {
        String root = "default.";
        Properties.defClass = config.getString(root + "class");
        Properties.defLevel = config.getInt(root + "level", 1);
    }

    private void loadProperties(Configuration config) {
        String root = "properties.";
        Properties.iConomy = config.getBoolean(root + "iConomy", false);
        Properties.cColor = ChatColor.valueOf(config.getString(root + "color", "WHITE"));
        Properties.swapcost = config.getInt(root + "swapcost", 0);
    }

    private void loadExperience(Configuration config) {
        String root = "killing.";
        for (String item : config.getKeys(root)) {
            try {
                int exp = config.getInt(root + item, 0);
                if (item.equals("player")) {
                    Properties.playerKillingExp = exp;
                } else {
                    CreatureType type = CreatureType.valueOf(item.toUpperCase());
                    Properties.creatureKillingExp.put(type, exp);
                }
            } catch (IllegalArgumentException e) {
                plugin.log(Level.WARNING, "Invalid creature type (" + item + ") found in experience.yml.");
            }
        }

        root = "mining.";
        for (String item : config.getKeys(root)) {
            int exp = config.getInt(root + item, 0);
            Material type = Material.matchMaterial(item);
            if (type != null) {
                Properties.miningExp.put(type, exp);
            } else {
                plugin.log(Level.WARNING, "Invalid material type (" + item + ") found in experience.yml.");
            }
        }
        
        root = "logging.";
        int exp = config.getInt(root + "log", 0);
        Properties.loggingExp = exp;
    }

}
