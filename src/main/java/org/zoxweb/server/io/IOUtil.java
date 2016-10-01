package org.zoxweb.server.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

import org.zoxweb.shared.util.SharedStringUtil;

public class IOUtil 
{
	
	
	/**
	 * Close an AutoCloseable object if c is null the action is discarded, while closing catch any exception silently
	 * @param c
	 */
	public static void close(AutoCloseable c)
	{
		if (c != null)
		{
			try
			{
				c.close();
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public static IOException close(Closeable c)
	{
		if (c != null)
		{
			try
			{
				c.close();
			}
			catch(IOException e)
			{
				return e;
			}
		}
		
		return null;
	}
	
	
	/**
	 * This method will read all the response part of the url connection
	 * @param url to be read
	 * @return ByteArrayOutputStream.
	 * @throws IOException
	 */
	public static ByteArrayOutputStream readAllURLResponse(URL url)
		throws IOException
	{
		URLConnection con = (URLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		
		con.setUseCaches(false);
		con.connect();
		return inputStreamToByteArray(con.getInputStream(), true); 
	}	
	
	
	
	
	public static String inputStreamToString(InputStream is, String charsetEncoding, boolean close)
			throws IOException 
	{
		UByteArrayOutputStream baos = (UByteArrayOutputStream) inputStreamToByteArray(is, close);
		return new String( baos.getInternalBuffer(), 0 , baos.size(),charsetEncoding );
	}
	
	public static String inputStreamToString(InputStream is, boolean close) 
			throws IOException
	{
		byte buffer[] = new byte[4096];
		StringBuilder sb = new StringBuilder();
		int read;
		while ((read = is.read(buffer, 0, buffer.length)) > 0)
		{
			sb.append(new String(buffer, 0, read, SharedStringUtil.UTF_8));
		}
		
		if ( close)
		{
			close(is);
		}

		return sb.toString();
	}
	
	/**
	 * Read the all the content of an input stream and return it as a byte array
	 * @param is to be read
	 * @param close if true is will closed after reading
	 * @return byte array
	 * @throws IOException
	 */
	public static UByteArrayOutputStream inputStreamToByteArray(InputStream is, 
																boolean close) 
		throws IOException
	{
		UByteArrayOutputStream baos = new UByteArrayOutputStream();
		try
		{
			int read;
			byte buffer [] = new byte[4096];
			while((read = is.read( buffer)) != -1)
			{
				baos.write( buffer, 0, read);
			}
		}
		finally
		{
			if (close)
			{
				close(is);
			}
		}
		
		return baos;
	}
	
	public static void writeToFile(File f, byte buffer[]) throws IOException
	{
		FileOutputStream fos = null;
		
		try
		{
			fos = new FileOutputStream(f);
			fos.write(buffer);
		}
		finally
		{
			IOUtil.close(fos);
		}
				
	}
	
	
	
	
	/**
	 * Return the file creation in millis
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static long fileCreationTime(File file) throws IOException
	{
		if(!file.exists())
		{
			throw new FileNotFoundException();
		}
		return fileCreationTime(file.toPath());	
	}
	
	/**
	 * Return the file creation in millis
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static long fileCreationTime(Path path) throws IOException
	{
		BasicFileAttributes attributes =  Files.readAttributes(path, BasicFileAttributes.class);
		return attributes.creationTime().to(TimeUnit.MILLISECONDS);
	}
	
	
	public static File createDirectory(String dirName)
	{
		File dir = new File( dirName);
		if ( !dir.exists())
		{
			dir.mkdirs();
			
		}
		
		
		return dir;
	}
	
	
	public static long relayStreams(InputStream is, 
									OutputStream os, 
									boolean closeBoth)
	throws IOException
	{
		return relayStreams(is, os, closeBoth, closeBoth);
	}
	
	
	public static long relayStreams(InputStream is, 
									OutputStream os, 
									boolean closeIS,
									boolean closeOS) 
	throws IOException
	{
		long totalCopied = 0;
		try
		{	
			int read;
			byte buffer [] = new byte[4096];
			while((read = is.read( buffer)) != -1)
			{
				os.write(buffer, 0, read);
				totalCopied+=read;
			}
		}
		finally
		{
			if (closeIS || is instanceof CloseEnabledInputStream)
			{
				close( is);
			}
			
			if (closeOS || os instanceof CloseEnabledOutputStream)
			{
				close( os);
			}
		}
		
		
		return totalCopied;
		
		
	}
	
	
	/**
	 * This utility method will check if the output stream != null then invoke flush 
	 * @param os output stream 
	 * @throws IOException if os.flush() throw an exception
	 */
	public static void flush(OutputStream os) throws IOException
	{
		if (os != null)
		{
			os.flush();
		}
	}
}
