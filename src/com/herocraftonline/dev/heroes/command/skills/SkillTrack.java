package com.herocraftonline.dev.heroes.command.skills;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillTrack extends Skill {

    // TODO: Register this command in Heroes
    public SkillTrack(Heroes plugin) {
        super(plugin);
        name = "Track";
        description = "Skill - Track";
        usage = "/track <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("track");
    }

    @Override
    public void use(Player player, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

        if (!(heroClass.getSpells().contains(Spells.TRACK))) {
            plugin.getMessager().send(player, "Sorry, that ability isn't for your class!");
            return;
        }

        Properties properties = plugin.getConfigManager().getProperties();
        HashMap<String, Long> cooldowns = hero.getCooldowns();
        if (cooldowns.containsKey(getName())) {
            if (cooldowns.get(getName()) - System.currentTimeMillis() >= properties.trackcooldown) {
                cooldowns.put(getName(), System.currentTimeMillis());
            } else {
                plugin.getMessager().send(player, "Sorry, that skill is still on cooldown!");
                return;
            }
        }

        if (plugin.getServer().getPlayer(args[0]) != null) {
            Player target = plugin.getServer().getPlayer(args[0]);
            Location location = target.getLocation();
            player.sendMessage(location.toString());
            player.setCompassTarget(location);
        }
    }
}
