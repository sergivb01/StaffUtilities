package site.solenxia.staffutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.payloads.Cache;
import site.solenxia.staffutilities.payloads.types.RequestPayload;
import site.solenxia.staffutilities.utils.StringUtils;

import static org.bukkit.ChatColor.RED;

public class RequestCommand implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(RED + "This command may not be executed by Console.");
			return true;
		}

		Player player = (Player) sender;
		if(args.length < 1){
			player.sendMessage(RED + "Invalid usage: `/request <message...>`");
			return true;
		}

		String reason = StringUtils.join(args);


		if(!Cache.canExecute(player)){
			player.sendMessage(RED + "You hav already used the report or request command in the past 5 minutes! Please wait and try again.");
			return true;
		}


		new RequestPayload(player.getName(), player.getUniqueId(), reason).send();

		return true;
	}
}
