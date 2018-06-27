package site.solenxia.staffutilities;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import site.solenxia.staffutilities.commands.RequestCommand;
import site.solenxia.staffutilities.commands.MuteChatCommand;
import site.solenxia.staffutilities.commands.ReportCommand;
import site.solenxia.staffutilities.commands.StaffChatCommand;
import site.solenxia.staffutilities.listeners.MuteChatListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class StaffUtilities extends JavaPlugin{

	@Getter
	public static StaffUtilities instance;
	public static boolean leaked;

	public void onEnable(){
		instance = this;

		instance.Check();
		instance.Check2();


		getCommand("mutechat").setExecutor(new MuteChatCommand());
		getCommand("helpop").setExecutor(new RequestCommand());
		getCommand("report").setExecutor(new ReportCommand());
		getCommand("staffchat").setExecutor(new StaffChatCommand());

		new MuteChatListener(this);
	}

	public void onDisable(){
		instance = null;
	}


	public void Check(){
		try{
			boolean license = false;
			URL url = new URL("http://solenxia.site/antileak/admin/api.php?plugin=HubCore&license=" + getConfig().getString("LICENSE_KEY"));
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
				System.out.println("Solenxia Hubcore - Version " + pdf.getVersion());
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
			URL url = new URL("https://solenxia.site/antileak/admin/api.php?plugin=HubCore&license=" + getConfig().getString("LICENSE_KEY"));
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