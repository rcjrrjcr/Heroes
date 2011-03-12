package com.herocraftonline.dev.Heroes;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.dev.Heroes.persistance.player;

/**
 * Heroes Plugin for Herocraft
 *
 * @author Herocraft's Plugin Team
 */
@SuppressWarnings("unused")
public class Heroes extends JavaPlugin {
	private final Listener Listener = new Listener(this);
	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	public static final Logger Log = Logger.getLogger("Minecraft");
	public void onDisable() {
		System.out.println("Goodbye world!");
	}

	public void onEnable() {
		getServer().getPluginManager();
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		Properties.expCalc();
		System.out.println(player.getLevel(99999));
	}

	public boolean isDebugging(final Player player) {
		if (debugees.containsKey(player)) {
			return debugees.get(player);
		} else {
			return false;
		}
	}

	public void setDebugging(final Player player, final boolean value) {
		debugees.put(player, value);
	}

}