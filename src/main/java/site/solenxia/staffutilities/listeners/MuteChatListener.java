package site.solenxia.staffutilities.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import site.solenxia.staffutilities.commands.MuteChatCommand;

public class MuteChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        if (MuteChatCommand.muteChat) {
            if (player.hasPermission("staffutilities.mutechat.chat")) {
                e.setCancelled(true);
            } else {
                e.setCancelled(false);
            }
        }
    }
}
