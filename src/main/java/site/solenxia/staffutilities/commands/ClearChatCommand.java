package site.solenxia.staffutilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.StaffUtilities;

public class ClearChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        for (Player online : Bukkit.getServer().getOnlinePlayers()) {

            online.sendMessage(new String[101]);

            String name = "%clearName%";
            online.sendMessage(ChatColor.translateAlternateColorCodes('&', StaffUtilities.getInstance().getConfig().getString("clearchat-formatting")
                    .replace(name, commandSender.getName())
            ));
        }
        return false;
    }
}
