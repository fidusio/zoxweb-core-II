package org.zoxweb.server.net.security;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLSession;

import org.zoxweb.server.io.ByteBufferUtil;

public class SSLSessionData 
{
	private SSLSession sslSession;
	private SSLEngine sslEngine;
	private ByteBuffer outSSLBuffer;
	private ByteBuffer inSSLBuffer;
	private Lock ioLock = new ReentrantLock();
	
	
	SSLSessionData(SSLEngine sslEngine)
	{
		this.sslEngine = sslEngine;
		sslSession = sslEngine.getSession();
		outSSLBuffer = ByteBuffer.allocate(sslSession.getPacketBufferSize());
		inSSLBuffer  = ByteBuffer.allocate(sslSession.getPacketBufferSize());
	}
	
	
	
	
	
	public int getApplicationBufferSize()
	{
		return sslSession.getApplicationBufferSize();
	}
	
	public int read(SocketChannel sc, ByteBuffer appBuffer, boolean sync) throws IOException
	{
		int read;
		
		try
		{
			if (sync)
				ioLock.lock();
			
			read = sc.read(inSSLBuffer);
			if (read > 0)
			{
				inSSLBuffer.flip();
				SSLEngineResult res = sslEngine.unwrap(inSSLBuffer, appBuffer);
				SSLEngineResult.Status status = res.getStatus();
				if (status != null)
				{
					switch(status)
					{
					case BUFFER_OVERFLOW:
						break;
					case BUFFER_UNDERFLOW:
						break;
					case CLOSED:
						return -1;
					case OK:
						runDelegatedTasks(res.getHandshakeStatus(), sslEngine);
						inSSLBuffer.compact();
						read = appBuffer.position();
						break;
					
					
					}
				}
			}
		}
		finally
		{
			if (sync)
				ioLock.unlock();
		}
		
		return read;
	}
	
	
	/*
     * If the result indicates that we have outstanding tasks to do,
     * go ahead and run them in this thread.
     */
    private static void runDelegatedTasks(SSLEngineResult.HandshakeStatus hStatus,
            SSLEngine engine) throws IOException {

        if (hStatus == HandshakeStatus.NEED_TASK) {
            Runnable runnable;
            while ((runnable = engine.getDelegatedTask()) != null) {
                
                runnable.run();
            }
            HandshakeStatus hsStatus = engine.getHandshakeStatus();
            if (hsStatus == HandshakeStatus.NEED_TASK) {
                throw new IOException(
                    "handshake shouldn't need additional tasks");
            }
          
        }
    }
	
	public void write(SocketChannel sc, ByteBuffer appBuffer, boolean sync) throws IOException
	{
		
		try
		{
			if (sync)
				ioLock.lock();
			
			appBuffer.flip();	
			outSSLBuffer.clear();
			SSLEngineResult res = sslEngine.wrap(appBuffer, outSSLBuffer);
			SSLEngineResult.Status status = res.getStatus();
			if (status != null)
			{
				switch(status)
				{
				case BUFFER_OVERFLOW:
					break;
				case BUFFER_UNDERFLOW:
					break;
				case CLOSED:
					throw new IOException("Closed");
				case OK:
					runDelegatedTasks(res.getHandshakeStatus(), sslEngine);
					ByteBufferUtil.write(sc, outSSLBuffer);
					break;
				}
			}
		}
		finally
		{
			if(sync)
				ioLock.unlock();
		}
		
	
	}
	
}
