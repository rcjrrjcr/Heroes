package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillSyphon extends TargettedSkill {

    private double hpMult;
    private int def;
    private final static int maxHealth = 20; // Max health(200) or full health(20)??

    public SkillSyphon(Heroes plugin) {
        super(plugin);
        name = "Syphon";
        description = "Skill - Syphon";
        usage = "/skill syphon";
        minArgs = 1;
        maxArgs = 2;
        identifiers.add("skill syphon");
    }

    @Override
    public void init() {
        maxDistance = config.getInt("max-distance", 15);
        hpMult = config.getDouble("multiplier", 1d);
        def = config.getInt("default-health", 4);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("max-distance", 15);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();

        int transferredHealth = def;
        if (args.length != 1) {
            try {
                transferredHealth = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("Sorry, that's an incorrect health value!");
                return false;
            }
        }
        int playerHealth = player.getHealth();
        transferredHealth = playerHealth < transferredHealth ? playerHealth : transferredHealth > maxHealth ? maxHealth : transferredHealth;
        player.setHealth(playerHealth - transferredHealth);
        int targetHealth = target.getHealth();
        transferredHealth *= hpMult;
        transferredHealth = maxHealth - targetHealth < transferredHealth ? maxHealth - targetHealth : transferredHealth < 0 ? 0 : transferredHealth;
        target.setHealth(targetHealth + transferredHealth);

        String targetName = target instanceof Player ? ((Player) target).getName() : target.getClass().getSimpleName().substring(5);
        if(useText != null) notifyNearbyPlayers(player.getLocation().toVector(), useText, player.getName(), name, target == player ? "himself" : targetName);
        return true;
    }

}
