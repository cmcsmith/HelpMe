package cmc.helpme;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Events implements Listener{
    private Main plugin = Main.getPlugin(Main.class);
    @EventHandler
    public void onGodList(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            if (Commands.godlist.contains(((Player) entity).getName())) {
                event.setCancelled(true);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (Commands.godlist.contains(((Player) entity).getName())) {
                            Commands.godlist.remove(((Player) entity).getName());
                            ((Player) entity).sendMessage(ChatColor.RED + "Your God Mode has been removed!");
                        }
                    }
                }.runTaskLater(plugin, 2500);
            }
        } else {
            event.setCancelled(false);
        }
    }
}
