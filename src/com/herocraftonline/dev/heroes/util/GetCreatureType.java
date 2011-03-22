package com.herocraftonline.dev.heroes.util;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;

public class GetCreatureType {

    public static CreatureType getCreatureType(Entity e) {
        String entity = e.toString();
        
        if(!(entity.contains("Craft"))){
            return null;
        } else {
            entity.replace("Craft", "");
        }
        
        return CreatureType.fromName(entity);
    }
}
