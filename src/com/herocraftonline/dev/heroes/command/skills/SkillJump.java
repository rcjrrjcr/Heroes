package com.herocraftonline.dev.heroes.command.skills;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillJump extends BaseCommand {

    // TODO: Register this command in Heroes
    public SkillJump(Heroes plugin) {
        super(plugin);
        name = "Jump";
        description = "Skill - Jump";
        usage = "/jump";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("jump");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = hero.getPlayerClass();

            Properties properties = plugin.getConfigManager().getProperties();
            HashMap<String, Long> cooldowns = hero.getCooldowns();
            if (cooldowns.containsKey(getName())) {
                if (cooldowns.get(getName()) - System.currentTimeMillis() >= properties.jumpcooldown) {
                    cooldowns.put(getName(), System.currentTimeMillis());
                } else {
                    plugin.getMessager().send(sender, "Sorry, that skill is still on cooldown!");
                    return;
                }
            }

            if (!(heroClass.getSpells().contains(Spells.JUMP))) {
                plugin.getMessager().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }

            hero.getPlayer().setVelocity(hero.getPlayer().getVelocity().setY((hero.getPlayer().getVelocity().getY() * 2)));
        }
    }
}
