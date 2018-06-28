package site.solenxia.staffutilities;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import site.solenxia.staffutilities.commands.*;
import site.solenxia.staffutilities.listeners.MuteChatListener;
import site.solenxia.staffutilities.listeners.PlayerListener;
import site.solenxia.staffutilities.listeners.patchlisteners.ItemPatchListener;
import site.solenxia.staffutilities.manager.StaffModeManager;
import site.solenxia.staffutilities.manager.VanishManager;
import site.solenxia.staffutilities.redis.RedisManager;

import java.io.File;
import java.util.Map;

public class StaffUtilities extends JavaPlugin{

	@Getter
	public static StaffUtilities instance;

	public void onEnable(){
		instance = this;

		final File configFile = new File(this.getDataFolder() + "/config.yml");
		if(!configFile.exists()){
			this.saveDefaultConfig();
		}
		this.getConfig().options().copyDefaults(true);

		//Register redis
		new RedisManager(this);


		//Register Listeners
		new PlayerListener(this);
		new MuteChatListener(this);
		new ItemPatchListener(this);

		//Register managers
		new VanishManager(this);
		new StaffModeManager(this);

		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("mutechat").setExecutor(new MuteChatCommand());
		getCommand("request").setExecutor(new RequestCommand());
		getCommand("report").setExecutor(new ReportCommand());
		getCommand("clearchat").setExecutor(new ClearChatCommand());
		getCommand("staffchat").setExecutor(new StaffChatCommand());
		getCommand("staffmode").setExecutor(new StaffModeCommand());

		Map<String, Map<String, Object>> map = getDescription().getCommands();
		for(Map.Entry<String, Map<String, Object>> entry : map.entrySet()){
			PluginCommand command = getCommand(entry.getKey());
			command.setPermission("staffutils.command." + entry.getKey());
			command.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', "&e&l⚠ &cYou lack the permission to execute this command."));
		}

	}

	public void onDisable(){
		RedisManager.getSubscriber().getJedisPubSub().unsubscribe();
		RedisManager.getSubscriber().getJedis().disconnect();
		RedisManager.getPublisher().getPool().destroy();

		instance = null;
	}
}