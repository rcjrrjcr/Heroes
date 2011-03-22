package com.herocraftonline.dev.heroes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.herocraftonline.dev.heroes.Heroes;

public class Updater {
    public static Heroes plugin;

    public static void updateLatest() {
        URL url;
        try {
            url = new URL("LINK TO BE DECIDED");
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream("plugins" + File.pathSeparator + "Heroes.jar");
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);

            PluginManager pm = plugin.getServer().getPluginManager();
            Plugin plugin = pm.getPlugin("Heroes");
            pm.disablePlugin(plugin);
            reload(new File("plugins" + File.pathSeparator + "Heroes.jar"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reload(File file) {
        try {
            PluginManager pm = plugin.getServer().getPluginManager();
            pm.loadPlugin(file);
        } catch (Throwable ex) {
            plugin.log(Level.WARNING, "Could not load plugin");
        }
    }

}
