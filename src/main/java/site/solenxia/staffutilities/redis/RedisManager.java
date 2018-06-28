package site.solenxia.staffutilities.redis;

import lombok.Getter;
import site.solenxia.staffutilities.StaffUtilities;
import site.solenxia.staffutilities.redis.pubsub.Publisher;
import site.solenxia.staffutilities.redis.pubsub.Subscriber;

public class RedisManager{
	@Getter
	public static Publisher publisher;
	@Getter
	public static Subscriber subscriber;
	private StaffUtilities plugin;

	public RedisManager(StaffUtilities plugin){
		this.plugin = plugin;
		init();
	}

	private void init(){
		publisher = new Publisher(plugin);
		subscriber = new Subscriber(plugin);
		plugin.getLogger().info("Registered sub and pub");
	}


}