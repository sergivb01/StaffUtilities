package site.solenxia.staffutilities.redis.pubsub;

import lombok.Getter;
import org.bson.Document;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import site.solenxia.staffutilities.StaffUtilities;
import site.solenxia.staffutilities.payloads.PayloadParser;

import java.util.Arrays;

public class Subscriber{
	private StaffUtilities plugin;
	@Getter
	private JedisPubSub jedisPubSub;
	private Jedis jedis;

	public Subscriber(StaffUtilities plugin){
		this.plugin = plugin;
		this.jedis = new Jedis(plugin.getConfig().getString("redis.host"), plugin.getConfig().getInt("redis.port"), 2000);
		if(plugin.getConfig().getBoolean("redis.auth.enabled")){
			this.jedis.auth(plugin.getConfig().getString("redis.auth.password"));
		}
		init();
	}

	private void init(){
		jedisPubSub = this.get();
		new Thread(() -> {
			jedis.subscribe(jedisPubSub, plugin.getConfig().getString("redis.channel"));
			plugin.getLogger().info("Redis subscriber has subscribed to " + plugin.getConfig().getString("redis.channel"));
		}).start(); //Create subscriber in new Thread to avoid blocking main one
	}

	private JedisPubSub get(){
		return new JedisPubSub(){
			@Override
			public void onMessage(final String channel, final String message){
				final String[] args = message.split(";");
				final String command = args[0].toLowerCase();
				//TODO: Implement encryption (?)

				if(command.equalsIgnoreCase("payload")){
					Document document = Document.parse(args[1]);
					if(document != null){
						PayloadParser.parse(document);
					}
					return;
				}

				plugin.getLogger().warning("Recived unknown redis message! " + Arrays.toString(args));
			}

			@Override
			public void onPMessage(String s, String s1, String s2){

			}

			@Override
			public void onSubscribe(String s, int i){

			}

			@Override
			public void onUnsubscribe(String s, int i){

			}

			@Override
			public void onPUnsubscribe(String s, int i){

			}

			@Override
			public void onPSubscribe(String s, int i){

			}

		};
	}


}