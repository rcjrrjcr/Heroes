package com.herocraftonline.dev.heroes.command.skill;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public abstract class Skill extends BaseCommand {

    protected HashMap<Material, Integer> cost = new HashMap<Material, Integer>();
    protected Material bind;
    protected HashMap<String, String> configs = new HashMap<String, String>();

    public Skill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            use((Player) sender, args);
        }
    }

    public abstract void use(Player player, String[] args);

    public HashMap<Material, Integer> getCost() {
        return cost;
    }

    public Material getBind() {
        return bind;
    }

    public HashMap<String, String> getConfig() {
        return configs;
    }

}
