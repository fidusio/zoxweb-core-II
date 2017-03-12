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
	 * This method will update the last time object was used in millis second.
	 * @return
	 */
	protected synchronized long touch()
	{
		lastAccessTS = System.currentTimeMillis();
		return lastAccessTS;
	}
	
	/**
	 * This method will return last time it was used or accessed
	 * @return the last it was accessed in millis
	 */
	public long lastTimeAccessed()
	{
		return lastAccessTS;
	}
	
	/**
	 * This method will return the delta between NOW and last time the object was used
	 * @return the inactivity duration in millis
	 */
	public long inactivityDuration()
	{	
		return System.currentTimeMillis() - lastTimeAccessed();
	}
	
	
	
	@Override
	public APIConfigInfo getAPIConfigInfo()
	{
		// TODO Auto-generated method stub
		return apiConfigInfo;
	}

	@Override
	public void setAPIConfigInfo(APIConfigInfo configInfo) {
		// TODO Auto-generated method stub
		apiConfigInfo = configInfo;
	}
	
	
	@Override
	public APIExceptionHandler getAPIExceptionHandler()
	{
		// TODO Auto-generated method stub
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
		SharedUtil.checkIfNulls("Null porperty name", propertyName);
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
		
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isBusy()
	{
		return pendingCalls.get() != 0;
	}
	
	
	
	
}
