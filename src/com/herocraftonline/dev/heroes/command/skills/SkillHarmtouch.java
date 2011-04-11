package com.herocraftonline.dev.heroes.command.skills;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.command.BaseSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillHarmtouch extends BaseSkill {

    // TODO: Register this command in Heroes
    public SkillHarmtouch(Heroes plugin) {
        super(plugin);
        name = "Harmtouch";
        description = "Skill - Harmtouch";
        usage = "/harmtouch [player]";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("harmtouch");
        cooldown = 3000;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

            // Cooldown - This is just a mockup for it. Change it if you want.
            // Just trying this out for now.
            HashMap<String, Long> cooldowns = hero.getCooldowns();
            if (hero.getCooldowns().containsKey(name)) {
                if (cooldowns.get(name) - System.currentTimeMillis() >= cooldown) {
                    cooldowns.put(name, System.currentTimeMillis());
                } else {
                    plugin.getMessager().send(sender, "Sorry, that skill is still on cooldown!");
                    return;
                }
            }

            // Ability checker
            if (!(heroClass.getSpells().contains(Spells.HARMTOUCH))) {
                plugin.getMessager().send(sender, "Sorry, that ability isn't for your class!");
                return;
            }

            // Get target from line of sight or argument
            LivingEntity target;
            if (args.length == 0) {
                target = BaseSkill.getPlayerTarget(player, 15);
            } else {
                target = plugin.getServer().getPlayer(args[0]);
            }

            // Spell Stuff
            if (target != null) {
                double dx = player.getLocation().getX() - target.getLocation().getX();
                double dz = player.getLocation().getZ() - target.getLocation().getZ();
                double distance = Math.sqrt(dx * dx + dz * dz);
                if (distance < 15) {
                    target.setHealth((int) (target.getHealth() - (plugin.getConfigManager().getProperties().getLevel(hero.getExperience()) * 0.5)));
                } else {
                    plugin.getMessager().send(sender, "Sorry, that person isn't close enough!");
                }
            }
        }
    }

}
