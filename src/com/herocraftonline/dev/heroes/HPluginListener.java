package com.herocraftonline.dev.heroes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

/**
 * Checks for plugins whenever one is enabled
 */
public class HPluginListener extends ServerListener {
    private Heroes plugin;

    public HPluginListener(Heroes instance) {
        this.plugin = instance;
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        Plugin plugin = event.getPlugin();

        // Check if the name is iConomy.
        if (plugin.getDescription().getName().equals("iConomy")) {
            // Run the iConomy Setup.
            this.plugin.setupiConomy();
        }

        // Check if the name is Permissions.
        if (plugin.getDescription().getName().equals("Permissions")) {
            // Check if we haven't already setup Permissions.
            if (Heroes.Permissions == null) {
                // Run the Permissions Setup.
                this.plugin.setupPermissions();
            }
        }

        // Check if the name is BukkitContrib.
        if (plugin.getDescription().getName().equals("BukkitContrib")){
            Heroes.useBukkitContrib = true;
        }
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        Plugin plugin = event.getPlugin();
        // Check if the name is BukkitContrib.
        if (plugin.getDescription().getName().equals("BukkitContrib")){
            // If BukkitContrib just Disabled then we tell Heroes to stop using BukkitContrib
            Heroes.useBukkitContrib = false;
            // Then we swap all the Players NSH to our Custom NSH.
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                this.plugin.switchToHNSH(player);
            }
        }
    }
}
