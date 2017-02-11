package org.zoxweb.server.io;


import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;

import org.zoxweb.server.task.RunnableTask;
import org.zoxweb.shared.util.DaemonController;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.data.events.EventListenerManager;
import org.zoxweb.shared.data.events.StringTokenEvent;
import org.zoxweb.shared.util.Const.TimeInMillis;



public class FileMonitor extends RunnableTask
implements DaemonController
{
	private static final transient Logger log = Logger.getLogger(FileMonitor.class.getName());
	
	
	private File file;
	private DataInput bufferedReader;
	
	private long creationTime;
	private boolean autoRun;
	private boolean device;
	private EventListenerManager<?, StringTokenEvent> elm;
	
	
	
	public FileMonitor(String logToMonitor, EventListenerManager<?, StringTokenEvent> elm, boolean autoRun)
			throws NullPointerException, IllegalArgumentException, IOException
	{
		this(logToMonitor, elm, autoRun, false);
	}
	
	public FileMonitor(String logToMonitor, EventListenerManager<?, StringTokenEvent> elm, boolean autoRun, boolean device)
		throws NullPointerException, IllegalArgumentException, IOException
	{
		SharedUtil.checkIfNulls("Null variable", logToMonitor);
		// open file and got to the end
		file = new File(logToMonitor);
		this.device = device;
		if (!file.exists())
		{
			throw new FileNotFoundException(file.getName());			
		}
		
		if (!file.isFile() && !device)
		{
			throw new IllegalArgumentException(file.getName() + " is not a file");
		}
		bufferedReader = openFile(file);
		
		this.elm = elm;
		this.autoRun = autoRun;
		

		
		if (autoRun)
		{
			new Thread(this).start();
		}
	}
	
	
	private DataInput openFile(File file) throws IOException
	{
		if (bufferedReader instanceof Closeable)
			IOUtil.close((Closeable)bufferedReader);
		if (device)
		{
			creationTime = System.currentTimeMillis();
		}
		else
			creationTime = IOUtil.fileCreationTime(file);
		if (device)
			return new DataInputStream( new FileInputStream(file));
		else 
			return  new RandomAccessFile(file, "r");

			
	}
	
	
	


	@Override
	public void run() 
	{
		
		
		try
		{
			if (!device)
				bufferedReader = IOUtil.endOfFile((RandomAccessFile)bufferedReader);
			do
			{
				try
				{
					
					String line = bufferedReader.readLine();
					
					if (line != null)
					{
						// we must poccess the line
						
						if (elm != null)
						{
							elm.dispatch(new StringTokenEvent(this, line));
						}
						else if(!line.trim().isEmpty())
						{
							log.info(line);
						}
					}
					else 
					{
						
						try
						{
							
							if (!device && creationTime != IOUtil.fileCreationTime(file))
							{
								bufferedReader = openFile(file);
								IOUtil.endOfFile((RandomAccessFile)bufferedReader);
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						synchronized(this)
						{
							try
							{
								wait(TimeInMillis.MILLI.MILLIS*250);
							}
							catch(InterruptedException e)
							{
								
							}
						}
					}
					
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}while(autoRun);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String ...args)
	{
		try
		{
			int index = 0;
			String file = args[index++];
			boolean device = false;
			if (index < args.length)
			{
				device = "dev".equalsIgnoreCase(args[index++]);
			}

			new FileMonitor(file, null, true, device);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("TrafficMonitor fileToMonitor tokens");
		}
	}


	@Override
	public void close() 
	{
		// TODO Auto-generated method stub
		autoRun = false;
	}


	@Override
	public boolean isClosed() 
	{
		// TODO Auto-generated method stub
		return !autoRun;
	}
}
