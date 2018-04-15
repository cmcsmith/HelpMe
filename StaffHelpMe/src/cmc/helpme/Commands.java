package cmc.helpme;

import net.minecraft.server.v1_12_R1.CommandExecute;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.HashMap;

public class Commands extends CommandExecute implements Listener,CommandExecutor {
    Main plugin;
    public Commands(Main instance) {
        this.plugin = instance;
    }

    public static ArrayList<String> helplist = new ArrayList<>();
    public static ArrayList<String> godlist = new ArrayList<>();
    public static HashMap<String, Location> staffback = new HashMap<>();
    public String helpme = "helpme";
    public String stafftp = "stafftp";
    public String staffbackcmd = "staffback";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((cmd.getName().equalsIgnoreCase(helpme)) && ((sender instanceof Player))) {
            Player player = (Player) sender;
            String sErrorUsageHelpMe = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorUsageHelpMe"));
            String sErrorShortHelpMe = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorShortHelpMe"));
            String sErrorLongHelpMe = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorLongHelpMe"));
            String sSentToStaff = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SentToStaff"));
            String sReasonSent = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReasonSent"));
            String reasonString = "";

            for (int i = 0; i < args.length; i++) {
                String arg = args[i] + " ";
                reasonString = reasonString + arg;
            }
            int length = reasonString.length();

            if (length == 0) {
                player.sendMessage(sErrorUsageHelpMe);
                return true;
            }

            if (length > 40) {
                player.sendMessage(sErrorLongHelpMe);
                return true;
            }

            if (length < 12) {
                player.sendMessage(sErrorShortHelpMe);
                return true;
            }

            player.sendMessage(sSentToStaff);
            if (!helplist.contains(player.getName())) {
                helplist.add(player.getName());
            }
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.hasPermission("helpme.staff")) {
                    p.sendMessage(sReasonSent.replace("%player%", player.getName()).replace("%reason%", reasonString));
                }
            }
        }
        if((cmd.getName().equalsIgnoreCase(stafftp)) && ((sender instanceof Player))) {
            Player player = (Player) sender;
            String sErrorUsageTP = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorUsageTP"));
            String sErrorUsagePlayer = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorUsagePlayer"));
            String sErrorNeverRequest = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorNeverRequest"));
            String sTPtoPlayer1 = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TPtoPlayera"));
            String sTPtoPlayer2 = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TPtoPlayerb"));
            String sTPtoPlayer3 = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TPtoPlayerc"));
            String sTargetRespond = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TargetRespond"));
            String sToStaff = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ToStaff"));
            String sNotStaff = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NotStaff"));

            if (args.length == 0){
                player.sendMessage(sErrorUsageTP);
                return true;
            }

            if (!(player.hasPermission("helpme.staff"))) {
                player.sendMessage(sNotStaff);
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(sErrorUsagePlayer);
                return true;
            }

            boolean helpcheck = helplist.contains(target.getName());
            if (helpcheck) {
                player.sendMessage(sTPtoPlayer1);
                player.sendMessage(sTPtoPlayer2);
                player.sendMessage(sTPtoPlayer3);
                target.sendMessage(sTargetRespond.replace("%player%", player.getName()));
                staffback.put(player.getName(), player.getLocation());
                godlist.add(player.getName());
                helplist.remove(target.getName());
                player.teleport(target);
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p.hasPermission("helpme.staff")) {
                        p.sendMessage(sToStaff.replace("%player%", player.getName()).replace("%target%", target.getName()));
                    }
                }
            } else {
                player.sendMessage(sErrorNeverRequest);
            }
        }

        if((cmd.getName().equalsIgnoreCase(staffbackcmd)) && ((sender instanceof Player))) {
            Player player = (Player) sender;
            String sErrorUsageStaffback = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorUsageStaffback"));
            String sErrorNeverResponded = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ErrorNeverResponded"));
            String sReturn = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Return"));
            String sNotStaff = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NotStaff"));

            if (args.length >= 1){
                player.sendMessage(sErrorUsageStaffback);
                return true;
            }

            if (!(player.hasPermission("helpme.staff"))) {
                player.sendMessage(sNotStaff);
                return true;
            }

            if(staffback.containsKey(player.getName())) {
                player.sendMessage(sReturn);
                player.teleport(staffback.get(player.getName()));
                staffback.remove(player.getName());
                godlist.remove(player.getName());
            } else {
                player.sendMessage(sErrorNeverResponded);
            }

        }
        return false;
    }
}