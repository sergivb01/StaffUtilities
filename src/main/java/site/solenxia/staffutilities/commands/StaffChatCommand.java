package site.solenxia.staffutilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StaffChatCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender.hasPermission("staffutilities.staffchat")) {

            if(args.length < 1) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Usage: /staffchat <message>"));
                return false;
            }

            String msg = " ";
            Bukkit.broadcastMessage(msg);

            /**
             * Payload tings etc
             */

        }
        return false;
    }
}
