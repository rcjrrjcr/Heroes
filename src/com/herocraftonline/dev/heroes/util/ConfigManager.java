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
    protected File skillConfigFile;
    protected Properties propertiesFile = new Properties();

    public ConfigManager(Heroes plugin) {
        this.plugin = plugin;
        this.primaryConfigFile = new File(plugin.getDataFolder(), "config.yml");
        this.classConfigFile = new File(plugin.getDataFolder(), "classes.yml");
        this.expConfigFile = new File(plugin.getDataFolder(), "experience.yml");
        this.skillConfigFile = new File(plugin.getDataFolder(), "skills.yml");
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
            checkForConfig(skillConfigFile);

            Configuration primaryConfig = new Configuration(primaryConfigFile);
            primaryConfig.load();
            loadLevelConfig(primaryConfig);
            loadDefaultConfig(primaryConfig);
            loadProperties(primaryConfig);
            loadPersistence(primaryConfig);

            Configuration expConfig = new Configuration(expConfigFile);
            expConfig.load();
            loadExperience(expConfig);
            
            Configuration skillConfig = new Configuration(skillConfigFile);
            skillConfig.load();
            loadSkills(skillConfig);

            ClassManager classManager = new ClassManager(plugin);
            classManager.loadClasses(classConfigFile);
            plugin.setClassManager(classManager);
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
                InputStream input = ConfigManager.class.getResourceAsStream("/defaults/" + config.getName());
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
        plugin.getConfigManager().getProperties().power = config.getDouble(root + "power", 1.03);
        plugin.getConfigManager().getProperties().baseExp = config.getInt(root + "baseExperience", 100);
        plugin.getConfigManager().getProperties().maxExp = config.getInt(root + "maxExperience", 90000);
        plugin.getConfigManager().getProperties().maxLevel = config.getInt(root + "maxLevel", 99);
    }

    private void loadDefaultConfig(Configuration config) {
        String root = "default.";
        plugin.getConfigManager().getProperties().defClass = config.getString(root + "class");
        plugin.getConfigManager().getProperties().defLevel = config.getInt(root + "level", 1);
    }

    private void loadProperties(Configuration config) {
        String root = "properties.";
        plugin.getConfigManager().getProperties().iConomy = config.getBoolean(root + "iConomy", false);
        plugin.getConfigManager().getProperties().cColor = ChatColor.valueOf(config.getString(root + "color", "WHITE"));
        plugin.getConfigManager().getProperties().swapcost = config.getInt(root + "swapcost", 0);
        plugin.getConfigManager().getProperties().debug = config.getBoolean(root + "debug", false);
    }

    private void loadPersistence(Configuration config) {
        String root = "data.";
        plugin.getConfigManager().getProperties().host = config.getString(root + "host", "localhost");
        plugin.getConfigManager().getProperties().port = config.getString(root + "port", "3306");
        plugin.getConfigManager().getProperties().database = config.getString(root + "database", "heroes");
        plugin.getConfigManager().getProperties().username = config.getString(root + "username", "root");
        plugin.getConfigManager().getProperties().password = config.getString(root + "password", "");
        plugin.getConfigManager().getProperties().method = config.getString(root + "method", "sqlite");
    }

    private void loadExperience(Configuration config) {
        String root = "killing";
        for (String item : config.getKeys(root)) {
            try {
                int exp = config.getInt(root + "." + item, 0);
                if (item.equals("player")) {
                    plugin.getConfigManager().getProperties().playerKillingExp = exp;
                } else {
                    CreatureType type = CreatureType.valueOf(item.toUpperCase());
                    plugin.getConfigManager().getProperties().creatureKillingExp.put(type, exp);
                }
            } catch (IllegalArgumentException e) {
                plugin.log(Level.WARNING, "Invalid creature type (" + item + ") found in experience.yml.");
            }
        }

        root = "mining";
        for (String item : config.getKeys(root)) {
            int exp = config.getInt(root + "." + item, 0);
            Material type = Material.matchMaterial(item);

            if (type != null) {
                plugin.getConfigManager().getProperties().miningExp.put(type, exp);
            } else {
                plugin.log(Level.WARNING, "Invalid material type (" + item + ") found in experience.yml.");
            }
        }

        root = "logging";
        for (String item : config.getKeys(root)) {
            int exp = config.getInt(root + "." + item, 0);
            Material type = Material.matchMaterial(item);

            if (type != null) {
                plugin.getConfigManager().getProperties().loggingExp.put(type, exp);
            } else {
                plugin.log(Level.WARNING, "Invalid material type (" + item + ") found in experience.yml.");
            }
        }
    }
    
    private void loadSkills(Configuration config){
        getProperties().blackjackcooldown = config.getInt("blackjack.cooldown", 3000);
        getProperties().blackjackmana = config.getInt("blackjack.mana", 30);
        getProperties().bladegraspcooldown = config.getInt("bladegrasp.cooldown", 3000);
        getProperties().bladegraspmana = config.getInt("bladegrasp.mana", 30);
        getProperties().harmtouchcooldown = config.getInt("harmtouch.cooldown", 3000);
        getProperties().harmtouchmana = config.getInt("harmtouch.mana", 30);
        getProperties().layhandscooldown = config.getInt("layhands.cooldown", 3000);
        getProperties().layhandsmana = config.getInt("layhands.mana", 30);
        getProperties().summoncooldown = config.getInt("summon.cooldown", 3000);
        getProperties().summonmana = config.getInt("summon.mana", 30);
        getProperties().tamecooldown = config.getInt("tame.cooldown", 3000);
        getProperties().tamemana = config.getInt("tame.mana", 30);
        getProperties().trackcooldown = config.getInt("track.cooldown", 3000);
        getProperties().trackmana = config.getInt("track.mana", 30);
        getProperties().jumpcooldown = config.getInt("jump.cooldown", 3000);
        getProperties().jumpmana = config.getInt("jump.mana", 30);
        
    }

    public Properties getProperties() {
        return propertiesFile;
    }
}
