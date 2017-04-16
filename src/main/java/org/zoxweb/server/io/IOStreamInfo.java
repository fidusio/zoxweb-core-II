package org.zoxweb.server.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOStreamInfo
	implements AutoCloseable
{

	public volatile InputStream is;
	public volatile OutputStream os;
	public volatile Socket s;
	
	public IOStreamInfo(InputStream is, OutputStream os)
	{
		this.is = is;
		this.os = os;
	}
	
	public IOStreamInfo(Socket socket)
		throws IOException
	{
		this(socket.getInputStream(), socket.getOutputStream());
		s = socket;
	}
	
	public IOStreamInfo(Socket socket, int timeout)
		throws IOException
	{
		this(socket.getInputStream(), socket.getOutputStream());
		socket.setSoTimeout(timeout);
	}

	@Override
	public void close()
		throws IOException
	{
		IOUtil.close(is);
		IOUtil.close(os);
		IOUtil.close(s);
	}

}
