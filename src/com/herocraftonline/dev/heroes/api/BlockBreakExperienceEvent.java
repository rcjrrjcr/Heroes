package com.herocraftonline.dev.heroes.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class BlockBreakExperienceEvent extends ExperienceGainEvent {
    
    protected Material blockType;

    public BlockBreakExperienceEvent(Player player, int exp, Material blockType) {
        super(player, exp);
        this.blockType = blockType;
    }

    public Material getBlockType() {
        return blockType;
    }

}
