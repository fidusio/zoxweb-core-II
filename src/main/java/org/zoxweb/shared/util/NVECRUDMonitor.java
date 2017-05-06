package org.zoxweb.shared.util;

public interface NVECRUDMonitor 
{
	/**
	 * Applies CRUDNVEntity to session data cache.
	 * @param crudNVE
	 */
	public void monitorNVEntity(CRUDNVEntity crudNVE);
	
	/**
	 * Applies CRUDNVEntityList to session data cache.
	 * @param crudNVEList
	 */
	public void monitorNVEntity(CRUDNVEntityList crudNVEList);

}
