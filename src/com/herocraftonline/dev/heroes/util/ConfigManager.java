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
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.command.skill.Skill;

public class ConfigManager {
    protected Heroes plugin;
    protected File primaryConfigFile;
    protected File classConfigFile;
    protected File expConfigFile;
    protected File skillConfigFile;
    protected Properties properties = new Properties();

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
            generateSkills(skillConfig);

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
        properties.power = config.getDouble(root + "power", 1.03);
        properties.maxExp = config.getInt(root + "maxExperience", 90000);
        properties.maxLevel = config.getInt(root + "maxLevel", 20);
        properties.expLoss = config.getDouble(root + "expLoss", 0.95);
        properties.calcExp();
    }

    private void loadDefaultConfig(Configuration config) {
        String root = "default.";
        properties.defClass = config.getString(root + "class");
        properties.defLevel = config.getInt(root + "level", 1);
    }

    private void loadProperties(Configuration config) {
        String root = "properties.";
        properties.iConomy = config.getBoolean(root + "iConomy", false);
        properties.cColor = ChatColor.valueOf(config.getString(root + "color", "WHITE"));
        properties.swapCost = config.getInt(root + "swapcost", 0);
        properties.debug = config.getBoolean(root + "debug", false);
        properties.blockTrackingDuration = config.getInt(root + "block-tracking-duration", 10 * 60 * 1000);
        properties.maxTrackedBlocks = config.getInt(root + "max-tracked-blocks", 1000);
    }

    private void loadPersistence(Configuration config) {
        String root = "data.";
        properties.host = config.getString(root + "host", "localhost");
        properties.port = config.getString(root + "port", "3306");
        properties.database = config.getString(root + "database", "heroes");
        properties.username = config.getString(root + "username", "root");
        properties.password = config.getString(root + "password", "");
        properties.method = config.getString(root + "method", "sqlite");
    }

    private void loadExperience(Configuration config) {
        String root = "killing";
        for (String item : config.getKeys(root)) {
            try {
                int exp = config.getInt(root + "." + item, 0);
                if (item.equals("player")) {
                    properties.playerKillingExp = exp;
                } else {
                    CreatureType type = CreatureType.valueOf(item.toUpperCase());
                    properties.creatureKillingExp.put(type, exp);
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
                properties.miningExp.put(type, exp);
            } else {
                plugin.log(Level.WARNING, "Invalid material type (" + item + ") found in experience.yml.");
            }
        }

        root = "logging";
        for (String item : config.getKeys(root)) {
            int exp = config.getInt(root + "." + item, 0);
            Material type = Material.matchMaterial(item);

            if (type != null) {
                properties.loggingExp.put(type, exp);
            } else {
                plugin.log(Level.WARNING, "Invalid material type (" + item + ") found in experience.yml.");
            }
        }
    }

    private void loadSkills(Configuration config) {
        config.load();
        for (BaseCommand baseCommand : plugin.getCommandManager().getCommands()) {
            if (baseCommand instanceof Skill) {
                Skill skill = (Skill) baseCommand;
                ConfigurationNode node = config.getNode(skill.getName());
                if (node != null) {
                    skill.setConfig(node);
                } else {
                    skill.setConfig(Configuration.getEmptyNode());
                }
                skill.init();
            }
        }
    }

    private void generateSkills(Configuration config) {
        for (BaseCommand baseCommand : plugin.getCommandManager().getCommands()) {
            if (baseCommand instanceof Skill) {
                Skill skill = (Skill) baseCommand;
                ConfigurationNode node = config.getNode(skill.getName());
                if (node == null) {
                    addNodeToConfig(config, skill.getDefaultConfig(), skill.getName());
                }
            }

        }
        config.save();
        loadSkills(config);
    }

    private void addNodeToConfig(Configuration config, ConfigurationNode node, String path) {
        for (String key : node.getKeys(null)) {
            config.setProperty(path + "." + key, node.getProperty(key));
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
