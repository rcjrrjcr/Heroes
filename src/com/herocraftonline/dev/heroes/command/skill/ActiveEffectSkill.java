package com.herocraftonline.dev.heroes.command.skill;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class ActiveEffectSkill extends ActiveSkill {

    protected String expireText = null;
    public ActiveEffectSkill(Heroes plugin) {
        super(plugin);
    }
    
    @Override
    public void init() {
        expireText = config.getString("expiretext", "%hero% lost %name%!");
        if(expireText != null) {
            expireText = expireText.replace("%hero%", "$1").replace("%skill%", "$2");
        }
    }
    
    public void onExpire(Hero hero) {
        if(expireText != null) notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), expireText, hero.getPlayer().getName(), name);
    }
}
