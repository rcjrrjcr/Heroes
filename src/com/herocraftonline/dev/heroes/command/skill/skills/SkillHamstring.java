package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class SkillHamstring extends TargettedSkill {

    private PlayerListener playerListener = new SkillPlayerListener();
    private Map<Integer, Long> hamstrungEntities = new HashMap<Integer, Long>();

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
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = super.getDefaultConfig();
        node.setProperty("duration", 6);
        node.setProperty("max-distance", 5);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        if (target == player) {
            Messaging.send(player, "You need a target!");
            return false;
        }

        EntityDamageByEntityEvent damageEntityEvent = new EntityDamageByEntityEvent(player, target, DamageCause.CUSTOM, 0);
        plugin.getServer().getPluginManager().callEvent(damageEntityEvent);
        if (damageEntityEvent.isCancelled()) {
            return false;
        }

        target.setVelocity(target.getVelocity().setX(0).setZ(0));
        int duration = config.getInt("duration", 5000);
        hamstrungEntities.put(target.getEntityId(), System.currentTimeMillis() + duration);

        String targetName = target instanceof Player ? ((Player) target).getName() : target.getClass().getSimpleName().substring(5);
        if (useText != null) {
            notifyNearbyPlayers(player.getLocation().toVector(), useText, player.getName(), name, target == player ? "himself" : targetName);
        }
        return true;
    }

    private boolean checkHamstrung(Entity entity) {
        if (entity == null) {
            return false;
        }
        int id = entity.getEntityId();
        if (hamstrungEntities.containsKey(id)) {
            if (hamstrungEntities.get(id) > System.currentTimeMillis()) {
                return true;
            } else {
                hamstrungEntities.remove(id);
                return false;
            }
        }
        return false;
    }

    public class SkillPlayerListener extends PlayerListener {

        @Override
        public void onPlayerMove(PlayerMoveEvent event) {
            if (checkHamstrung(event.getPlayer())) {
                event.getPlayer().setVelocity(event.getPlayer().getVelocity().setX(0).setZ(0));
            }
        }

    }

}
