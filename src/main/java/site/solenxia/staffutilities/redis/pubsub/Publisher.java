package site.solenxia.staffutilities.redis.pubsub;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import site.solenxia.staffutilities.StaffUtilities;

public class Publisher{
	private StaffUtilities plugin;
	@Getter
	private JedisPool pool;
	private String channel;

	public Publisher(StaffUtilities plugin){
		if(plugin.getConfig().getBoolean("redis.auth.enabled")){ //Handle auth
			pool = new JedisPool(new JedisPoolConfig(), plugin.getConfig().getString("redis.host"), plugin.getConfig().getInt("redis.port"), 2000, plugin.getConfig().getString("redis.auth.password"));
		}else{
			pool = new JedisPool(new JedisPoolConfig(), plugin.getConfig().getString("redis.host"), plugin.getConfig().getInt("redis.port"), 2000);
		}
		this.channel = plugin.getConfig().getString("redis.channel");
	}

	public void write(final String message){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			jedis.publish(channel, message);
			pool.returnResource(jedis);
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
	}


}