package site.solenxia.staffutilities;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import site.solenxia.staffutilities.commands.MuteChatCommand;
import site.solenxia.staffutilities.commands.ReportCommand;
import site.solenxia.staffutilities.commands.RequestCommand;
import site.solenxia.staffutilities.commands.StaffChatCommand;
import site.solenxia.staffutilities.listeners.MuteChatListener;
import site.solenxia.staffutilities.redis.RedisManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
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
//
//		instance.Check();
//		instance.Check2();

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
		RedisManager.getPublisher().getPool().destroy();

		instance = null;
	}


	public void Check(){
		try{
			boolean license = false;
			URL url = new URL("http://solenxia.site/antileak/admin/api.php?plugin=StaffUtilities=" + getConfig().getString("LICENSE_KEY"));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine = in.readLine();
			while(inputLine != null){
				if(!(inputLine.contains("true")))
					continue;
				license = true;
				PluginDescriptionFile pdf = this.getDescription(); //Gets plugin.yml
				pdf.getVersion(); //Gets the version
				System.out.println("-----------------------------------");
				System.out.println(" ");
				System.out.println("Solenxia StaffUtils - Version " + pdf.getVersion());
				System.out.println(" ");
				System.out.println("Successfuly verified license key.");
				System.out.println(" ");
				System.out.println("-----------------------------------");

				return;
			}
			in.close();
			if(!(license)){
				System.out.println("Leaked version of Solenxia Hubcore detected, shutting down server.");
				Bukkit.getPluginManager().disablePlugin(this);
				this.leaked = true;
				return;
			}
		}catch(Exception ex){
			System.out.print("Connection issue with auth servers.");
			System.out.print("Please report this to me on Discord.");
			System.out.print(ex);
		}

		Bukkit.getPluginManager().disablePlugin(this);
		this.leaked = true;
		return;
	}

	public void Check2(){
		try{
			boolean license = false;
			URL url = new URL("https://solenxia.site/antileak/admin/api.php?plugin=?plugin=StaffUtilities=" + getConfig().getString("LICENSE_KEY"));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine = in.readLine();
			while(inputLine != null){
				if(!(inputLine.contains("true")))
					continue;
				license = true;

				return;
			}
			in.close();
			if(!(license)){
				System.out.println("Leaked version of Solenxia Hubcore detected, shutting down server.");
				Bukkit.getPluginManager().disablePlugin(this);
				this.leaked = true;
				return;
			}
		}catch(Exception ex){
			System.out.print("Connection issue with auth servers.");
			System.out.print("Please report this to me on Discord.");
			System.out.print(ex);
		}

		Bukkit.getPluginManager().disablePlugin(this);
		this.leaked = true;
		return;
	}

	public void Timer(){
		new BukkitRunnable(){
			public void run(){
				Check2();
			}
		}.runTaskTimerAsynchronously(this, 10L, 0L);
	}
}