package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.List;

import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Vec3D;

import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillFireball extends Skill{
  //TODO: Cooldown

    public SkillFireball(Heroes plugin) {
        super(plugin);
        name = "Fireball";
        description = "Skill - Fireball";
        usage = "/fireball";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("fireball");
        configs.put("mana", "20");
        configs.put("level", "20");
        configs.put("cooldown", "20");

    }
    @Override
    public void use(Player player, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = hero.getPlayerClass();
        Properties p = plugin.getConfigManager().getProperties();

        if (!heroClass.getSkills().contains(getName())) {
            if (!(p.getLevel(hero.getExperience()) > Integer.parseInt(p.skillInfo.get(getName() + "level")))) {
                if (!(hero.getMana() > Integer.parseInt(p.skillInfo.get(getName() + "mana")))) {
                    return;
                }
            }
        }
        
        List<org.bukkit.block.Block> target = player.getLineOfSight(null, 600);
        Location playerLoc = player.getLocation();
        double dx = target.get(0).getX() - playerLoc.getX();
        double height = 1;
        double dy = (target.get(0).getY() + (double)(height / 2.0F)) - (playerLoc.getY() + (double)(height / 2.0F));
        double dz = target.get(0).getZ() - playerLoc.getZ(); 
        
        CraftPlayer craftPlayer = (CraftPlayer)player;
        EntityLiving playerEntity = craftPlayer.getHandle();
        EntityFireball fireball = new EntityFireball(((CraftWorld)player.getWorld()).getHandle(), playerEntity, dx, dy, dz);
        
        double d8 = 4D;
        Vec3D vec3d = getLocation(player, 1.0F);
        fireball.locX = playerLoc.getX() + vec3d.a * d8;
        fireball.locY = playerLoc.getY() + (double)(height / 2.0F) + 0.5D;
        fireball.locZ = playerLoc.getZ() + vec3d.c * d8;
        
        ((CraftWorld)player.getWorld()).getHandle().a(fireball);
        
        hero.getCooldowns().put(getName(), System.currentTimeMillis() + Long.parseLong(p.skillInfo.get(getName() + "cooldown")));
    }
    
    public Vec3D getLocation(Player player, float f)
    {
        Location playerLoc = player.getLocation();
        float rotationYaw = playerLoc.getYaw();
        float rotationPitch = playerLoc.getPitch();
        float prevRotationYaw = playerLoc.getYaw();
        float prevRotationPitch = playerLoc.getPitch();
        if(f == 1.0F)
        {
            float f1 = MathHelper.cos(-rotationYaw * 0.01745329F - 3.141593F);
            float f3 = MathHelper.sin(-rotationYaw * 0.01745329F - 3.141593F);
            float f5 = -MathHelper.cos(-rotationPitch * 0.01745329F);
            float f7 = MathHelper.sin(-rotationPitch * 0.01745329F);
            return Vec3D.create(f3 * f5, f7, f1 * f5);
        } else
        {
            float f2 = prevRotationPitch + (rotationPitch - prevRotationPitch) * f;
            float f4 = prevRotationYaw + (rotationYaw - prevRotationYaw) * f;
            float f6 = MathHelper.cos(-f4 * 0.01745329F - 3.141593F);
            float f8 = MathHelper.sin(-f4 * 0.01745329F - 3.141593F);
            float f9 = -MathHelper.cos(-f2 * 0.01745329F);
            float f10 = MathHelper.sin(-f2 * 0.01745329F);
            return Vec3D.create(f8 * f9, f10, f6 * f9);
        }
    }
}
