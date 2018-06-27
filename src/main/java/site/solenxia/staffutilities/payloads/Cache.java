package site.solenxia.staffutilities.payloads;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Cache{
	public static Map<UUID, Long> commandDelay = new HashMap<>();

	public static boolean canExecute(Player player){
		return !commandDelay.containsKey(player.getUniqueId()) || System.currentTimeMillis() - commandDelay.get(player.getUniqueId()) > TimeUnit.MINUTES.toMillis(5);
	}

	public static void addPlayerDelay(Player player){
		commandDelay.put(player.getUniqueId(), System.currentTimeMillis());
	}


}
