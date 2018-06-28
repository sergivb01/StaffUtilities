package site.solenxia.staffutilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import site.solenxia.staffutilities.StaffUtilities;
import site.solenxia.staffutilities.payloads.types.ServerSwitchPayload;

public class PlayerListener implements Listener{
	private StaffUtilities plugin;

	public PlayerListener(StaffUtilities plugin){
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onStaffJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();

		if(player.hasPermission("staffutils.utils.staff")){
			//Ran .5s later to avoid getting join message before quit
			Bukkit.getScheduler().runTaskLater(plugin, () -> new ServerSwitchPayload(player.getUniqueId(), player.getName(), "joined").send(), 10L);
		}
	}


	@EventHandler
	public void onStaffQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();

		if(player.hasPermission("staffutils.utils.staff")){
			new ServerSwitchPayload(player.getUniqueId(), player.getName(), "quit").send();
		}
	}



}
