package site.solenxia.staffutilities.payloads.types;

import lombok.Getter;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.StaffUtilities;

import java.util.UUID;

@Getter
public class RequestPayload extends Payload{
	private UUID uuid;

	private String playerName;
	private UUID playerUUID;
	private String reason;

	public RequestPayload(){
		super("request");
	}

	public RequestPayload(String playerName, UUID playerUUID, String reason){
		super("request");
		this.uuid = UUID.randomUUID();
		this.playerName = playerName;
		this.playerUUID = playerUUID;
		this.reason = reason;
	}

	public void fromDocument(Document document){
		this.uuid = (UUID) document.get("uuid");
		this.playerName = document.getString("playerName");
		this.playerUUID = (UUID) document.get("playerUUID");
		this.reason = document.getString("reason");
		this.server = document.getString("server");
	}

	public Document toDocument(){
		return new Document("playerName", playerName)
				.append("playerUUID", playerUUID)
				.append("reason", reason)
				.append("uuid", uuid)
				.append("server", server);
	}

	public void sendMessage(Player player){
		StaffUtilities.getInstance().getConfig().getStringList("request-formatting").forEach(str ->
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', str
						.replace("%player%", playerName)
						.replace("%reason%", reason)
						.replace("%server%", server)
				))
		);
	}
}

