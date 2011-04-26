package com.herocraftonline.dev.heroes.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class RecoverItemsCommand extends BaseCommand {

    public RecoverItemsCommand(Heroes plugin) {
        super(plugin);
        name = "RecoverItems";
        description = "Recover removed items";
        usage = "/hero recoveritems";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("hero recoveritems");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Hero h = this.plugin.getHeroManager().getHero(p);

            List<String> items = h.getItems();
            List<String> newItems = new ArrayList<String>();

            if (!(items.size() > 0)) {
                this.plugin.getMessager().send(p, "You have no items to recover");
            }

            for (int i = 0; i < items.size(); i++) {
                int slot = this.plugin.firstEmpty(p);
                Material material = Material.valueOf(items.get(i));
                if (slot == -1) {
                    newItems.add(items.get(i));
                    continue;
                }
                p.getInventory().setItem(slot, new ItemStack(material, 1));
                this.plugin.getMessager().send(p, "Recovered Item $1 - $2", "#" + (i + 1), material.toString());
            }

            if (newItems.size() > 0) {
                this.plugin.getMessager().send(p, "You have $1 left to recover.", newItems.size() + " Items");
            }
            h.setItems(newItems);
        }
    }
}
