package site.solenxia.staffutilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import site.solenxia.staffutilities.commands.HelpOpCommand;
import site.solenxia.staffutilities.commands.MuteChatCommand;
import site.solenxia.staffutilities.commands.ReportCommand;
import site.solenxia.staffutilities.commands.StaffChatCommand;
import site.solenxia.staffutilities.listeners.MuteChatListener;

public class StaffUtilitiesManager {

    private StaffUtilities staffUtilities;

    public StaffUtilitiesManager(StaffUtilities staffUtilities) {
        this.staffUtilities = staffUtilities;

        staffUtilities.getCommand("mutechat").setExecutor(new MuteChatCommand());
        staffUtilities.getCommand("helpop").setExecutor(new HelpOpCommand());
        staffUtilities.getCommand("report").setExecutor(new ReportCommand());
        staffUtilities.getCommand("staffchat").setExecutor(new StaffChatCommand());

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new MuteChatListener(), staffUtilities);
    }
}
