package com.herocraftonline.dev.heroes.command.skill;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class ActiveEffectSkill extends ActiveSkill {

    protected String expiryText = null;

    public ActiveEffectSkill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        super.init();
        expiryText = config.getString("expire-text", "%hero% lost %skill%!");
        if (expiryText != null) {
            expiryText = expiryText.replace("%hero%", "$1").replace("%skill%", "$2");
        }
    }

    public void onExpire(Hero hero) {
        if (expiryText != null) {
            notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), expiryText, hero.getPlayer().getName(), name);
        }
    }
}
