package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveEffectSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroEffects;

public class SkillOne extends ActiveEffectSkill {

    public SkillOne(Heroes plugin) {
        super(plugin);
        name = "One";
        description = "Skill - one";
        usage = "/skill one";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill one");

        registerEvent(Type.PLAYER_MOVE, new SkillPlayerListener(), Priority.Normal);
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        hero.getEffects().putEffect(name, 10000.0);
        if(useText != null) notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), useText, hero.getPlayer().getName(), name);
        return true;
    }

    public class SkillPlayerListener extends PlayerListener {

        @Override
        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();
            Hero hero = plugin.getHeroManager().getHero(player);

            HeroEffects effects = hero.getEffects();
            if (effects.hasEffect(name)) {
                player.setVelocity(player.getLocation().getDirection().multiply(1.3).setY(0));
            }
        }

    }
}
