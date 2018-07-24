package org.zoxweb.server.util.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.zoxweb.server.task.TaskDefault;
import org.zoxweb.server.task.TaskEvent;
import org.zoxweb.server.task.TaskSchedulerProcessor;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.DateUtil;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.JWTToken;
import org.zoxweb.shared.util.Const.TimeInMillis;
import org.zoxweb.shared.util.AppointmentDefault;
import org.zoxweb.shared.util.KVMapStore;
import org.zoxweb.shared.util.KVMapStoreDefault;
import org.zoxweb.shared.util.SharedUtil;

public class JWTTokenCache
implements KVMapStore<String, JWT>
{
	
	
	
	private static final Logger log = Logger.getLogger(JWTTokenCache.class.getName());
	private class CacheCleanerTask extends TaskDefault
	{

		@Override
		protected void childExecuteTask(TaskEvent event) 
		{
			// TODO Auto-generated method stub
			String hash = (String) event.getTaskExecutorParameters()[0];
			cache.remove(hash);
			log.info("Removed:" + hash);
		}
		
	}
	
	
	private KVMapStore<String, JWT> cache = new KVMapStoreDefault<String, JWT>(new HashMap<String, JWT>(), new HashSet<String>());
	private long expirationPeriod;
	private volatile TaskSchedulerProcessor tsp;
	private volatile CacheCleanerTask cct;
	
	public JWTTokenCache()
	{
		this(5*TimeInMillis.MINUTE.MILLIS, TaskUtil.getDefaultTaskScheduler());
	}
	
	public JWTTokenCache(long expirationPeriod, TaskSchedulerProcessor tsp)
	{
		SharedUtil.checkIfNulls("TaskScheduler null", tsp);
		
		if (expirationPeriod <= 0)
		{
			throw new IllegalArgumentException("invalid expiration period <= 0 " + expirationPeriod);
		}
		
		cct = new CacheCleanerTask();
		this.expirationPeriod = expirationPeriod;
		this.tsp = tsp;
		log.info("ExpirationPeriod " + TimeInMillis.toString(expirationPeriod) + ", TaskScheduler:" + tsp);
	}

	
	
	public boolean map(JWTToken jwtToken)
	{
		return map(jwtToken.getJWT().getHash(), jwtToken.getJWT());
	}
	
	public boolean map(JWT jwt)
	{
		return map(jwt.getHash(), jwt);
	}

	@Override
	public boolean map(String jwtHash, JWT jwt)
		throws SecurityException
	{
		
		long delta = Math.abs(System.currentTimeMillis() - jwt.getPayload().getIssuedAt());
		
		
		if (delta >= expirationPeriod)
		{
			throw new SecurityException("Expired token issued at " + DateUtil.DEFAULT_GMT_MILLIS.format(new Date(jwt.getPayload().getIssuedAt())));
		}
		
		
		boolean ret;
		synchronized(this)
		{
			if (cache.lookup(jwtHash) != null)
			{
				// otp replay
				throw new SecurityException("Token already used, replay attack.");
			}
			
			// register the token
			ret = cache.map(jwtHash, jwt);
			
			tsp.queue(this, new AppointmentDefault(expirationPeriod), cct, jwtHash);
			
		}
		
		// TODO Auto-generated method stub
		return ret;
	}

	@Override
	public JWT lookup(String jwtHash) {
		// TODO Auto-generated method stub
		return cache.lookup(jwtHash);
	}

	@Override
	public boolean remove(String jwtHash) {
		// TODO Auto-generated method stub
		return cache.remove(jwtHash);
	}

	@Override
	public void clear(boolean all) {
		// TODO Auto-generated method stub
		cache.clear(all);
	}

	@Override
	public Iterator<String> exclusions() {
		// TODO Auto-generated method stub
		return cache.exclusions();
	}

	@Override
	public Iterator<JWT> values() {
		// TODO Auto-generated method stub
		return cache.values();
	}

	@Override
	public Iterator<String> keys() {
		// TODO Auto-generated method stub
		return cache.keys();
	}

	@Override
	public void addExclusion(String exclusion) {
		// TODO Auto-generated method stub
		cache.addExclusion(exclusion);;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return cache.size();
	}
	
	
}
