package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class LevelInformationCommand extends BaseCommand {

    public LevelInformationCommand(Heroes plugin) {
        super(plugin);
        name = "LevelInformation";
        description = "Player Level information";
        usage = "/hlevel";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("heroes level");
        identifiers.add("hlevel");
        identifiers.add("level");
        identifiers.add("lvl");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            Properties prop = this.plugin.getConfigManager().getProperties();
            int exp = hero.getExperience();
            int level = prop.getLevel(exp);
            int current = prop.getExperience(level);
            int next = prop.getExperience(level + 1);

            sender.sendMessage("§c-----[ " + "§fYour Level Information§c ]-----");

            sender.sendMessage("  §aClass : " + hero.getPlayerClass().getName());
            sender.sendMessage("  §aLevel : " + level);
            sender.sendMessage("  §aExp : " + exp);
            sender.sendMessage("  §aNext Level : " + (level + 1));
            sender.sendMessage("  §aExp to Go: " + (next - exp));
            sender.sendMessage(createExperienceBar(exp, current, next));
            sender.sendMessage(createManaBar(hero.getMana()));
        }
    }
    
    private String createExperienceBar(int exp, int currentLevelExp, int nextLevelExp) {
        String expBar = "[§2";
        int progress = (int) ((double) (exp - currentLevelExp) / (nextLevelExp - currentLevelExp) * 92);
        for (int i = 0; i < progress; i++) {
            expBar += "|";
        }
        expBar += "§4";
        for (int i = 0; i < 92 - progress; i++) {
            expBar += "|";
        }
        expBar += "§f]";
        return expBar;
    }
    
    private String createManaBar(int mana) {
        String manaBar = "[§9";
        int progress = (int) (mana / 100.0 * 92);
        for (int i = 0; i < progress; i++) {
            manaBar += "|";
        }
        manaBar += "§4";
        for (int i = 0; i < 92 - progress; i++) {
            manaBar += "|";
        }
        manaBar += "§f]";
        return manaBar;
    }
}
