/*
 * Copyright 2012 ZoxWeb.com LLC.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.server.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import org.zoxweb.server.io.IOUtil;

import org.zoxweb.server.net.NetUtil;
import org.zoxweb.shared.data.SystemInfoDAO;
import org.zoxweb.shared.net.NetworkInterfaceDAO;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;




/**
 * 
 * @author mnael
 * @version $Revision: 1.35 $
 * @lastModifiedBy $Author: mnael $
 * @lastModifiedAt $Date: 2015/12/24 03:58:18 $
 
 */
public final class ServerUtil 
{
	
	
	private ServerUtil()
	{
		
	}
	
	
	public final static SecureRandom RNG = new SecureRandom();
	
	
	public final static Lock LOCK = new ReentrantLock();
		
	
	/**
	 * This methods will concatenate two arrays as one
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> T[] concat(T[] first, T[] second) 
	{
		
		
		
		  if ( first != null && second != null)
		  {
			  T[] result = Arrays.copyOf(first, first.length + second.length);
			  System.arraycopy(second, 0, result, first.length, second.length);
			  return result;
		  }
		
		
		  if ( first == null && second != null)
		  {
			  return second;
		  }
		  
		  if ( first != null && second == null)
		  {
			  return first;
		  }
		  
		
		  
		  return null;
	}
	
	
	
	/**
	 * This method reads files based on the file name/location
	 * on the computer and stores the data in an array list of 
	 * strings.
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List<String> toStringList(String fileName) throws IOException
	{
	   
		List<String> ret = null;
		FileInputStream fis = null;
		
		try
		{
			fis = new FileInputStream(fileName);
			ret = toStringList(fis);
		}
		
		finally
		{
			IOUtil.close(fis);
		}
		
		return ret;
	    
	}
	
	
	/**
	 * This method is used to convert input streams to an
	 * array list of strings.
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static List<String> toStringList(InputStream inputStream) throws IOException
	{
	    
	    BufferedReader textReader = null;

	    ArrayList<String> messageList = new ArrayList<String>();

	    try
	    {
	    	textReader = new BufferedReader(new InputStreamReader(inputStream));
	    	String line = textReader.readLine();
		        
		        while (line != null) 
		        {
		            messageList.add(line);
		            line = textReader.readLine();
		        }
	    }

	    finally
	    {  	
	    	IOUtil.close(textReader);
	    	IOUtil.close(inputStream);
	    }
	           
	    return messageList;
	    
	}
	
	

	
	public static Object[] readXMLToBeans( InputStream is)
	{
		XMLDecoder decoder = new XMLDecoder(is);
		ArrayList<Object> ret = new ArrayList<Object>();
		
		
		try
		{
			do
			{
				ret.add(decoder.readObject());
				
				
			}while(true);
			
		}
		catch ( Exception e)
		{
			//e.printStackTrace();
		}
		IOUtil.close( decoder);
		IOUtil.close( is);
		
		return ret.toArray();
	}
	
	/**
	 * Write beans to XML file.
	 * @param os
	 * @param objs
	 */
	public static void writeBeansToXML( OutputStream os, Object... objs)
	{
		
		XMLEncoder enc = new XMLEncoder( os);
		
		for( Object o : objs)
		{
			enc.writeObject( o);
		}
		enc.flush();
		IOUtil.close( enc);
		IOUtil.close( os);
	}
	

	
	

	
	

	
	
	
	

	
	
	
	
	
	
	
	
	
	public static SystemInfoDAO loadSystemInfoDAO(boolean includeNetworkDetails) 
	{
		SystemInfoDAO ret = new SystemInfoDAO();
		Map.Entry<?, ?> all[] = System.getProperties().entrySet().toArray( new Map.Entry[0]);
		for ( Map.Entry<?, ?>e : all)
		{
			ret.getSystemProperties().add( new NVPair( (String)e.getKey(), (String)e.getValue()));
		}
		
		
		
		if (includeNetworkDetails)
		{
			try
			{
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			
			for (;interfaces.hasMoreElements();)
			{
				NetworkInterface ni = interfaces.nextElement();
				if ( !ni.isPointToPoint() && !ni.isLoopback() && !ni.isVirtual() && ni.getHardwareAddress() != null )
				{
					NetworkInterfaceDAO niDAO = new NetworkInterfaceDAO();
					niDAO.setMACAddress(SharedStringUtil.bytesToHex(ni.getHardwareAddress(), ":"));
					niDAO.setName( ni.getName());
					niDAO.setDisplayName( ni.getDisplayName());
				
				
					Enumeration<InetAddress> addresses = ni.getInetAddresses();
					while( addresses.hasMoreElements())
					{
						
						niDAO.getInetAddresses().add(NetUtil.toInetAddressDAO(addresses.nextElement()));
					}
					
					ret.getNetworkInterfaces().add(niDAO);
					
				}
				
			}
			}
			catch(IOException e)
			{
				
			}
		}
		
		return ret;
	}
	
	
	public static SystemInfoDAO loadSystemInfoDAO()
	{
		return loadSystemInfoDAO(true);
	}
	
	
	
	
	
	
	
	
	private static long delayInNanos(long nanos)
	{
		
		long stopAt = System.nanoTime() + nanos;
		
		
		do
		{
			
		}while(System.nanoTime() < stopAt);
		
		return System.nanoTime() - stopAt;
		
		
//		long ts =  System.nanoTime();
//		do
//		{
//			
//		}while(System.nanoTime() - ts < nanos);
//		
//		return System.nanoTime() - ts - nanos;
	}
	
	/**
	 * This method initiates a delay based on the specified time in
	 * nanoseconds. If the time is less than 1 millisecond, the program
	 * enters a while loop for the delay. Otherwise, the program calls 
	 * the sleep function based on the time length.
	 * @param timeToSleepNanos
	 * @return
	 */
	public static long delay(long timeToSleepNanos)
	{		
		//long lastTs = 0;
		
		//long start = System.nanoTime();
		
		if(timeToSleepNanos <= 1000000) 
		{
			return delayInNanos(timeToSleepNanos);
		}
		else 
		{
			final long endingTime = System.nanoTime() + timeToSleepNanos;
			long remainingTime = timeToSleepNanos;
			//while( remainingTime > 0)
			{
				long ms = remainingTime/1000000;
				int ns = (int) remainingTime % 1000000;
				if (ms > 0)
				{
					try 
					{
						Thread.sleep(ms, ns);
					} catch (InterruptedException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					delayInNanos(ns);
				}
					
				return System.nanoTime() - endingTime;
				}
		
			}

		//return System.nanoTime() - start;
	}
	
	public static boolean areAllInstancesMatchingType(List<?> list, Class<?> clazz)
	{		
		SharedUtil.checkIfNulls("Null list or class.", list, clazz);
		
		if (list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				if (list.get(i) != null && !clazz.isAssignableFrom(list.get(i).getClass()))
				{
					return false;
				}
			}
			
		}
		
		return true;
	}
	
	
	public static void main( String ... args)
	{
		try 
		{
			SystemInfoDAO siDAO = loadSystemInfoDAO();
			System.out.println(SharedUtil.toCanonicalID(':', siDAO.getName(), siDAO.getDescription()));
			System.out.println(siDAO.getSystemProperties());
			NVEntity[] allInterfaces = siDAO.getNetworkInterfaces().values();
			//Iterator<NetworkInterfaceDAO> it = (Iterator<NetworkInterfaceDAO> )allInterfaces.iterator();
			for (int i = 0; i < allInterfaces.length; i++)
			{
				NetworkInterfaceDAO niDAO = (NetworkInterfaceDAO) allInterfaces[i];
				System.out.println( SharedUtil.toCanonicalID( ',', i, niDAO.getName(), niDAO.getMACAddress()));
				System.out.println( niDAO.getInetAddresses());
			}
			System.out.println(GSONUtil.toJSON(siDAO, true));
			
			System.out.println(siDAO.getContent().getClass().getName());
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
}