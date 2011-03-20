package com.herocraftonline.dev.heroes.classes;

import java.util.HashSet;
import java.util.Set;

public class ClassManager {

    protected Set<HeroClass> classes;
    
    public ClassManager() {
        classes = new HashSet<HeroClass>();
    }
    
    public HeroClass getClass(String name) {
        for (HeroClass c : classes) {
            if (name.equalsIgnoreCase(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public boolean addClass(HeroClass c) {
        return classes.add(c);
    }
    
    public boolean removeClass(HeroClass c) {
        return classes.remove(c);
    }
    
    public Set<HeroClass> getClasses() {
        return classes;
    }
    
}
