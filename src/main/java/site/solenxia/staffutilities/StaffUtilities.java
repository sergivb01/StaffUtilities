package site.solenxia.staffutilities;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import site.solenxia.staffutilities.commands.MuteChatCommand;
import site.solenxia.staffutilities.commands.ReportCommand;
import site.solenxia.staffutilities.commands.RequestCommand;
import site.solenxia.staffutilities.commands.StaffChatCommand;
import site.solenxia.staffutilities.listeners.MuteChatListener;
import site.solenxia.staffutilities.redis.RedisManager;

import java.io.File;
import java.util.Map;

public class StaffUtilities extends JavaPlugin{

	@Getter
	public static StaffUtilities instance;
	public static boolean leaked;

	public void onEnable(){
		instance = this;

		final File configFile = new File(this.getDataFolder() + "/config.yml");
		if(!configFile.exists()){
			this.saveDefaultConfig();
		}
		this.getConfig().options().copyDefaults(true);

		new RedisManager(this);

		new MuteChatListener(this);

		getCommand("mutechat").setExecutor(new MuteChatCommand());
		getCommand("request").setExecutor(new RequestCommand());
		getCommand("report").setExecutor(new ReportCommand());
		getCommand("staffchat").setExecutor(new StaffChatCommand());

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