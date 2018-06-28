package site.solenxia.staffutilities.payloads.types;

import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.StaffUtilities;

import java.util.UUID;

@Getter
public class StaffChatPayload extends Payload {
    private UUID uuid;

    private UUID playerUUID;
    private String playerName;
    private String message;

    public StaffChatPayload() {
        super("staffchat");
    }

    public StaffChatPayload(UUID playerUUID, String playerName, String message) {
        super("staffchat");
        this.uuid = UUID.randomUUID();
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.message = message;
    }

    public void fromDocument(Document document) {
        this.uuid = (UUID) document.get("uuid");
        this.playerUUID = (UUID) document.get("playerUUID");
        this.playerName = document.getString("playerName");
        this.message = document.getString("message");
        this.server = document.getString("server");
    }

    public Document toDocument() {
        return new Document("uuid", uuid)
                .append("playerUUID", playerUUID)
                .append("playerName", playerName)
                .append("message", message)
                .append("server", server);
    }


    public void broadcast() {
        StaffUtilities.getInstance().getConfig().getStringList("staffchat-formatting")
                .forEach(str -> Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', str
                        .replace("%player%", playerName)
                        .replace("%message%", message)
                        .replace("%server%", server)
                )));
    }
}
