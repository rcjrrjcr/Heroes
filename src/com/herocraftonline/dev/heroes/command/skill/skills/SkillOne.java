package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillOne extends ActiveSkill {

    public SkillOne(Heroes plugin) {
        super(plugin);
        name = "One";
        description = "Skill - one";
        usage = "/one";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("one");

        registerEvent(Type.PLAYER_MOVE, new SkillPlayerListener(), Priority.Normal);
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        hero.getEffects().put(name, System.currentTimeMillis() + 10000.0);
        return true;
    }

    public class SkillPlayerListener extends PlayerListener {

        @Override
        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();
            Hero hero = plugin.getHeroManager().getHero(player);

            Map<String, Double> effects = hero.getEffects();
            if (effects.containsKey(name)) {
                if (effects.get(name) > System.currentTimeMillis()) {
                    player.setVelocity(player.getLocation().getDirection().multiply(1.3).setY(0));
                } else {
                    effects.remove(name);
                }
            }
        }

    }
}
