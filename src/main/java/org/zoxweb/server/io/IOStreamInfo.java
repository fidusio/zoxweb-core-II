package org.zoxweb.server.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOStreamInfo
	implements AutoCloseable
{
	public final InputStream is;
	public final OutputStream os;
	
	public IOStreamInfo(InputStream is, OutputStream os)
	{
		this.is = is;
		this.os = os;
	}
	
	public IOStreamInfo(Socket socket) throws IOException
	{
		this(socket.getInputStream(), socket.getOutputStream());
	}

	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub
		IOUtil.close(is);
		IOUtil.close(os);
	}

}
