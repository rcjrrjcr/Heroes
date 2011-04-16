package com.herocraftonline.dev.heroes;

import java.util.Set;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

import com.herocraftonline.dev.heroes.api.BlockBreakExperienceEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.ExperienceType;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class HBlockListener extends BlockListener {

    private final Heroes plugin;

    public HBlockListener(Heroes plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Get the Hero representing the player
        Hero hero = plugin.getHeroManager().getHero(player);
        // Get the player's class definition
        HeroClass playerClass = hero.getPlayerClass();
        // Get the sources of experience for the player's class
        Set<ExperienceType> expSources = playerClass.getExperienceSources();

        int exp = hero.getExperience();
        int addedExp = 0;

        if (expSources.contains(ExperienceType.MINING)) {
            if (plugin.getConfigManager().getProperties().miningExp.containsKey(block.getType())) {
                addedExp = plugin.getConfigManager().getProperties().miningExp.get(block.getType());
            }
        }

        if (expSources.contains(ExperienceType.LOGGING)) {
            if (plugin.getConfigManager().getProperties().loggingExp.containsKey(block.getType())) {
                addedExp = plugin.getConfigManager().getProperties().loggingExp.get(block.getType());
            }
        }

        BlockBreakExperienceEvent expEvent = new BlockBreakExperienceEvent(player, addedExp, block.getType());
        plugin.getServer().getPluginManager().callEvent(expEvent);
        if (!expEvent.isCancelled()) {
            addedExp = expEvent.getExp();

            if (addedExp != 0) {
                hero.setExperience(exp + addedExp);
                plugin.getMessager().send(player, "$1: $2 Exp (+$3)", playerClass.getName(), String.valueOf(exp), String.valueOf(addedExp));
            }
        }
    }

}
