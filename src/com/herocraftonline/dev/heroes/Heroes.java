package com.herocraftonline.dev.heroes;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.dev.heroes.command.CommandManager;
import com.herocraftonline.dev.heroes.command.commands.UpdateCommand;
import com.herocraftonline.dev.heroes.persistance.SQLite;
import com.herocraftonline.dev.heroes.util.ConfigManager;
import com.herocraftonline.dev.heroes.util.Properties;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * Heroes Plugin for Herocraft
 * 
 * @author Herocraft's Plugin Team
 */
public class Heroes extends JavaPlugin {
    private final Listener Listener = new Listener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public static final Logger log = Logger.getLogger("Minecraft");
    private ConfigManager configManager;
    public static PermissionHandler Permissions;
    private CommandManager commandManager;
    public static SQLite sql = new SQLite(null);

    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }

    @Override
    public void onEnable() {
    	  setupPermissions();
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_LOGIN, Listener, Priority.Normal, this);
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
        configManager = new ConfigManager(this);
        try {
            configManager.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Properties.calcExp();
        sql.tryUpdate("CREATE TABLE IF NOT EXISTS `players` (`id` INT auto_increment, `name` VARCHAR, `class` VARCHAR, `exp` INT, `mana` INT)");
        registerEvents();
        registerCommands();

    }
    
    private void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions)test).getHandler();
            } else {
                log.info("Permission system not detected, defaulting to OP");
            }
        }
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandManager.dispatch(sender, command, label, args);
    }

    private void registerEvents() {

    }

    private void registerCommands() {
        commandManager = new CommandManager();
        // Page 1
        commandManager.addCommand(new UpdateCommand(this));
    }

}
