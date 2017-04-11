package org.zoxweb.shared.net;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("serial")
public class IPMapCache
        implements Serializable,IPMapStore {

	private HashMap<String, String> ipMapC = new HashMap<String, String>();
	private HashSet<String> ignoreFilter = new HashSet<String>();
	
	public IPMapCache() {

	}
	
	public synchronized boolean addIPMap(String ipAddress, String macAddress) {
		if (ipAddress != null && macAddress != null) {
			if (!ignoreFilter.contains(ipAddress) && !ignoreFilter.contains( macAddress)) {
				ipMapC.put(ipAddress, macAddress);
				return true;
			}
		}
		
		return false;
	}
	
	public synchronized void clear() {
		ipMapC.clear();
		ignoreFilter.clear();
	}
	
	public synchronized String lookupMapFromIPAddress(String ipAddress) throws IOException {
		return ipMapC.get(ipAddress);
	}
	
	public int size() {
		return ipMapC.size();
	}
	
	public int filterSize() {
		return ignoreFilter.size();
	}
	
	public synchronized void addToIgnoreFilter(String ipOrMac) {
		ignoreFilter.add(ipOrMac);
	}
	
	public synchronized void removeFromIgnoreFilter(String ipOrMac) {
		ignoreFilter.remove(ipOrMac);
	}
	
	public final HashSet<String> getIgnoreFilter() {
		return ignoreFilter;
	}

	@Override
	public synchronized boolean removeIPAddress(String ipAddress) {
		return (ipMapC.remove(ipAddress) != null);
	}
}
