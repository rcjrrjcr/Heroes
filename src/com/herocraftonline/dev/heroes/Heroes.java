package com.herocraftonline.dev.heroes;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.command.CommandManager;
import com.herocraftonline.dev.heroes.command.commands.CClassCommand;
import com.herocraftonline.dev.heroes.command.commands.ConfigReloadCommand;
import com.herocraftonline.dev.heroes.command.commands.SelectClassCommand;
import com.herocraftonline.dev.heroes.command.commands.UpdateCommand;
import com.herocraftonline.dev.heroes.persistance.SQLite;
import com.herocraftonline.dev.heroes.util.ConfigManager;
import com.herocraftonline.dev.heroes.util.Properties;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import com.nijiko.coelho.iConomy.iConomy;

/**
 * Heroes Plugin for Herocraft
 * 
 * @author Herocraft's Plugin Team
 */
public class Heroes extends JavaPlugin {
    // Simple hook to Minecrafts logger so we can output to the console.
    public static final Logger log = Logger.getLogger("Minecraft");

    // Setup the Player and Plugin listener for Heroes.
    private final HPlayerListener playerListener = new HPlayerListener(this);
    private final HPluginListener pluginListener = new HPluginListener(this);

    // Create a new instance of the ConfigManager
    public ConfigManager configManager;

    // Create a new instance of our SQLite manager.
    public static SQLite sql;

    // Variable to contain the Command Manager
    private CommandManager commandManager;
    
    // Variable to contain the Class Manager
    private ClassManager classManager;

    // Variable for the Permissions plugin handler.
    public static PermissionHandler Permissions;

    // Variable to contain whether Heroes will utilize iConomy or not.
    private boolean useiConomy = false;
    // Variable for the iConomy plugin handler.
    private static iConomy iConomy = null;

    public void onLoad() {
        getDataFolder().mkdirs(); // Create the Heroes Plugin Directory.
        sql = new SQLite();
        configManager = new ConfigManager(this);
    }

    @Override
    public void onEnable() {
        log.info(getDescription().getName() + " version " + getDescription().getVersion() + " is enabled!"); // Simple Name and Version output.

        // Attempt to load the Configuration file.
        try {
            configManager.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setup the Property for Levels * Exp
        Properties.calcExp();

        // Create the Player table if it doesn't already exist.
        sql.tryUpdate("CREATE TABLE IF NOT EXISTS `players` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` VARCHAR, `class` VARCHAR, `exp` INT, `mana` INT)");

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
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this); // To setup the Players Initial Class.
        getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this); // To keep an eye out for Permissions and iConomy.
    }

    /**
     * Register Heroes commands to DThielke's Command Manager.
     */
    private void registerCommands() {
        commandManager = new CommandManager();
        // Page 1
        commandManager.addCommand(new UpdateCommand(this));
        commandManager.addCommand(new CClassCommand(this));
        commandManager.addCommand(new ConfigReloadCommand(this));
        commandManager.addCommand(new SelectClassCommand(this));
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
     * Send a message to the Player with the Prefix Colored.
     * 
     * @param p Player to send message to.
     * @param m Message contents.
     */
    public static void sendMessage(Player p, String m) {
        p.sendMessage(Properties.cColor + "Heroes: " + m);
    }

    /**
     * What to do during the Disabling of Heroes -- Likely save data and close connections.
     */
    @Override
    public void onDisable() {
        Heroes.iConomy = null; // When it Enables again it performs the checks anyways.
        Heroes.Permissions = null; // When it Enables again it performs the checks anyways.
        log.info(getDescription().getName() + " version " + getDescription().getVersion() + " is disabled!");
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    public void setClassManager(ClassManager classManager) {
        this.classManager = classManager;
    }
}
