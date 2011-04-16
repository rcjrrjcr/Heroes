package com.herocraftonline.dev.heroes.command;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import org.bukkit.util.config.Configuration;

import com.herocraftonline.dev.heroes.Heroes;

public class SkillLoader extends URLClassLoader{
    protected Heroes plugin;
    protected URL url;

    public SkillLoader(URL path, Heroes plugin) throws Exception {
        super(new URL[] { path });
        this.url = path;
        this.plugin = plugin;
    }

    @SuppressWarnings({ "unused", "static-access" })
    public void loadSkill(File file) throws Exception{
        JarFile jarFile = null;
        URLClassLoader clazzLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
        Class c = null;
        jarFile = new JarFile(file);
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            plugin.log(Level.INFO, "A");
            JarEntry element = entries.nextElement();
            if (element.getName().equalsIgnoreCase("skill.yml")) {
                plugin.log(Level.INFO, "B");
                File n = new File(clazzLoader.loadClass(file.getName()).getClassLoader().getResource("skill.yml").getPath());
                Configuration config = new Configuration(n);
                config.getString("main");
                if(config.getString("main").equalsIgnoreCase("")){
                    c = clazzLoader.loadClass(file.getName()).forName(config.getString("main"));
                }else{
                    plugin.log(Level.INFO, "A");
                    plugin.log(Level.INFO, "The skill " + file.getName() + " failed to load");
                    return;
                }
            }
        }
    }


}
