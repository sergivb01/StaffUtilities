package site.solenxia.staffutilities.payloads.types;

import site.solenxia.staffutilities.StaffUtilities;
import site.solenxia.staffutilities.redis.RedisManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class Payload{
	String server;
	private String type;

	public Payload(String type){
		this.type = type;
		this.server = StaffUtilities.getInstance().getConfig().getString("server-name");
	}

	@Override
	public boolean equals(Object obj){
		return super.equals(obj);
	}

	public void send(){
		Document document = this.toDocument()
				.append("type", type)
				.append("server", server)
				.append("timestamp", System.currentTimeMillis());

		RedisManager.publisher.write("payload;" +
				document.toJson()
		);
		broadcast();
	}

	public Collection<Player> getStaffMembers(){
		return Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("staffutils.utils.staff")).collect(Collectors.toList());
	}

	public abstract void fromDocument(Document document);

	public abstract Document toDocument();

	public abstract void broadcast();


}
