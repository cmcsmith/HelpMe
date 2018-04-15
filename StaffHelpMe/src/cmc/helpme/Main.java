package cmc.helpme;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    private static Plugin plugin;
    private Commands commands = new Commands(this);

    public void onEnable() {
        plugin = this;
        registerCommands();
        registerListener();
        registerConfig();
    }

    public void onDisable() {
        plugin = null;
    }

    public void registerConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerCommands() {
        getCommand(commands.helpme).setExecutor(commands);
        getCommand(commands.stafftp).setExecutor(commands);
        getCommand(commands.staffbackcmd).setExecutor(commands);
    }

    private void registerListener() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
    }
}

