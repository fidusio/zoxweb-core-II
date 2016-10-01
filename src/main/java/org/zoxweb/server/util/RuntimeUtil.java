/*
 * ====================================================================
 *
 * Copyright (c) 2001 Zoxsoft Corp.  All rights reserved.
 *
 * Originally written by Mori Uchida, Zoxsoft Corp., www.zoxsoft.com
 *
 * ====================================================================
 */

package org.zoxweb.server.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.shared.data.RuntimeResultDAO;
import org.zoxweb.shared.data.VMInfoDAO;
import org.zoxweb.shared.data.RuntimeResultDAO.ResultAttribute;



// import java.util.Vector;

/**
 * The UExec
 * 
 */
public class RuntimeUtil 
{
	

	/**
	 * Constructor
	 * 
	 */
	private RuntimeUtil() 
	{
	}

	/**
	 * Get the output of the command as string. IT will block until the
	 * executing process closes the stream.
	 * 
	 * @return the output of the command. If there is no output, "" is returned.
	 * @throws InterruptedException 
	 */
	public static String getRuntimeResponse(Process p, ResultAttribute ra ) 
			throws IOException, InterruptedException 
	{
		if (p == null)
			return "";
		
		
		InputStream is = null;
		switch( ra)
		{
		case OUTPUT:
			is = p.getInputStream();
			break;
		case ERROR:
			is = p.getErrorStream();
			break;
		case EXIT_CODE:
			p.waitFor();
			return ""+p.exitValue();

		
		}
		p.waitFor();
		
		return IOUtil.inputStreamToString(is, false);
		
	}

	/**
	 * This will execute a system command till it finishes.
	 * 
	 * @param command
	 *            to be executed.
	 * @return The execution result the process exit code and the output stream
	 */
	public static RuntimeResultDAO runAndFinish(String command)
			throws InterruptedException, IOException 
	{	
		return runAndFinish(command, ResultAttribute.OUTPUT);
	}
	
	
	/**
	 * This will execute a system command till it finishes.
	 * 
	 * @param command
	 *            to be executed.
	 * @return The execution result the process exit code and the output stream
	 */
	public static RuntimeResultDAO runAndFinish(String command, ResultAttribute ra)
			throws InterruptedException, IOException 
	{
		Process p = Runtime.getRuntime().exec(command);
		String ret = getRuntimeResponse(p, ra);
	
		return new RuntimeResultDAO(p.exitValue(), ret);
	}
	
	

	/**
	 * This method will create an executable file scripts based on the command and filename on the file system 
	 * and then execute it and command line.
	 * @param command
	 * @param filename
	 * @return the exceution result
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static RuntimeResultDAO runAndFinish(String command, String filename)
			throws InterruptedException, IOException 
	{
		return runAndFinish( command, new File(filename));
	}
	
	
	/**
	 * This method will create an executable file scripts based on the command and f on the file system 
	 * and then execute it and command line.
	 * @param command
	 * @param f
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static RuntimeResultDAO runAndFinish(String command, File f)
			throws InterruptedException, IOException 
	{
		f.createNewFile();
		f.setExecutable(true);
		IOUtil.writeToFile(f, command.getBytes());
		return runAndFinish(f.getCanonicalPath());
	}
	
	
	public static VMInfoDAO vmSnapshot()
	{
		Runtime rt = Runtime.getRuntime();
		VMInfoDAO ret = new VMInfoDAO();
		
		ret.setCoreCount(rt.availableProcessors());
		ret.setMaxMemory(rt.maxMemory());
		ret.setFreeMemory(rt.freeMemory());
		ret.setUsedMemory(rt.totalMemory() - rt.freeMemory());
		ret.setTotalMemory(rt.totalMemory());
		ret.setTimeStamp( new Date());
		return ret;
	}
	

}
