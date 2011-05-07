package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class SkillHamstring extends TargettedSkill {

    private PlayerListener playerListener = new SkillPlayerListener();
    private int duration;

    public SkillHamstring(Heroes plugin) {
        super(plugin);
        name = "Hamstring";
        description = "Skill - Hamstring";
        usage = "/skill hamstring [player]";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("skill hamstring");

        registerEvent(Type.PLAYER_MOVE, playerListener, Priority.Highest);
    }

    @Override
    public void init() {
        super.init();
        maxDistance = config.getInt("max-distance", 5);
        duration = config.getInt("duration", 6000);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = super.getDefaultConfig();
        node.setProperty("duration", 6000);
        node.setProperty("max-distance", 5);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        System.out.println("hamstring");
        if (target == player || !(target instanceof Player)) {
            Messaging.send(player, "You need a target!");
            return false;
        }

        EntityDamageByEntityEvent damageEntityEvent = new EntityDamageByEntityEvent(player, target, DamageCause.CUSTOM, 0);
        plugin.getServer().getPluginManager().callEvent(damageEntityEvent);
        if (damageEntityEvent.isCancelled()) {
            return false;
        }

        Player targetPlayer = (Player) target;
        String targetName = targetPlayer.getName();
        Hero targetHero = plugin.getHeroManager().getHero(targetPlayer);
        targetHero.getEffects().putEffect(name, (double) duration);

        if (useText != null) {
            notifyNearbyPlayers(player.getLocation().toVector(), useText, player.getName(), name, target == player ? "himself" : targetName);
        }
        return true;
    }

    public class SkillPlayerListener extends PlayerListener {

        @Override
        public void onPlayerMove(PlayerMoveEvent event) {
            if (event.isCancelled()) {
                return;
            }

            Player player = event.getPlayer();
            Hero hero = plugin.getHeroManager().getHero(player);
            if (hero.getEffects().hasEffect(name)) {
                Vector from = event.getFrom().toVector();
                Vector to = event.getTo().toVector();
                Vector velocity = to.clone().subtract(from).normalize().multiply(0.1);
                player.setVelocity(velocity);
            }
        }

    }

}
