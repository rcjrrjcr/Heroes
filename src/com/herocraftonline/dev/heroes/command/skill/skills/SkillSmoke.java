package com.herocraftonline.dev.heroes.command.skill.skills;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveEffectSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillSmoke extends ActiveEffectSkill {

    public SkillSmoke(Heroes plugin) {
        super(plugin);
        name = "Smoke";
        description = "Skill - smoke";
        usage = "/skill smoke";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill smoke");

        registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal);
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        CraftPlayer craftPlayer = (CraftPlayer) hero.getPlayer();
        // Tell all the logged in Clients to Destroy the Entity - Appears Invisible.
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            CraftPlayer hostilePlayer = (CraftPlayer) player;
            hostilePlayer.getHandle().netServerHandler.sendPacket(new Packet29DestroyEntity(craftPlayer.getEntityId()));
        }
        hero.getEffects().putEffect(getName(), 10000.0);
        if(useText != null) notifyNearbyPlayers(craftPlayer.getLocation().toVector(), useText, craftPlayer.getName(), name); //Kinda ruins the stealthy part, but can be set to null to disable it
        return true;
    }

    public class SkillEntityListener extends EntityListener {
        @Override
        public void onEntityDamage(EntityDamageEvent event) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                Hero hero = plugin.getHeroManager().getHero(player);
                if (hero.getEffects().hasEffect(getName())) {
                    hero.getEffects().expireEffect(name);
                }
            }
        }
    }

    @Override
    public void onExpire(Hero hero) {
        Player player = hero.getPlayer();
        EntityHuman entity = ((CraftPlayer) player).getHandle();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(player.getName())) {
                continue;
            }
            CraftPlayer hostilePlayer = (CraftPlayer) p;
            hostilePlayer.getHandle().netServerHandler.sendPacket(new Packet20NamedEntitySpawn(entity));
        }
        super.onExpire(hero);
    }
}
