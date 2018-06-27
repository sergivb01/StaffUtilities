package site.solenxia.staffutilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChatCommand implements CommandExecutor {

    public static boolean muteChat = false;

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if (player.hasPermission("staffutilities.mutechat")) {
            if (!muteChat && player.hasPermission("staffutilities.mutechat.toggle")) {
                muteChat = true;
                Bukkit.broadcastMessage(ChatColor.GREEN + "Chat has been muted by: " + ChatColor.WHITE + player.getName() + ChatColor.GREEN + ".");
            } else if ((muteChat) && (player.hasPermission("staffutilities.mutechat.toggle"))) {
                muteChat = false;
                Bukkit.broadcastMessage(ChatColor.RED + "Chat has been unmuted by: " + ChatColor.WHITE + player.getName() + ChatColor.GREEN + ".");
            }
        }
        return false;
    }
}
