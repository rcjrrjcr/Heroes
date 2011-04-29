package com.herocraftonline.dev.heroes;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.WeaponItems;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.command.CommandManager;
import com.herocraftonline.dev.heroes.command.SkillLoader;
import com.herocraftonline.dev.heroes.command.commands.AssignSkillCommand;
import com.herocraftonline.dev.heroes.command.commands.ConfigReloadCommand;
import com.herocraftonline.dev.heroes.command.commands.HelpCommand;
import com.herocraftonline.dev.heroes.command.commands.LevelInformationCommand;
import com.herocraftonline.dev.heroes.command.commands.ManaCommand;
import com.herocraftonline.dev.heroes.command.commands.PartyAcceptCommand;
import com.herocraftonline.dev.heroes.command.commands.PartyChatCommand;
import com.herocraftonline.dev.heroes.command.commands.PartyCreateCommand;
import com.herocraftonline.dev.heroes.command.commands.PartyInviteCommand;
import com.herocraftonline.dev.heroes.command.commands.RecoverItemsCommand;
import com.herocraftonline.dev.heroes.command.commands.SelectProfessionCommand;
import com.herocraftonline.dev.heroes.command.commands.SelectSpecialtyCommand;
import com.herocraftonline.dev.heroes.command.commands.SkillCommand;
import com.herocraftonline.dev.heroes.command.skill.OutsourcedSkill;
import com.herocraftonline.dev.heroes.command.skill.Skill;
import com.herocraftonline.dev.heroes.inventory.HNetServerHandler;
import com.herocraftonline.dev.heroes.party.PartyManager;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroManager;
import com.herocraftonline.dev.heroes.util.ConfigManager;
import com.herocraftonline.dev.heroes.util.DebugLog;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * Heroes Plugin for Herocraft
 * 
 * @author Herocraft's Plugin Team
 */
public class Heroes extends JavaPlugin {

    // Using this instead of getDataFolder(), getDataFolder() uses the File
    // Name. We wan't a constant folder name.
    public static final File dataFolder = new File("plugins" + File.separator + "Heroes");

    // Simple hook to Minecraft's logger so we can output to the console.
    private static final Logger log = Logger.getLogger("Minecraft");
    private static DebugLog debugLog;

    // Setup the Player and Plugin listener for Heroes.
    private final HPlayerListener playerListener = new HPlayerListener(this);
    private final HPluginListener pluginListener = new HPluginListener(this);
    private final HEntityListener entityListener = new HEntityListener(this);
    private final HBlockListener blockListener = new HBlockListener(this);
    private final HCustomEventListener customListener = new HCustomEventListener(this);

    // Various data managers
    private ConfigManager configManager;
    private CommandManager commandManager = new CommandManager();
    private ClassManager classManager;
    private HeroManager heroManager;
    private PartyManager partyManager;

    // Messaging
    private Messaging messaging = new Messaging();

    // Variable for the Permissions plugin handler.
    public static PermissionHandler Permissions;

    // Variable to contain whether Heroes will utilize iConomy or not.
    private boolean useiConomy = false;
    // Variable for the iConomy plugin handler.
    private static iConomy iConomy = null;
    // Variable for mana regen
    private long regenrate = 100L;

    @Override
    public void onLoad() {
        dataFolder.mkdirs(); // Create the Heroes Plugin Directory.
        configManager = new ConfigManager(this);
        heroManager = new HeroManager(this);
        partyManager = new PartyManager(this);
        debugLog = new DebugLog("Heroes", dataFolder + File.separator + "debug.log");
    }

    @Override
    public void onEnable() {
        log(Level.INFO, "version " + getDescription().getVersion() + " is enabled!");

        // Setup the Property for Levels * Exp
        getConfigManager().getProperties().calcExp();

        // Start mana regen
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Hero hero : getHeroManager().getHeroes()) {
                    if (hero.getMana() < 100) {
                        if (hero.getMana() > 95) {
                            hero.setMana(100);
                        } else {
                            hero.setMana(hero.getMana() + 5);
                        }
                    }
                }
            }
        }, 100L, regenrate);

        // Skills Loader
        loadSkills();

        // Attempt to load the Configuration file.
        try {
            configManager.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Player player : getServer().getOnlinePlayers()) {
            heroManager.loadHeroFile(player);
            swapNetServerHandler(player);
            inventoryCheck(player);
        }

        // Call our function to register the events Heroes needs.
        registerEvents();
        // Call our function to setup Heroes Commands.
        registerCommands();

        // Perform the Permissions check.
        setupPermissions();
    }

    /**
     * Perform a Permissions check and setup Permissions if found.
     */
    public void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
        if (Heroes.Permissions == null) {
            if (test != null) {
                Heroes.Permissions = ((Permissions) test).getHandler();
                log(Level.INFO, "Permissions found.");
                for (Player player : getServer().getOnlinePlayers()) {
                    for (BaseCommand cmd : commandManager.getCommands()) {
                        if (cmd instanceof OutsourcedSkill) {
                            ((OutsourcedSkill) cmd).tryLearningSkill(player);
                        }
                    }
                }
            }
        }
    }

    /**
     * Handle Heroes Commands, in this case we send them straight to the commandManager.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandManager.dispatch(sender, command, label, args);
    }

    /**
     * Register the Events which Heroes requires.
     */
    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.PLAYER_ITEM_HELD, playerListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.PLAYER_PICKUP_ITEM, playerListener, Priority.Normal, this);

        pluginManager.registerEvent(Type.ENTITY_DAMAGE, entityListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.ENTITY_TARGET, entityListener, Priority.Normal, this);

        pluginManager.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Normal, this);

        pluginManager.registerEvent(Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this);
        pluginManager.registerEvent(Type.CUSTOM_EVENT, customListener, Priority.Normal, this);
    }

    /**
     * Register Heroes commands to DThielke's Command Manager.
     */
    private void registerCommands() {
        // Page 1
        commandManager.addCommand(new HelpCommand(this));
        commandManager.addCommand(new RecoverItemsCommand(this));
        commandManager.addCommand(new SelectProfessionCommand(this));
        commandManager.addCommand(new SelectSpecialtyCommand(this));
        commandManager.addCommand(new PartyAcceptCommand(this));
        commandManager.addCommand(new PartyCreateCommand(this));
        commandManager.addCommand(new PartyInviteCommand(this));
        commandManager.addCommand(new PartyChatCommand(this));
        // Page 2
        commandManager.addCommand(new AssignSkillCommand(this));
        commandManager.addCommand(new ConfigReloadCommand(this));
        commandManager.addCommand(new SkillCommand(this));
        commandManager.addCommand(new LevelInformationCommand(this));
        commandManager.addCommand(new ManaCommand(this));
    }

    /**
     * Return True/False as to whether iConomy should be used.
     * 
     * @return
     */
    public boolean checkiConomy() {
        this.useiConomy = (iConomy != null); // Tbf this needs changing... even if iConomy is detected we only want to use it if its setup.
        return this.useiConomy;
    }

    /**
     * Setup the iConomy plugin.
     * 
     * @param plugin
     * @return
     */
    public void setupiConomy() {
        Plugin test = this.getServer().getPluginManager().getPlugin("iConomy");
        if (Heroes.iConomy == null) {
            if (test != null) {
                Heroes.iConomy = (iConomy) test;
            }
        }
    }

    /**
     * Return iConomy.
     * 
     * @return
     */
    public static iConomy getiConomy() {
        return iConomy;
    }

    /**
     * What to do during the Disabling of Heroes -- Likely save data and close connections.
     */
    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            heroManager.saveHeroFile(player);
        }

        Heroes.iConomy = null; // When it Enables again it performs the checks anyways.
        Heroes.Permissions = null; // When it Enables again it performs the checks anyways.

        log.info(getDescription().getName() + " version " + getDescription().getVersion() + " is disabled!");
        debugLog.close();
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    public void setClassManager(ClassManager classManager) {
        this.classManager = classManager;
    }

    /**
     * Print messages to the server Log as well as to our DebugLog. 'debugLog' is used to seperate Heroes information from the Servers Log Output.
     * 
     * @param level
     * @param msg
     */
    public void log(Level level, String msg) {
        log.log(level, "[Heroes] " + msg);
        debugLog.log(level, "[Heroes] " + msg);
    }

    /**
     * Print messages to the Debug Log, if the servers in Debug Mode then we also wan't to print the messages to the standard Server Console.
     * 
     * @param level
     * @param msg
     */
    public void debugLog(Level level, String msg) {
        if (this.configManager.getProperties().debug) {
            log.log(level, "[Debug] " + msg);
        }
        debugLog.log(level, "[Debug] " + msg);
    }

    /**
     * Load all the external classes.
     */
    public void loadSkills() {
        File dir = new File(getDataFolder(), "externals");
        ArrayList<String> skNo = new ArrayList<String>();
        dir.mkdir();
        boolean added = false;
        for (String f : dir.list()) {
            if (f.contains(".jar")) {
                Skill skill = SkillLoader.loadSkill(new File(dir, f), this);
                if (skill != null) {
                    commandManager.addCommand(skill);
                    if (!added) {
                        log(Level.INFO, "Collecting and loading skills");
                        added = true;
                    }
                    skNo.add(skill.getName());
                    debugLog.log(Level.INFO, "Skill " + skill.getName() + " Loaded");
                }
            }
        }
        log(Level.INFO, "Skills loaded: " + skNo.toString());
    }

    public void inventoryCheck(Player p) {
        PlayerInventory inv = p.getInventory();
        Hero h = this.heroManager.getHero(p);
        HeroClass hc = h.getPlayerClass();
        int count = 0;
        String item;
        if ((inv.getHelmet() != null) && (inv.getHelmet().getTypeId() != 0)) {
            item = inv.getHelmet().getType().toString();
            if (!(hc.getAllowedArmor().contains(item))) {
                h.addItem(inv.getHelmet());
                this.getMessager().send(p, "$1 has been removed from your Inventory", item);
                inv.setHelmet(null);
                count++;
            }
        }
        if ((inv.getChestplate() != null) && (inv.getChestplate().getTypeId() != 0)) {
            item = inv.getChestplate().getType().toString();
            if (!(hc.getAllowedArmor().contains(item))) {
                h.addItem(inv.getChestplate());
                this.getMessager().send(p, "$1 has been removed from your Inventory", item);
                inv.setChestplate(null);
                count++;
            }
        }
        if ((inv.getLeggings() != null) && (inv.getLeggings().getTypeId() != 0)) {
            item = inv.getLeggings().getType().toString();
            if (!(hc.getAllowedArmor().contains(item))) {
                h.addItem(inv.getLeggings());
                this.getMessager().send(p, "$1 has been removed from your Inventory", item);
                inv.setLeggings(null);
                count++;
            }
        }
        if ((inv.getBoots() != null) && (inv.getBoots().getTypeId() != 0)) {
            item = inv.getBoots().getType().toString();
            if (!(hc.getAllowedArmor().contains(item))) {
                h.addItem(inv.getBoots());
                this.getMessager().send(p, "$1 has been removed from your Inventory", item);
                inv.setBoots(null);
                count++;
            }
        }
        for (int i = 0; i < 8; i++) {
            ItemStack itemStack = inv.getItem(i);
            String itemType = itemStack.getType().toString();

            // Perform a check to see if what we have is a Weapon.
            if (!(itemType.equalsIgnoreCase("BOW"))) {
                try {
                    WeaponItems.valueOf(itemType.substring(itemType.indexOf("_") + 1, itemType.length()));
                } catch (IllegalArgumentException e1) {
                    continue;
                }
            }

            if (!(hc.getAllowedWeapons().contains(itemType))) {
                if (!(moveItem(p, i, itemStack))) {
                    this.getMessager().send(p, "$1 has been removed from your Inventory", itemType);
                    count++;
                } else {
                    this.getMessager().send(p, "You are not trained to use a $1", itemType);
                }
            }
        }
        if (count > 0) {
            getMessager().send(p, "$1 have been removed from your inventory.", count + " Items");
            getMessager().send(p, "Please make space in your inventory then type '$1'", "/heroes recoveritems");
        }
    }

    public boolean moveItem(Player p, int slot, ItemStack item) {
        PlayerInventory inv = p.getInventory();
        Hero h = this.getHeroManager().getHero(p);
        int empty = firstEmpty(p);
        if (empty == -1) {
            h.addItem(item);
            inv.setItem(slot, null);
            return false;
        } else {
            inv.setItem(empty, item);
            inv.setItem(slot, null);
            return true;
        }
    }

    /**
     * Grab the first empty INVENTORY SLOT, skips the Hotbar.
     * 
     * @param p
     * @return
     */
    public int firstEmpty(Player p) {
        ItemStack[] inventory = p.getInventory().getContents();
        for (int i = 9; i < inventory.length; i++) {
            if (inventory[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public HeroManager getHeroManager() {
        return heroManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Messaging getMessager() {
        return messaging;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }

    public void swapNetServerHandler(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        CraftServer server = (CraftServer) Bukkit.getServer();

        if (!(craftPlayer.getHandle().netServerHandler instanceof HNetServerHandler)) {
            Location loc = player.getLocation();
            HNetServerHandler handler = new HNetServerHandler(server.getHandle().server, craftPlayer.getHandle().netServerHandler.networkManager, craftPlayer.getHandle());
            handler.a(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            craftPlayer.getHandle().netServerHandler = handler;
        }
    }
}
