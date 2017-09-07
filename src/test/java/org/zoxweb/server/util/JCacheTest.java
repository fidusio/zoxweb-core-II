package org.zoxweb.server.util;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;

import javax.cache.expiry.ExpiryPolicy;
import javax.cache.spi.CachingProvider;

public class JCacheTest {
	

	@SuppressWarnings("serial")
	public static class ExpirationPolicy
	 implements ExpiryPolicy, Serializable
	{
		
		private Duration creation;
		private Duration access;
		private Duration update;
		ExpirationPolicy(Duration ...durations)
		{
			if (durations != null && durations.length > 0)
			{
				int index = 0;
				creation = durations[index++];
				if (durations.length > index)
				{
					access = durations[index++];
					if (durations.length > index)
					{
						update = durations[index++];
					}
				}
			}
		}
		
		
		public static Factory<ExpiryPolicy> factoryOf(Duration ...durations) {
		    return new FactoryBuilder.SingletonFactory<ExpiryPolicy>(
		        new ExpirationPolicy(durations));
		  }

		@Override
		public Duration getExpiryForCreation() {
			// TODO Auto-generated method stub
			return creation;
		}

		@Override
		public Duration getExpiryForAccess() {
			// TODO Auto-generated method stub
			return access;
		}

		@Override
		public Duration getExpiryForUpdate() {
			// TODO Auto-generated method stub
			return update;
		}
		
	}
	
	
	public static void main(String ...args)
	{
		
		MutableConfiguration<String, String> configuration = new MutableConfiguration<String, String>();
		configuration.setTypes(String.class, String.class);
		configuration.setExpiryPolicyFactory(ExpirationPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 9)));
		
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager cm = provider.getCacheManager();
		Cache<String, String> cache = cm.createCache("test-string", configuration);
		System.out.println("Provider:" + provider);
		System.out.println("Provider:" + cm);
		System.out.println("Provider:" + cache);
		
	
		cache.put("marwan", "nael");
		System.out.println("marwan:"+cache.get("marwan"));
		System.out.println(""+cache.get("toto"));
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("marwan:"+cache.get("marwan"));
		
		
	}

}
