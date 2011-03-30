package com.herocraftonline.dev.heroes;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.command.CommandManager;
import com.herocraftonline.dev.heroes.command.commands.ConfigReloadCommand;
import com.herocraftonline.dev.heroes.command.commands.SelectProfession;
import com.herocraftonline.dev.heroes.command.commands.SelectSpecialty;
import com.herocraftonline.dev.heroes.command.commands.UpdateCommand;
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

    // Using this instead of getDataFolder(), getDataFolder() uses the File Name. We wan't a constant folder name.
    public static final File dataFolder = new File("plugins" + File.separator + "Heroes");

    // Simple hook to Minecraft's logger so we can output to the console.
    private static final Logger log = Logger.getLogger("Minecraft");
    private static DebugLog debugLog;

    // Setup the Player and Plugin listener for Heroes.
    private final HPlayerListener playerListener = new HPlayerListener(this);
    private final HPluginListener pluginListener = new HPluginListener(this);
    private final HEntityListener entityListener = new HEntityListener(this);
    private final HBlockListener blockListener = new HBlockListener(this);

    // Various data managers
    private ConfigManager configManager;
    private CommandManager commandManager;
    private ClassManager classManager;
    private HeroManager heroManager;

    // Messaging
    private Messaging messaging = new Messaging();

    // Variable for the Permissions plugin handler.
    public static PermissionHandler Permissions;

    // Variable to contain whether Heroes will utilize iConomy or not.
    private boolean useiConomy = false;
    // Variable for the iConomy plugin handler.
    private static iConomy iConomy = null;

    @Override
    public void onLoad() {
        dataFolder.mkdirs(); // Create the Heroes Plugin Directory.
        configManager = new ConfigManager(this);
        heroManager = new HeroManager(this);
        debugLog = new DebugLog("Heroes", dataFolder + File.separator + "debug.log");
    }

    @Override
    public void onEnable() {
        log(Level.INFO, "version " + getDescription().getVersion() + " is enabled!"); // Simple Name and Version output.

        // Attempt to load the Configuration file.
        try {
            configManager.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setup the Property for Levels * Exp
        getConfigManager().getProperties().calcExp();

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
        pluginManager.registerEvent(Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Normal, this);

        pluginManager.registerEvent(Type.ENTITY_DAMAGE, entityListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.ENTITY_DEATH, entityListener, Priority.Normal, this);

        pluginManager.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Normal, this);

        pluginManager.registerEvent(Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this); // To keep an eye out for Permissions and iConomy.
    }

    /**
     * Register Heroes commands to DThielke's Command Manager.
     */
    private void registerCommands() {
        commandManager = new CommandManager();
        // Page 1
        commandManager.addCommand(new UpdateCommand(this));
        commandManager.addCommand(new SelectProfession(this));
        commandManager.addCommand(new ConfigReloadCommand(this));
        commandManager.addCommand(new SelectSpecialty(this));
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
     * Print messages to the server Log as well as to our DebugLog.
     * 'debugLog' is used to seperate Heroes information from the Servers Log Output.
     * @param level
     * @param msg
     */
    public void log(Level level, String msg) {
        log.log(level, "[Heroes] " + msg);
        debugLog.log(level, "[Heroes] " + msg);
    }

    /**
     * Print messages to the Debug Log, if the servers in Debug Mode then we
     * also wan't to print the messages to the standard Server Console.
     * @param level
     * @param msg
     */
    public void debugLog(Level level, String msg) {
        if (this.configManager.getProperties().debug) {
            log.log(level, "[Debug] " + msg);
        }
        debugLog.log(level, "[Debug] " + msg);
    }

    public HeroManager getHeroManager() {
        return heroManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Messaging getMessaging() {
        return messaging;
    }
}
