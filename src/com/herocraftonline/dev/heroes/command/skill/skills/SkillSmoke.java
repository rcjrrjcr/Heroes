package com.herocraftonline.dev.heroes.command.skill.skills;

import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillSmoke extends Skill{
  //TODO: Cooldown

    public SkillSmoke(Heroes plugin) {
        super(plugin);
        name = "Smoke";
        description = "Skill - smoke";
        usage = "/smoke";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("smoke");
        configs.put("mana", "20");
        configs.put("level", "20");

        plugin.getServer().getPluginManager().registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal, plugin);
    }

    @Override
    public void use(Player player, String[] args) {
        CraftPlayer player1 = (CraftPlayer)player;
        player1.getHandle().netServerHandler.a(new Packet29DestroyEntity(player1.getEntityId()));
        plugin.getHeroManager().getHero(player1).getEffects().put(getName(), System.currentTimeMillis() + 10000);
    }

    public class SkillEntityListener extends EntityListener {

        @Override
        public void onEntityDamage(EntityDamageEvent event) {
            if(event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                Hero hero = plugin.getHeroManager().getHero(player);
                CraftPlayer player1 = (CraftPlayer)player;
                if (hero.getEffects().containsKey(getName())) {
                    player1.getHandle().netServerHandler.a(new Packet20NamedEntitySpawn());
                }
            }
        }
    }
}



