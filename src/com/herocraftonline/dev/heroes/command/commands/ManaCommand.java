package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class ManaCommand extends BaseCommand {

    public ManaCommand(Heroes plugin) {
        super(plugin);
        name = "Mana";
        description = "Displays your current mana";
        usage = "/mana";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("mana");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            int mana = hero.getMana();
            player.sendMessage("§9Mana: §f" + mana + " " + createManaBar(mana));
        }
    }

    private String createManaBar(int mana) {
        String manaBar = "§c[§9";
        int progress = (int) (mana / 100.0 * 92);
        for (int i = 0; i < progress; i++) {
            manaBar += "|";
        }
        manaBar += "§4";
        for (int i = 0; i < 92 - progress; i++) {
            manaBar += "|";
        }
        manaBar += "§c]";
        return manaBar;
    }

}
