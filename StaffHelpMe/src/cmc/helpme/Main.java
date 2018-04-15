package cmc.helpme;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main extends JavaPlugin implements Listener {
    private static Plugin plugin;
    private Commands commands = new Commands(this);

    public void onEnable() {
        plugin = this;
        Metrics metrics = new Metrics(this);
        registerCommands();
        registerListener();
        registerConfig();

        UpdateChecker updater = new UpdateChecker(this, 55739);
        try {
            if (updater.checkForUpdates())
                getLogger().info("An update was found! New version: " + updater.getLatestVersion() + " download: " + updater.getResourceURL());
            getLogger().info("An update was found! New version: " + updater.getLatestVersion() + " download: " + updater.getResourceURL());
            getLogger().info("An update was found! New version: " + updater.getLatestVersion() + " download: " + updater.getResourceURL());
        } catch (Exception e) {
            getLogger().info("Could not check for updates! Stacktrace:");
            e.printStackTrace();
        }
    }

    public void onDisable() {
        plugin = null;
    }

    public class UpdateChecker {

        private int project = 0;
        private URL checkURL;
        private String newVersion = "";
        private JavaPlugin plugin;

        public UpdateChecker(JavaPlugin plugin, int projectID) {
            this.plugin = plugin;
            this.newVersion = plugin.getDescription().getVersion();
            this.project = projectID;
            try {
                this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
            } catch (MalformedURLException e) {
            }
        }

        public String getLatestVersion() {
            return newVersion;
        }

        public String getResourceURL() {
            return "https://www.spigotmc.org/resources/" + project;
        }

        public boolean checkForUpdates() throws Exception {
            URLConnection con = checkURL.openConnection();
            this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            return !plugin.getDescription().getVersion().equals(newVersion);
        }

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

