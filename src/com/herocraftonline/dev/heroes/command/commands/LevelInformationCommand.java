package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;

public class LevelInformationCommand extends BaseCommand {

    public LevelInformationCommand(Heroes plugin) {
        super(plugin);
        name = "LevelInformation";
        description = "Player Level information";
        usage = "/lvl OR /level OR /hero level";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("hero level");
        identifiers.add("level");
        identifiers.add("lvl");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            Properties prop = this.plugin.getConfigManager().getProperties();
            int exp = hero.getExp();
            int level = prop.getLevel(exp);
            int current = prop.getExperience(level);

            sender.sendMessage("§c-----[ " + "§fYour Level Information§c ]-----");
            sender.sendMessage("  §aClass : " + hero.getHeroClass().getName());
            sender.sendMessage("  §aLevel : " + level);
            sender.sendMessage("  §aExp : " + exp);
            if (level != prop.maxLevel) {
                int next = prop.getExperience(level + 1);
                sender.sendMessage("  §aNext Level : " + (level + 1));
                sender.sendMessage("  §aExp to Go: " + (next - exp));
                sender.sendMessage(createExperienceBar(exp, current, next));
            } else {
                sender.sendMessage("  §aMASTERED!");
            }
            sender.sendMessage(Messaging.createManaBar(hero.getMana()));
        }
    }

    private String createExperienceBar(int exp, int currentLevelExp, int nextLevelExp) {
        String expBar = "§c[§2";
        int progress = (int) ((double) (exp - currentLevelExp) / (nextLevelExp - currentLevelExp) * 92);
        for (int i = 0; i < progress; i++) {
            expBar += "|";
        }
        expBar += "§4";
        for (int i = 0; i < 92 - progress; i++) {
            expBar += "|";
        }
        expBar += "§c]";
        return expBar;
    }

}
