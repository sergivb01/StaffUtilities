package site.solenxia.staffutilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.payloads.types.Payload;
import site.solenxia.staffutilities.payloads.types.StaffChatPayload;
import site.solenxia.staffutilities.utils.StringUtils;

import static org.bukkit.ChatColor.RED;

public class StaffChatCommand implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(RED + "This command may not be executed by Console.");
			return true;
		}

		Player player = (Player) sender;
		if(args.length < 1){
			player.sendMessage(RED + "Invalid usage: `/staffchat <message...>`");
			return true;
		}

		String message = StringUtils.join(args);

		Payload payload = new StaffChatPayload(player.getUniqueId(), player.getName(), StringUtils.join(args));
		System.out.println("Being sent: " + payload.toDocument().toJson());
		payload.send();

		return false;
	}


}
