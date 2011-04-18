package com.herocraftonline.dev.heroes;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.command.skills.Skill;
import com.herocraftonline.dev.heroes.persistence.HeroManager;

public class HPlayerListener extends PlayerListener {
    private final Heroes plugin;

    public HPlayerListener(Heroes instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        HeroManager heroManager = plugin.getHeroManager();
        heroManager.loadHeroFile(player);
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HeroManager heroManager = plugin.getHeroManager();
        heroManager.saveHeroFile(player);
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (plugin.getHeroManager().getHero(p).getBinds().containsKey(event.getMaterial())) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                String[] args = plugin.getHeroManager().getHero(p).getBinds().get(event.getMaterial());
                for (BaseCommand baseCommand : plugin.getCommandManager().getCommands()) {
                    if (baseCommand instanceof Skill) {
                        Skill skillCommand = (Skill) baseCommand;
                        if (skillCommand.getName().equalsIgnoreCase(args[0])) {
                            skillCommand.use(p, Arrays.copyOf(args, 1));
                        }
                    }
                }
            }
        }
    }
}
