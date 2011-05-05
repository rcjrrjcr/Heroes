package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillGTeleport extends ActiveSkill {

    private String targetText = null;
    public SkillGTeleport(Heroes plugin) {
        super(plugin);
        name = "Group Teleport";
        description = "Skill - Group Teleport";
        usage = "/skill gteleport";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill gteleport");
    }
    @Override
    public void init() {
        super.init();
        targetText = config.getString("targettext");
        if(targetText != null) {
            targetText = targetText.replace("%hero%", "$1").replace("%skill%", "$2").replace("%target%","$3");
        }
    }
    @Override
    public boolean use(Hero hero, String[] args) {
        if (hero.getParty() != null && hero.getParty().getMembers().size() != 1) {
            Player player = hero.getPlayer();
            String heroName = player.getName();
            for (Player n : hero.getParty().getMembers()) {
                n.teleport(player);
                if(targetText != null) notifyNearbyPlayers(n.getLocation().toVector(), useText, heroName, name, n.getName());
            }
            if(useText != null) notifyNearbyPlayers(player.getLocation().toVector(), useText, heroName, name);
            return true;
        }
        return false;
    }
}
