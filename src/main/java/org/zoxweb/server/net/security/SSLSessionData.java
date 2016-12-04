package org.zoxweb.server.net.security;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLSession;

import org.zoxweb.server.io.ByteBufferUtil;

public class SSLSessionData
	implements Closeable
{
	private static final boolean debug = true;
	
	private static final transient Logger log = Logger.getLogger(SSLSessionData.class.getName());
	
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
		if(debug) log.info("read start");
		try
		{
			if (sync)
				ioLock.lock();
			inSSLBuffer.clear();
			read = sc.read(inSSLBuffer);
			if (read > 0)
			{
				inSSLBuffer.flip();
				if(debug) log.info("before unwrap appBuffer " + appBuffer + " inSSLBuffer " + inSSLBuffer);
				SSLEngineResult res = sslEngine.unwrap(inSSLBuffer, appBuffer);
				
				SSLEngineResult.HandshakeStatus hStatus =  res.getHandshakeStatus();
				if(debug) log.info("after unwrap handshake:" + hStatus + " appBuffer " + appBuffer + " inSSLBuffer " + inSSLBuffer );
				
//				while(hStatus != null && 
//					  hStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING && 
//					  hStatus != SSLEngineResult.HandshakeStatus.FINISHED)
				{
					switch(res.getHandshakeStatus())
					{
					case NEED_TASK:
						hStatus = runDelegatedTasks(sslEngine);
						if(debug) log.info("run delegate:" + hStatus);
						break;
					case NEED_UNWRAP:
						break;
					case NEED_WRAP:
						inSSLBuffer.flip();
						res = sslEngine.wrap(inSSLBuffer, appBuffer);
						hStatus = res.getHandshakeStatus();
						if(res.getStatus() == SSLEngineResult.Status.OK)
						{
							ByteBufferUtil.write(sc, appBuffer);
						}
						
						
						break;
					default:
						break;
					
					}
				}
				
				
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
    private static SSLEngineResult.HandshakeStatus runDelegatedTasks(SSLEngine engine)
    		throws IOException 
    {

        //if (hStatus == HandshakeStatus.NEED_TASK) 
        {
            Runnable runnable;
            while ((runnable = engine.getDelegatedTask()) != null) 
            {
            	if(debug) log.info("Run delegate");
                runnable.run();
            }
            HandshakeStatus hsStatus = engine.getHandshakeStatus();
            if (hsStatus == HandshakeStatus.NEED_TASK) {
                throw new IOException(
                    "handshake shouldn't need additional tasks");
            }
            return hsStatus;
          
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
					runDelegatedTasks(sslEngine);
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





	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
