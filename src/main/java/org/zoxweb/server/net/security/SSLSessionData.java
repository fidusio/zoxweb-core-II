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
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

import org.zoxweb.server.io.ByteBufferUtil;
import org.zoxweb.server.io.ByteBufferUtil.BufferType;

public class SSLSessionData
	implements Closeable
{
	private static final boolean debug = false;
	
	private static final transient Logger log = Logger.getLogger(SSLSessionData.class.getName());
	
	private SSLSession sslSession;
	private SSLEngine sslEngine;
	private ByteBuffer outSSLBuffer;
	private ByteBuffer inSSLBuffer;
	private Lock ioLock = new ReentrantLock();
	
	boolean firstRead = true;
	
	
	SSLSessionData(SSLEngine sslEngine)
	{
		this.sslEngine = sslEngine;
		sslSession = sslEngine.getSession();
		outSSLBuffer = ByteBuffer.allocate(sslSession.getPacketBufferSize());
		inSSLBuffer  = ByteBuffer.allocate(sslSession.getPacketBufferSize());
	}
	
	
	public int minBufferSize()
	{
		return sslSession.getPacketBufferSize();
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
			
			if (firstRead)
			{
				firstRead = false;
				sslEngine.beginHandshake();
				doHandshake(sc, sslEngine);
				return 0;
			}
			
			
			
			inSSLBuffer.compact();
			read = sc.read(inSSLBuffer);
			if (read > 0)
			{
				inSSLBuffer.flip();
				if(debug) log.info("before unwrap appBuffer " + appBuffer + " inSSLBuffer " + inSSLBuffer);
				SSLEngineResult res = sslEngine.unwrap(inSSLBuffer, appBuffer);
				
				SSLEngineResult.HandshakeStatus hStatus =  res.getHandshakeStatus();
				if(debug) log.info("after unwrap handshake:" + hStatus + " appBuffer " + appBuffer + " inSSLBuffer " + inSSLBuffer );
				
				while(hStatus != null && 
					  hStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING && 
					  hStatus != SSLEngineResult.HandshakeStatus.FINISHED)
				{
					switch(hStatus)
					{
					case NEED_TASK:
						hStatus = runDelegatedTasks(sslEngine);
						if(debug) log.info("run delegate:" + hStatus);
						break;
					case NEED_UNWRAP:
						if(debug) log.info("need unwrap " + res + " " + appBuffer);
						inSSLBuffer.compact();
						read = sc.read(inSSLBuffer);
						if (read > 0)
						{
							res = sslEngine.unwrap(inSSLBuffer, appBuffer);
							if(debug) log.info("insisde the unwrap " + res + " " + appBuffer);
							hStatus = res.getHandshakeStatus();
							
						} 
						else
						{
							return read;
						}
						break;
												
					case NEED_WRAP:
						appBuffer.clear();
						
						//ByteBuffer temp = ByteBuffer.allocate((int) (Const.SizeInBytes.K.LENGTH*64));
						res = sslEngine.wrap(inSSLBuffer, appBuffer);
						hStatus = sslEngine.getHandshakeStatus();
						if(debug) log.info("after wrap " + res + " " + appBuffer);
						//appBuffer = adjustBuffer(appBuffer, minBufferSize(), res.getStatus());
						//if(debug) log.info("after adjust " + appBuffer);
					
					
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
						return 0;
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
    public static SSLEngineResult.HandshakeStatus runDelegatedTasks(SSLEngine engine)
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
    
    
    public static ByteBuffer adjustBuffer(ByteBuffer bb, int minCapacity, SSLEngineResult.Status status) throws IOException
    {
    	if (bb != null && status != null)
    	{
	    	switch(status)
	    	{
			case BUFFER_OVERFLOW:
				// we must increase the buffer size by doubling
				int capacity = bb.capacity() >= minCapacity ? bb.capacity() * 2 : minCapacity;
				ByteBuffer temp = ByteBufferUtil.allocateByteBuffer(BufferType.HEAP, capacity);
				bb.flip();
				temp.put(bb);
				return temp;
			case BUFFER_UNDERFLOW:
				// not sure what to do here
				break;
			case CLOSED:
				throw new IOException("Closed");
			case OK:
				// do nothing
				break;	
	    	}
    	}
    	
    	return bb;
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


	
	
    protected boolean doHandshake(SocketChannel socketChannel, SSLEngine engine) throws IOException {

  
    	if (debug) log.info("doHandshake");
        SSLEngineResult result;
        HandshakeStatus handshakeStatus;

        // NioSslPeer's fields myAppData and peerAppData are supposed to be large enough to hold all message data the peer
        // will send and expects to receive from the other peer respectively. Since the messages to be exchanged will usually be less
        // than 16KB long the capacity of these fields should also be smaller. Here we initialize these two local buffers
        // to be used for the handshake, while keeping client's buffers at the same size.
        int appBufferSize = engine.getSession().getApplicationBufferSize();
        ByteBuffer myAppData = ByteBuffer.allocate(appBufferSize);
        ByteBuffer peerAppData = ByteBuffer.allocate(appBufferSize);
        ByteBuffer myNetData = ByteBuffer.allocate(appBufferSize);
        ByteBuffer peerNetData = ByteBuffer.allocate(appBufferSize);
        myNetData.clear();
        peerNetData.clear();

        handshakeStatus = engine.getHandshakeStatus();
        while (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED && handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            switch (handshakeStatus) {
            case NEED_UNWRAP:
                if (socketChannel.read(peerNetData) < 0) {
                    if (engine.isInboundDone() && engine.isOutboundDone()) {
                        return false;
                    }
                    try {
                        engine.closeInbound();
                    } catch (SSLException e) {
                        //log.error("This engine was forced to close inbound, without having received the proper SSL/TLS close notification message from the peer, due to end of stream.");
                    }
                    engine.closeOutbound();
                    // After closeOutbound the engine will be set to WRAP state, in order to try to send a close message to the client.
                    handshakeStatus = engine.getHandshakeStatus();
                    break;
                }
                peerNetData.flip();
                try {
                    result = engine.unwrap(peerNetData, peerAppData);
                    peerNetData.compact();
                    handshakeStatus = result.getHandshakeStatus();
                } catch (SSLException sslException) {
                    if (debug)log.info("A problem was encountered while processing the data that caused the SSLEngine to abort. Will try to properly close connection...");
                    engine.closeOutbound();
                    handshakeStatus = engine.getHandshakeStatus();
                    break;
                }
                switch (result.getStatus()) {
                case OK:
                    break;
                case BUFFER_OVERFLOW:
                    // Will occur when peerAppData's capacity is smaller than the data derived from peerNetData's unwrap.
                    peerAppData = enlargeApplicationBuffer(engine, peerAppData);
                    break;
                case BUFFER_UNDERFLOW:
                    // Will occur either when no data was read from the peer or when the peerNetData buffer was too small to hold all peer's data.
                    peerNetData = handleBufferUnderflow(engine, peerNetData);
                    break;
                case CLOSED:
                    if (engine.isOutboundDone()) {
                        return false;
                    } else {
                        engine.closeOutbound();
                        handshakeStatus = engine.getHandshakeStatus();
                        break;
                    }
                default:
                    throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
                }
                break;
            case NEED_WRAP:
                myNetData.clear();
                try {
                    result = engine.wrap(myAppData, myNetData);
                    handshakeStatus = result.getHandshakeStatus();
                } catch (SSLException sslException) {
                    if (debug) log.info("A problem was encountered while processing the data that caused the SSLEngine to abort. Will try to properly close connection...");
                    engine.closeOutbound();
                    handshakeStatus = engine.getHandshakeStatus();
                    break;
                }
                switch (result.getStatus()) {
                case OK :
                    myNetData.flip();
                    while (myNetData.hasRemaining()) {
                        socketChannel.write(myNetData);
                    }
                    break;
                case BUFFER_OVERFLOW:
                    // Will occur if there is not enough space in myNetData buffer to write all the data that would be generated by the method wrap.
                    // Since myNetData is set to session's packet size we should not get to this point because SSLEngine is supposed
                    // to produce messages smaller or equal to that, but a general handling would be the following:
                    myNetData = enlargePacketBuffer(engine, myNetData);
                    break;
                case BUFFER_UNDERFLOW:
                    throw new SSLException("Buffer underflow occured after a wrap. I don't think we should ever get here.");
                case CLOSED:
                    try {
                        myNetData.flip();
                        while (myNetData.hasRemaining()) {
                            socketChannel.write(myNetData);
                        }
                        // At this point the handshake status will probably be NEED_UNWRAP so we make sure that peerNetData is clear to read.
                        peerNetData.clear();
                    } catch (Exception e) {
                        if (debug) log.info("Failed to send server's CLOSE message due to socket channel's failure.");
                        handshakeStatus = engine.getHandshakeStatus();
                    }
                    break;
                default:
                    throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
                }
                break;
            case NEED_TASK:
                Runnable task;
                while ((task = engine.getDelegatedTask()) != null) {
                    task.run();
                }
                handshakeStatus = engine.getHandshakeStatus();
                break;
            case FINISHED:
                break;
            case NOT_HANDSHAKING:
                break;
            default:
                throw new IllegalStateException("Invalid SSL status: " + handshakeStatus);
            }
        }

        return true;

    }

    protected static ByteBuffer handleBufferUnderflow(SSLEngine engine, ByteBuffer buffer) {
        if (engine.getSession().getPacketBufferSize() < buffer.limit()) {
            return buffer;
        } else {
            ByteBuffer replaceBuffer = enlargePacketBuffer(engine, buffer);
            buffer.flip();
            replaceBuffer.put(buffer);
            return replaceBuffer;
        }
    }
    
    protected static ByteBuffer enlargePacketBuffer(SSLEngine engine, ByteBuffer buffer) {
        return enlargeBuffer(buffer, engine.getSession().getPacketBufferSize());
    }

    protected static ByteBuffer enlargeApplicationBuffer(SSLEngine engine, ByteBuffer buffer) {
        return enlargeBuffer(buffer, engine.getSession().getApplicationBufferSize());
    }

    /**
     * Compares <code>sessionProposedCapacity<code> with buffer's capacity. If buffer's capacity is smaller,
     * returns a buffer with the proposed capacity. If it's equal or larger, returns a buffer
     * with capacity twice the size of the initial one.
     *
     * @param buffer - the buffer to be enlarged.
     * @param sessionProposedCapacity - the minimum size of the new buffer, proposed by {@link SSLSession}.
     * @return A new buffer with a larger capacity.
     */
    protected static ByteBuffer enlargeBuffer(ByteBuffer buffer, int sessionProposedCapacity) {
        if (sessionProposedCapacity > buffer.capacity()) {
            buffer = ByteBuffer.allocate(sessionProposedCapacity);
        } else {
            buffer = ByteBuffer.allocate(buffer.capacity() * 2);
        }
        return buffer;
    }


	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
