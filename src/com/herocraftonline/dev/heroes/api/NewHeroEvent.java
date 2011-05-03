package com.herocraftonline.dev.heroes.api;

import org.bukkit.event.Event;

import com.herocraftonline.dev.heroes.persistence.Hero;

@SuppressWarnings("serial")
public class NewHeroEvent extends Event {

    protected final Hero hero;

    public NewHeroEvent(Hero hero) {
        super("NewHeroEvent");
        this.hero = hero;
    }

    public final Hero getHero() {
        return hero;
    }
}
