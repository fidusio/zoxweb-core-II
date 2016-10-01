package org.zoxweb.server.io;


import java.io.File;
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
	private RandomAccessFile bufferedReader;
	private long creationTime;
	private boolean autoRun;
	private EventListenerManager<?, StringTokenEvent> elm;
	
	public FileMonitor(String logToMonitor, EventListenerManager<?, StringTokenEvent> elm, boolean autoRun)
		throws NullPointerException, IllegalArgumentException, IOException
	{
		SharedUtil.checkIfNulls("Null variable", logToMonitor);
		// open file and got to the end
		file = new File(logToMonitor);
		if (!file.exists())
		{
			throw new FileNotFoundException(file.getName());			
		}
		
		if (!file.isFile())
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
	
	
	private RandomAccessFile openFile(File file) throws IOException
	{
		IOUtil.close(bufferedReader);
		creationTime = IOUtil.fileCreationTime(file);
		RandomAccessFile fileReader = new RandomAccessFile(file, "r");
		return fileReader;
			
	}
	
	
	private RandomAccessFile readTillEnd(RandomAccessFile br) throws IOException
	{
		br.seek(br.length());
		return br;
	}


	@Override
	public void run() 
	{
		
		
		try
		{
			bufferedReader = readTillEnd(bufferedReader);
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
						else
						{
							log.info("line:" + line);
						}
					}
					else 
					{
						
						try
						{
							if (creationTime != IOUtil.fileCreationTime(file))
							{
								bufferedReader = openFile(file);
								readTillEnd(bufferedReader);
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

			new FileMonitor(file, null, true);
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
