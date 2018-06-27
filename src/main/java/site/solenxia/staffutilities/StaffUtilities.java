package site.solenxia.staffutilities;

import org.bukkit.plugin.java.JavaPlugin;

public class StaffUtilities extends JavaPlugin {

    private StaffUtilitiesManager staffUtilitiesManager;

    public void onEnable() {


        staffUtilitiesManager = new StaffUtilitiesManager(this);
    }
}
