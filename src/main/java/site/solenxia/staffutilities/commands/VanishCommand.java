package site.solenxia.staffutilities.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.manager.VanishManager;

import static org.bukkit.ChatColor.RED;

public class VanishCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args){
		if(!(commandSender instanceof Player)){
			commandSender.sendMessage(RED + "This command may not be executed by Console.");
			return true;
		}

		Player player = (Player) commandSender;
		boolean vanished = VanishManager.toggleVanish(player);

		String color = (vanished ? "&a" : "&c");

		player.sendMessage(ChatColor.translateAlternateColorCodes('&', color + "You are " + (vanished ? "now vanished" : "no longer vanished")));
		return false;
	}
}
