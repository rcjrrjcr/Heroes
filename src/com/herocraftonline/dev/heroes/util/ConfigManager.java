package com.herocraftonline.dev.heroes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.util.config.Configuration;
import org.bukkit.ChatColor;

import com.herocraftonline.dev.heroes.Heroes;

public class ConfigManager {
    protected Heroes plugin;
    protected static File primaryConfigFile;

    public ConfigManager(Heroes plugin) {
        this.plugin = plugin;
        ConfigManager.primaryConfigFile = new File(plugin.getDataFolder(), "config.yml");
    }

    public void reload() throws Exception {
        load();
        Heroes.log.info("Reloaded Configuration");
    }

    public void load() throws Exception {
        checkForConfig();

        Configuration config = new Configuration(primaryConfigFile);
        config.load();
        loadLevelConfig(config);
        loadDefaultConfig(config);
        loadProperties(config);
    }

    private static void checkForConfig() {
        if (!primaryConfigFile.exists()) {
            try {
                primaryConfigFile.getParentFile().mkdir();
                primaryConfigFile.createNewFile();
                OutputStream output = new FileOutputStream(primaryConfigFile, false);
                InputStream input = ConfigManager.class.getResourceAsStream("config.yml");
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

    public static void loadLevelConfig(Configuration config) {
        String globals = "leveling.";
        Properties.power = config.getDouble(globals + "power", 1.03);
        Properties.baseExp = config.getInt(globals + "baseExperience", 100);
        Properties.maxExp = config.getInt(globals + "maxExperience", 90000);
        Properties.maxLevel = config.getInt(globals + "maxLevel", 99);
    }
    
    public static void loadDefaultConfig(Configuration config){
    	String globals = "default.";
    	Properties.defClass = config.getString(globals + "class");
    	Properties.defLevel = config.getInt(globals + "level", 1);
    }
    
    public static void loadProperties(Configuration config){
    	String globals = "properties.";
    	Properties.iConomy = config.getBoolean(globals + "iConomy", false);
    	Properties.cColor = ChatColor.valueOf(config.getString(globals + "color", "WHITE"));
    	Properties.swapcost = config.getInt(globals + "swapcost", 0);
    }
    
    

}
