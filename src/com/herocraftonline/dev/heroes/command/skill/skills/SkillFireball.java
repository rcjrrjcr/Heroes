package com.herocraftonline.dev.heroes.command.skill.skills;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntitySnowball;
import net.minecraft.server.MathHelper;

import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillFireball extends ActiveSkill {

    private int damage;

    public SkillFireball(Heroes plugin) {
        super(plugin);
        name = "Fireball";
        description = "Skill - Fireball";
        usage = "/skill fireball [player]";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("skill fireball");
    }

    @Override
    public void init() {
        super.init();
        damage = config.getInt("damage", 10);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = super.getDefaultConfig();
        node.setProperty("damage", 10);
        return node;
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        Player player = hero.getPlayer();
        Location location = player.getEyeLocation();

        float pitch = location.getPitch() / 180.0F * 3.1415927F;
        float yaw = location.getYaw() / 180.0F * 3.1415927F;
        float f = 0.4f;
        double motX = (double) (-MathHelper.sin(yaw) * MathHelper.cos(pitch) * f);
        double motZ = (double) (MathHelper.cos(yaw) * MathHelper.cos(pitch) * f);
        double motY = (double) (-MathHelper.sin(pitch / 180.0F * 3.1415927F) * f);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer playerEntity = craftPlayer.getHandle();
        EntitySnowball snowball = new EntitySnowball(playerEntity.world, location.getX() + motX * 3, location.getY() + motY * 3, location.getZ() + motZ * 3);
        ((CraftWorld) player.getWorld()).getHandle().addEntity(snowball);
        snowball.a(motX, motY, motZ, 2F, 1.0F);
        snowball.fireTicks = 1000;

        if (useText != null) {
            notifyNearbyPlayers(location.toVector(), useText, hero.getPlayer().getName(), name);
        }
        return true;
    }

}
