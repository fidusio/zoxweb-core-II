package org.zoxweb.server.util;

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;


import javax.cache.spi.CachingProvider;

import org.zoxweb.server.security.shiro.cache.ShiroJCache;

public class JCacheTest {
	

	
	
	
	public static void main(String ...args)
	{
		long delta = System.currentTimeMillis();
		
		MutableConfiguration<String, String> configuration = new MutableConfiguration<String, String>();
		configuration.setTypes(String.class, String.class);
		configuration.setExpiryPolicyFactory(JCacheExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 5)));
		
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager cm = provider.getCacheManager();
		Cache<String, String> cache = cm.createCache("test-string", configuration);
		
		ShiroJCache<String, String> sjc = new ShiroJCache<>(cache);
		System.out.println("Provider:" + provider);
		System.out.println("Provider:" + cm);
		System.out.println("Provider:" + cache);
		
	
		sjc.put("marwan", "nael");
		System.out.println("marwan:"+sjc.get("marwan"));
		System.out.println(""+sjc.get("toto"));
		System.out.println(sjc.values());
		System.out.println(sjc.keys());
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("marwan:"+sjc.get("marwan"));
		System.out.println(sjc.values());
		System.out.println(sjc.keys());
		System.out.println("delta :" + (System.currentTimeMillis() - delta));
		
		
		
	}

}
