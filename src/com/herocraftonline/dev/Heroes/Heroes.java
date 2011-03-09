package com.herocraftonline.dev.Heroes;

import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * Heroes Plugin for Herocraft
 *
 * @author Herocraft's Plugin Team
 */
public class Heroes extends JavaPlugin {
    private final Listener playerListener = new Listener(this);
    private final Listener blockListener = new Listener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();


    public void onDisable() {
        System.out.println("Goodbye world!");
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
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
