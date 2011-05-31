package com.herocraftonline.dev.heroes;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;

/**
 * Checks for plugins whenever one is enabled
 */
public class HPluginListener extends ServerListener {
    private Heroes plugin;
    private Methods methods = null;

    public HPluginListener(Heroes instance) {
        this.plugin = instance;
        this.methods = new Methods("iConomy");
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        // First grab the Plugin.
        Plugin plugin = event.getPlugin(); // We'll check against the Plugins name, it's not fool proof but will do

        // Check if the name is iConomy.
        if (!this.methods.hasMethod()) {
            if (this.methods.setMethod(event.getPlugin())) {
                this.plugin.Method = (Methods) this.methods.getMethod();
                System.out.println("[Citizens]: Payment method found (" + methods.getMethod().getName() + " version: " + methods.getMethod().getVersion() + ")");
            }
        }

        // Check if the name is Permissions.
        if (plugin.getDescription().getName().equals("Permissions")) {
            // Check if we haven't already setup Permissions.
            if (Heroes.Permissions == null) {
                // Run the Permissions Setup.
                this.plugin.setupPermissions();
            }
        }

    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        // Check to see if the plugin thats being disabled is the one we are using
        if (this.methods != null && this.methods.hasMethod()) {
            Boolean check = this.methods.checkDisabled(event.getPlugin());
            if (check) {
                Heroes.getLog().info("[Heroes]: Payment method disabled.");
            }
        }
    }
}
