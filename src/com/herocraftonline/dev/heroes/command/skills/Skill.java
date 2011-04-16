package com.herocraftonline.dev.heroes.command.skills;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public abstract class Skill extends BaseCommand {

    protected HashMap<Material, Integer> cost = new HashMap<Material, Integer>();
    protected Material bind;

    public Skill(Heroes plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            use((Player) sender, args);
        }
    }

    public abstract void use(Player user, String[] args);

    public HashMap<Material, Integer> getCost() {
        return cost;
    }

    public Material getBind() {
        return bind;
    }

}
