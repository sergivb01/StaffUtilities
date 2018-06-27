package site.solenxia.staffutilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpOpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender.hasPermission("staffutilities.helpop")) {
            if(args.length < 1) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /helpop <Reason>"));
                return false;
            }

            String reason = " ";
            Bukkit.broadcastMessage(" ");
        }
        return false;
    }
}
