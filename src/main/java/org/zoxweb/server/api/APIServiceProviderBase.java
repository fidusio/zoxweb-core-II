package org.zoxweb.server.api;

import java.util.concurrent.atomic.AtomicLong;

import org.zoxweb.server.util.ServerUtil;
import org.zoxweb.shared.api.APIConfigInfo;
import org.zoxweb.shared.api.APIExceptionHandler;
import org.zoxweb.shared.api.APIServiceProvider;
import org.zoxweb.shared.data.DataConst.APIProperty;
import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public abstract class APIServiceProviderBase<V>
	implements APIServiceProvider<V>
{

	private long lastAccessTS;
	protected AtomicLong pendingCalls = new AtomicLong();
	private APIConfigInfo apiConfigInfo;
	private APIExceptionHandler apiExceptionHandler;

	protected APIServiceProviderBase()
	{
		touch();
	}

	/**
	 * Updates the last time object was used (milliseconds).
	 * @return
	 */
	protected synchronized long touch()
	{
		lastAccessTS = System.currentTimeMillis();
		return lastAccessTS;
	}
	
	/**
	 * Returns the last time it was accessed (milliseconds).
	 * @return the last it was accessed in millis
	 */
	public long lastTimeAccessed()
	{
		return lastAccessTS;
	}
	
	/**
	 * Return the delta between NOW and last time the object was used.
	 * @return the inactivity duration in millis
	 */
	public long inactivityDuration()
	{	
		return System.currentTimeMillis() - lastTimeAccessed();
	}

	@Override
	public APIConfigInfo getAPIConfigInfo()
	{
		return apiConfigInfo;
	}

	@Override
	public void setAPIConfigInfo(APIConfigInfo configInfo)
	{
		apiConfigInfo = configInfo;
	}
	
	
	@Override
	public APIExceptionHandler getAPIExceptionHandler()
	{
		return apiExceptionHandler;
	}

	@Override
	public void setAPIExceptionHandler(APIExceptionHandler exceptionHandler)
	{
		this.apiExceptionHandler = exceptionHandler;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupProperty(GetName propertyName) 
	{
		SharedUtil.checkIfNulls("Null property name", propertyName);

		if (propertyName instanceof APIProperty)
		{
			APIProperty apiProperty = (APIProperty) propertyName;

			switch(apiProperty)
			{
			case ASYNC_CREATE:
				return (T) Boolean.TRUE;
			case ASYNC_DELETE:
				break;
			case ASYNC_READ:
				break;
			case ASYNC_UPDATE:
				break;
			case RETRY_DELAY:
				return (T) new Long(Const.TimeInMillis.SECOND.MILLIS*ServerUtil.RNG.nextInt(4) + Const.TimeInMillis.SECOND.MILLIS*2);
				
			default:
				break;
			
			}
		}
		
		return null;
	}
	
	public boolean isBusy()
	{
		return pendingCalls.get() != 0;
	}

}