package site.solenxia.staffutilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import site.solenxia.staffutilities.StaffUtilities;
import site.solenxia.staffutilities.commands.MuteChatCommand;

import static org.bukkit.ChatColor.RED;

public class MuteChatListener implements Listener{

	public MuteChatListener(StaffUtilities plugin){
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();

		if(MuteChatCommand.muteChat && !player.hasPermission("staffutilities.mutechat.chat")){
			player.sendMessage(RED + "⚠ Global chat is currently muted.");
			event.setCancelled(true);
		}
	}

}
