package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class ConfigReloadCommand extends BaseCommand {

    public ConfigReloadCommand(Heroes plugin) {
        super(plugin);
        name = "Reload";
        description = "Reloads the heroes config file";
        usage = "/hero admin reload";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("hero admin reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!Heroes.Permissions.has((Player) sender, "heroes.admin.reload")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to do this");
                return;
            }
            try {
                plugin.getConfigManager().reload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
