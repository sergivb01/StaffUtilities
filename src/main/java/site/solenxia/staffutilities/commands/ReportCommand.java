package site.solenxia.staffutilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender.hasPermission("staffutilities.report")) {
            if(args.length < 2) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /report <Player> <Reason>"));
                return false;
            }

            String target = args[0];
            if(Bukkit.getPlayer(target) == null) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&C" + target + " is currently not online."));
                return false;
            }

            String reason = " ";
            Bukkit.broadcastMessage(" ");
        }
        return false;
    }
}
