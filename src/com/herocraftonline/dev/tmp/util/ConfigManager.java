package com.herocraftonline.dev.tmp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.util.config.Configuration;

import com.herocraftonline.dev.tmp.Heroes;

public class ConfigManager {
    protected Heroes plugin;
    protected File primaryConfigFile;

    public ConfigManager(Heroes plugin) {
        this.plugin = plugin;
        this.primaryConfigFile = new File(plugin.getDataFolder(), "config.yml");
    }

    public void reload() throws Exception {
        load();
    }

    public void load() throws Exception {
        checkForConfig();

        Configuration config = new Configuration(primaryConfigFile);
        config.load();
        loadLevelConfig(config);
    }

    private void checkForConfig() {
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

    public void loadLevelConfig(Configuration config) {
        String globals = "leveling.";
        Properties.power = config.getDouble(globals + "power", 1.03);
        Properties.baseExp = config.getInt(globals + "baseExperience", 100);
        Properties.maxExp = config.getInt(globals + "maxExperience", 90000);
        Properties.maxLevel = config.getInt(globals + "maxLevel", 99);
    }

}
