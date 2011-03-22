package com.herocraftonline.dev.heroes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.ClassManager;

public class ConfigManager {
	protected Heroes plugin;
	protected File primaryConfigFile;
	protected File classConfigFile;

	public ConfigManager(Heroes plugin) {
		this.plugin = plugin;
		this.primaryConfigFile = new File(plugin.getDataFolder(), "config.yml");
		this.classConfigFile = new File(plugin.getDataFolder(), "classes.yml");
	}


	public void reload() throws Exception {
		load();
	    plugin.log(Level.INFO, "Reloaded Configuration");
	}

	public void load() {
		try{
		    checkForConfig(primaryConfigFile);
		    checkForConfig(classConfigFile);

			Configuration config = new Configuration(primaryConfigFile);
			config.load();
			loadLevelConfig(config);
			loadDefaultConfig(config);
			loadProperties(config);
			
			ClassManager classManager = new ClassManager(plugin);
			classManager.loadClasses(classConfigFile);
			plugin.setClassManager(new ClassManager(plugin));
		}catch(Exception e){
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

	public static void loadLevelConfig(Configuration config) {
		String globals = "leveling.";
		Properties.power = config.getDouble(globals + "power", 1.03);
		Properties.baseExp = config.getInt(globals + "baseExperience", 100);
		Properties.maxExp = config.getInt(globals + "maxExperience", 90000);
		Properties.maxLevel = config.getInt(globals + "maxLevel", 99);
	}

	public static void loadDefaultConfig(Configuration config) {
		String globals = "default.";
		Properties.defClass = config.getString(globals + "class");
		Properties.defLevel = config.getInt(globals + "level", 1);
	}

	public static void loadProperties(Configuration config) {
		String globals = "properties.";
		Properties.iConomy = config.getBoolean(globals + "iConomy", false);
		Properties.cColor = ChatColor.valueOf(config.getString(globals + "color", "WHITE"));
		Properties.swapcost = config.getInt(globals + "swapcost", 0);
	}

}
