package org.zoxweb.server.task;


import java.util.UUID;
import java.util.concurrent.Executor;


import org.zoxweb.shared.util.GetDescription;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.LifeCycleMonitor;
import org.zoxweb.shared.util.SharedUtil;

public class ExecutorHolder<E extends Executor>
	implements Executor,
			   GetName,
	           GetDescription,
	           AutoCloseable
{

	protected final E es;
	private final String name;
	private final String description;
	protected final LifeCycleMonitor<ExecutorHolder<?>> lcm;
	private boolean closed = false;
	
	
	
	public ExecutorHolder(Executor es, LifeCycleMonitor<ExecutorHolder<?>> lcm)
	{
		this(es, lcm, null, null);
	}
	
	
	public ExecutorHolder(Executor es, LifeCycleMonitor<ExecutorHolder<?>> lcm, String name)
	{
		this(es, lcm, name, null);
		
	}
	
	@SuppressWarnings("unchecked")
	public ExecutorHolder(Executor es, LifeCycleMonitor<ExecutorHolder<?>> lcm, String name, String description)
	{
		SharedUtil.checkIfNulls("Executor or LifeCycleMonitor null", es, lcm);
		this.es = (E) es;
		this.name = name != null ? name : UUID.randomUUID().toString();
		this.description = description;
		this.lcm = lcm;
		if (!lcm.created(this))
		{
			throw new IllegalArgumentException("Creation failed for " + name);
		}
		
	}
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		es.execute(command);
	}
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (!closed)
		{
			synchronized(this)
			{
				if (!closed)
				{
					if(es instanceof AutoCloseable)
					{
						try 
						{
							((AutoCloseable)es).close();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					lcm.terminated(this);
				}
				
				closed = true;
			}
		}
	}

}
