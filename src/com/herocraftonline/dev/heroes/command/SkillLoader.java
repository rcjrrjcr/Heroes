package com.herocraftonline.dev.heroes.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skills.Skill;

public class SkillLoader extends URLClassLoader {
    protected Heroes plugin;
    protected URL url;

    public SkillLoader(URL path, Heroes plugin) throws Exception {
        super(new URL[] { path });
        this.url = path;
        this.plugin = plugin;
    }

    public Skill loadSkill(File file) throws Exception {
        try {
            JarFile jarFile = null;
            jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();

            String mainClass = null;
            while (entries.hasMoreElements()) {
                JarEntry element = entries.nextElement();
                if (element.getName().equalsIgnoreCase("skill.info")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
                    mainClass = reader.readLine().substring(12);
                    break;
                }
            }
            
            if (mainClass != null) {
                plugin.log(Level.INFO, "main-class: " + mainClass);
                ClassLoader loader = URLClassLoader.newInstance(new URL[] {file.toURI().toURL() }, getClass().getClassLoader());
                Class<?> clazz = Class.forName(mainClass, true, loader);
                Class<? extends Skill> skillClass = clazz.asSubclass(Skill.class);
                Constructor<? extends Skill> ctor = skillClass.getConstructor(Heroes.class);
                return ctor.newInstance(plugin);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            plugin.log(Level.INFO, "The skill " + file.getName() + " failed to load");
            e.printStackTrace();
            return null;
        }
    }

    /*
     * ClassLoader loader = URLClassLoader.newInstance( new URL[] { yourURL },
     * getClass().getClassLoader() ); Class<?> clazz =
     * Class.forName("mypackage.MyClass", true, loader); Class<? extends
     * Runnable> runClass = clazz.asSubclass(Runnable.class); // Avoid
     * Class.newInstance, for it is evil. Constructor<? extends Runnable> ctor =
     * runClass.getConstructor(); Runnable doRun = ctor.newInstance();
     * doRun.run();
     */
}
