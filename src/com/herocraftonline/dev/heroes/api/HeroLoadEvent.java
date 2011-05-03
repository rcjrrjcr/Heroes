package com.herocraftonline.dev.heroes.api;

import org.bukkit.event.Event;

import com.herocraftonline.dev.heroes.persistence.Hero;

@SuppressWarnings("serial")
public class HeroLoadEvent extends Event {

    protected final Hero hero;

    public HeroLoadEvent(Hero hero) {
        super("HeroLoadEvent");
        this.hero = hero;
    }

    public final Hero getHero() {
        return hero;
    }

}
