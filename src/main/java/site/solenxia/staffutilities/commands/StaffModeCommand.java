package site.solenxia.staffutilities.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.manager.StaffModeManager;

public class StaffModeCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args){

		Player player = (Player) commandSender;

		if(StaffModeManager.toggleStaff(player)){
			player.sendMessage(ChatColor.GREEN + "You are now in staff mode. (Saving previous inventory)");
		}else{
			player.sendMessage(ChatColor.RED + "Your no longer in modmode!");
			player.getInventory().clear();
			player.setGameMode(GameMode.SURVIVAL);
		}
		return false;
	}
}
