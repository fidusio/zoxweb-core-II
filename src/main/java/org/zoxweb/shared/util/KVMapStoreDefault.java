package org.zoxweb.shared.util;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class KVMapStoreDefault<K,V>
implements KVMapStore<K,V>
{

	protected final Map<K, V> mapCache;
	protected final Set<K> exclusionFilter;
	
	
	public KVMapStoreDefault(Map<K,V> map, Set<K> eFliter)
	{
		SharedUtil.checkIfNulls("Values can't be null", map, eFliter);
		this.mapCache = map;
		this.exclusionFilter = eFliter;
	}
	
	
	@Override
	public synchronized boolean map(K key, V value) 
	{
		if (key != null && value != null)
		{
			if (!exclusionFilter.contains(key))
			{
				mapCache.put(key, value);
				return true;	
			}
		}
		
		return false;
	}

	@Override
	public synchronized V lookup(K key) {
		// TODO Auto-generated method stub
		return mapCache.get(key);
	}

	@Override
	public synchronized boolean remove(K key) {
		// TODO Auto-generated method stub
		return (mapCache.remove(key) != null);
	}

	@Override
	public synchronized void clear(boolean all) {
		// TODO Auto-generated method stub
		mapCache.clear();
		if (all)
			exclusionFilter.clear();
		
	}

	@Override
	public Iterator<K> exclusions() 
	{
		// TODO Auto-generated method stub
		return exclusionFilter.iterator();
	}

	@Override
	public Iterator<V> values()
	{
		// TODO Auto-generated method stub
		return mapCache.values().iterator();
	}

	@Override
	public Iterator<K> keys()
	{
		// TODO Auto-generated method stub
		return mapCache.keySet().iterator();
	}

	@Override
	public synchronized void addExclusion(K exclusion) {
		if(exclusion != null)
			exclusionFilter.add(exclusion);
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mapCache.size();
	}

}
