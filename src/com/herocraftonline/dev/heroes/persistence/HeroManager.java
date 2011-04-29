package com.herocraftonline.dev.heroes.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.config.Configuration;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.api.HeroLoadEvent;
import com.herocraftonline.dev.heroes.api.NewHeroEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;

/**
 * Player management
 * 
 * @author Herocraft's Plugin Team
 */
public class HeroManager {

    private Heroes plugin;
    private Set<Hero> heroes;
    private File playerFolder;

    public HeroManager(Heroes plugin) {
        this.plugin = plugin;
        this.heroes = new HashSet<Hero>();
        playerFolder = new File(plugin.getDataFolder(), "players"); // Setup our Player Data Folder
        playerFolder.mkdirs(); // Create the folder if it do esn't exist.
    }

    /**
     * Load the given Players Data file.
     * 
     * @param player
     */
    public void loadHeroFile(Player player) {
        File playerFile = new File(playerFolder, player.getName() + ".yml"); // Setup our Players Data File.
        // Check if it already exists, if so we load the data.
        if (playerFile.exists()) {
            Configuration playerConfig = new Configuration(playerFile); // Setup the Configuration
            playerConfig.load(); // Load the Config File

            HeroClass playerClass;
            // Grab the Players Class.
            if (playerConfig.getString("class") != null) {
                playerClass = plugin.getClassManager().getClass(playerConfig.getString("class")); // Grab the Players Class from the File.
            } else {
                playerClass = plugin.getClassManager().getDefaultClass(); // If no Class saved then revert to the Default Class.
            }

            // Grab the Data we need.
            List<String> masteries = playerConfig.getStringList("masteries", new ArrayList<String>());
            int mana = playerConfig.getInt("mana", 0);
            int exp = playerConfig.getInt("experience", 0);

            // Lets sort out any items we need to recover.
            List<ItemStack> itemRecovery = new ArrayList<ItemStack>();
            List<String> itemKeys = playerConfig.getKeys("itemrecovery");
            if (itemKeys != null && itemKeys.size() > 0) {
                for (String item : itemKeys) {
                    try {
                        Short durability = Short.valueOf(playerConfig.getString("itemrecovery." + item, "0"));
                        Material type = Material.valueOf(item);
                        itemRecovery.add(new ItemStack(type, 1, durability));
                    } catch (IllegalArgumentException e) {
                        this.plugin.debugLog(Level.WARNING, "Either '" + item + "' doesn't exist or the durability is of an incorrect value!");
                        continue;
                    }
                }
            }

            // Create a New Hero
            Hero playerHero = new Hero(plugin, player, playerClass, exp, mana, masteries, itemRecovery);
            // Add the Hero to the Set.
            addHero(playerHero);
            plugin.log(Level.INFO, "Loaded hero: " + player.getName());
        } else {
            // Create a New Hero with the Default Setup.
            createNewHero(player);
            plugin.log(Level.INFO, "Created hero: " + player.getName());
        }
        plugin.getServer().getPluginManager().callEvent(new HeroLoadEvent(getHero(player)));
    }

    /**
     * Save the given Players Data to a file.
     * 
     * @param player
     */
    public void saveHeroFile(Player player) {
        File playerFile = new File(playerFolder, player.getName() + ".yml");
        Configuration playerConfig = new Configuration(playerFile);
        // Save the players stuff
        playerConfig.setProperty("class", getHero(player).getPlayerClass().toString());
        playerConfig.setProperty("experience", getHero(player).getExperience());
        playerConfig.setProperty("mana", getHero(player).getMana());
        playerConfig.setProperty("masteries", getHero(player).getMasteries());
        playerConfig.removeProperty("itemrecovery"); // Just a precaution, we'll remove any values before resaving the list.
        for(ItemStack item : getHero(player).getItems()){
            String durability = Short.toString(item.getDurability());
            playerConfig.setProperty("itemrecovery." + item, durability);
        }
        playerConfig.save();
        plugin.log(Level.INFO, "Saved hero: " + player.getName());
    }

    public boolean createNewHero(Player player) {
        Hero hero = new Hero(plugin, player, plugin.getClassManager().getDefaultClass(), 0, 0, new ArrayList<String>(), new ArrayList<ItemStack>());
        plugin.getServer().getPluginManager().callEvent(new NewHeroEvent(hero));
        return addHero(hero);
    }

    public boolean addHero(Hero hero) {
        return heroes.add(hero);
    }

    public boolean removeHero(Hero hero) {
        return heroes.remove(hero);
    }

    public boolean containsPlayer(Player player) {
        return getHero(player) != null;
    }

    public Hero getHero(Player player) {
        for (Hero hero : heroes) {
            if (hero.getPlayer() == null) {
                heroes.remove(hero); // Seeing as it's null we might as well remove it.
                continue;
            }
            if (player.getName().equalsIgnoreCase(hero.getPlayer().getName())) {
                return hero;
            }
        }
        // If it gets to this stage then clearly the HeroManager doesn't have it so we create it...
        loadHeroFile(player);
        for (Hero hero : heroes) {
            if (player.getName().equalsIgnoreCase(hero.getPlayer().getName())) {
                return hero;
            }
        }
        return null;
    }

    public Hero[] getHeroes() {
        return heroes.toArray(new Hero[0]);
    }
}
