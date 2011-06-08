package com.herocraftonline.dev.heroes.api;

import org.bukkit.event.Event;

import com.herocraftonline.dev.heroes.persistence.Hero;

@SuppressWarnings("serial")
public class LeveledEvent extends Event {

    protected Hero hero;

    public LeveledEvent(Hero hero, int from, int to) {
        super("LeveledEvent");
        this.hero = hero;
    }

    public final Hero getHero() {
        return hero;
    }
}
