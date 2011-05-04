package com.herocraftonline.dev.heroes.command.skill;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.persistence.Hero;

public abstract class ActiveEffectSkill extends ActiveSkill {

    public ActiveEffectSkill(Heroes plugin) {
        super(plugin);
    }

    public void onExpire(Hero hero) {
        notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), "$1 loses $2!", hero.getPlayer().getName(), name);
    }
}
