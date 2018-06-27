package site.solenxia.staffutilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class StaffUtilitiesManager {

    private StaffUtilities staffUtilities;

    public StaffUtilitiesManager(StaffUtilities staffUtilities) {
        this.staffUtilities = staffUtilities;

        PluginManager manager = Bukkit.getPluginManager();
    }
}
