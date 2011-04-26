package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillRevive extends TargettedSkill {

    public SkillRevive(Heroes plugin) {
        super(plugin);
        name = "Revive";
        description = "Skill - Revive";
        usage = "/skill revive";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("skill revive");
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        if (!(target instanceof Player)) {
            player.sendMessage("You must target a player.");
            return false;
        }
        Player targetPlayer = (Player) target;

        Properties prop = plugin.getConfigManager().getProperties();
        if (prop.playerDeaths.containsKey(targetPlayer)) {
            Location loc = prop.playerDeaths.get(targetPlayer);
            double dx = player.getLocation().getX() - loc.getX();
            double dz = player.getLocation().getZ() - loc.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (hero.getParty().getMembers().contains(targetPlayer)) {
                if (distance < 50) {
                    if (targetPlayer.isDead()) {
                        player.sendMessage("That player is still dead");
                    } else {
                        targetPlayer.teleport(loc);
                    }
                }
            }
        }
        return true;
    }
}
