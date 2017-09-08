package org.zoxweb.server.util.cache;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListener;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;
import javax.cache.event.CacheEntryUpdatedListener;




public class JCacheListener<K, V>
implements CacheEntryCreatedListener<K, V>,
		   CacheEntryUpdatedListener<K,V>,
		   CacheEntryRemovedListener<K,V>,
		   CacheEntryExpiredListener<K,V>,
		   CacheEntryEventFilter<K, V> 
{
	private static final transient Logger log = Logger.getLogger(JCacheListener.class.getName());
	private AtomicInteger counter = new AtomicInteger(0);

	@Override
	public void onCreated(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
			throws CacheEntryListenerException
	{
		// TODO Auto-generated method stub
		counter.addAndGet((int)events.spliterator().estimateSize());
		log.info(""+counter.get());
		
	}

	@Override
	public void onUpdated(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
			throws CacheEntryListenerException
	{
		// TODO Auto-generated method stub
		log.info(""+counter.get());
	}

	@Override
	public void onExpired(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
			throws CacheEntryListenerException {
		// TODO Auto-generated method stub
		counter.addAndGet(-(int)events.spliterator().estimateSize());
		log.info(""+counter.get());
	}

	@Override
	public void onRemoved(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
			throws CacheEntryListenerException 
	{
		// TODO Auto-generated method stub
		counter.addAndGet(-(int)events.spliterator().estimateSize());
		log.info(""+counter.get());
	}
	
	public int size() 
	{
		return counter.get();
	}
	
	@Override
	public boolean evaluate(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static <K,V> CacheEntryListenerConfiguration<K, V> toConfiguration() {
		return toConfiguration(new JCacheListener<K,V>());
	  }
	
	
	public static <K,V> CacheEntryListenerConfiguration<K, V> toConfiguration(JCacheListener<K,V> listener) {
		MutableCacheEntryListenerConfiguration<K, V> mcelc = new MutableCacheEntryListenerConfiguration<K,V>(
				
				new FactoryBuilder.SingletonFactory<CacheEntryListener<K, V>>(listener), 
			   new FactoryBuilder.SingletonFactory<CacheEntryEventFilter<K, V>>(listener), false, true);
		
		return mcelc;
	  
	  }


}
