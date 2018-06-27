package site.solenxia.staffutilities;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffUtilities extends JavaPlugin {

    @Getter public static StaffUtilities instance;
    private StaffUtilitiesManager staffUtilitiesManager;


    public void onEnable() {
        instance = this;

        staffUtilitiesManager = new StaffUtilitiesManager(this);
    }

    public void onDisable() {
        instance = null;
    }
}
