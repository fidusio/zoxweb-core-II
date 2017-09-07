package org.zoxweb.server.util;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

public class JCacheTest {
	
	public static void main(String ...args)
	{
		CachingProvider provider = Caching.getCachingProvider();
		System.out.println("Provider:" + provider);
	}

}
