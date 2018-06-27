package site.solenxia.staffutilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class MuteChatCommand implements CommandExecutor{
	public static boolean muteChat = false;

	public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(RED + "This command may not be executed by Console.");
			return true;
		}

		Player player = (Player) sender;

		boolean newMute = !muteChat;
		Bukkit.broadcastMessage(GREEN + "Chat has been " + (newMute ? "muted" : "unmuted") + " by: " + WHITE + player.getName() + GREEN + ".");

		return true;
	}


}
