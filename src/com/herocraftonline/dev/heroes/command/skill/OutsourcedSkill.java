package com.herocraftonline.dev.heroes.command.skill;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.api.ClassChangeEvent;
import com.herocraftonline.dev.heroes.api.LeveledEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.nijiko.permissions.StorageReloadEvent;
import com.nijiko.permissions.User;
import com.nijiko.permissions.WorldConfigLoadEvent;

public class OutsourcedSkill extends Skill {

    protected String[] permissions;

    private final static Map<String, Map<String, Set<String>>> transientAdd = new HashMap<String, Map<String, Set<String>>>();
    private final static Map<String, Map<String, Set<String>>> transientRemove = new HashMap<String, Map<String, Set<String>>>();

    public OutsourcedSkill(Heroes plugin, String name, String[] permissions, String usage) {
        super(plugin);
        this.name = name;
        this.permissions = permissions;
        this.usage = usage;
        this.minArgs = 0;
        this.maxArgs = 0;
        this.description = usage;
        registerEvent(Type.CUSTOM_EVENT, new SkillCustomListener(), Priority.Normal);
    }

    public void tryLearningSkill(Hero hero) {
        tryLearningSkill(hero, hero.getHeroClass());
    }

    public void tryLearningSkill(Hero hero, HeroClass heroClass) {
        if (Heroes.Permissions == null) {
            return;
        }

        Player player = hero.getPlayer();
        String world = player.getWorld().getName();
        String playerName = player.getName();
        ConfigurationNode settings = heroClass.getSkillSettings(name);
        if (settings != null) {
            if (meetsLevelRequirement(hero, getSetting(heroClass, SETTING_LEVEL, 1))) {
                for (String permission : permissions) {
                    if (!Heroes.Permissions.has(player, permission)) {
                        addPermission(world, playerName, permission);
                    }
                }
            } else {
                for (String permission : permissions) {
                    if (Heroes.Permissions.has(player, permission)) {
                        removePermission(world, playerName, permission);
                    }
                }
            }
        } else {
            for (String permission : permissions) {
                if (Heroes.Permissions.has(player, permission)) {
                    removePermission(world, playerName, permission);
                }
            }
        }
    }

    private void removePermission(String world, String player, String permission) {
        try {
            Heroes.Permissions.removeUserPermission(world, player, permission);
            Heroes.Permissions.safeGetUser(world, player).removeTransientPermission(permission);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (transientRemove.get(world) == null)
            transientRemove.put(world, new HashMap<String, Set<String>>());
        if (transientRemove.get(world).get(player) == null)
            transientRemove.get(world).put(player, new HashSet<String>());
        transientRemove.get(world).get(player).add(permission);

        Map<String, Set<String>> worldAdd = transientAdd.get(world);
        if (worldAdd != null) {
            Set<String> addPerms = worldAdd.get(player);
            if (addPerms != null)
                addPerms.remove(permission);
        }
    }

    private void addPermission(String world, String player, String permission) {
        try {
            Heroes.Permissions.safeGetUser(world, player).addTransientPermission(permission);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (transientAdd.get(world) == null)
            transientAdd.put(world, new HashMap<String, Set<String>>());
        if (transientAdd.get(world).get(player) == null)
            transientAdd.get(world).put(player, new HashSet<String>());
        transientAdd.get(world).get(player).add(permission);

        Map<String, Set<String>> worldRem = transientRemove.get(world);
        if (worldRem != null) {
            Set<String> remPerms = worldRem.get(player);
            if (remPerms != null)
                remPerms.remove(permission);
        }
    }

    public class SkillCustomListener extends CustomEventListener {
        @Override
        public void onCustomEvent(Event event) {
            if (event instanceof ClassChangeEvent) {
                ClassChangeEvent subEvent = (ClassChangeEvent) event;
                tryLearningSkill(subEvent.getHero(), subEvent.getTo());
            } else if (event instanceof LeveledEvent) {
                LeveledEvent subEvent = (LeveledEvent) event;
                tryLearningSkill(subEvent.getHero());
            } else if (event instanceof WorldConfigLoadEvent) {
                WorldConfigLoadEvent subEvent = (WorldConfigLoadEvent) event;
                String world = subEvent.getWorld();
                reloadTransientPerms(world);
            } else if (event instanceof StorageReloadEvent) {
                Set<String> worlds = new HashSet<String>(transientAdd.keySet());
                worlds.addAll(transientRemove.keySet());
                for (String world : worlds) {
                    reloadTransientPerms(world);
                }
            }
        }
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    private void reloadTransientPerms(String world) {
        Map<String, Set<String>> worldAdd = transientAdd.get(world);
        Map<String, Set<String>> worldRemove = transientRemove.get(world);

        if (worldAdd != null) {
            for (Map.Entry<String, Set<String>> entry : worldAdd.entrySet()) {
                try {
                    User u = Heroes.Permissions.safeGetUser(world, entry.getKey());
                    for (String node : entry.getValue()) {
                        u.addTransientPermission(node);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

        if (worldRemove != null) {
            for (Map.Entry<String, Set<String>> entry : worldRemove.entrySet()) {
                try {
                    User u = Heroes.Permissions.safeGetUser(world, entry.getKey());
                    for (String node : entry.getValue()) {
                        u.removeTransientPermission(node);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

}
