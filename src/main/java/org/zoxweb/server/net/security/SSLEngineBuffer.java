/*
 * 
 */
package org.zoxweb.server.net.security;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLSession;

import org.zoxweb.server.io.ByteBufferUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class SSLEngineBuffer
{
  private final SocketChannel socketChannel;

  private final SSLEngine sslEngine;

 

  private final ByteBuffer networkInboundBuffer;

  private final ByteBuffer networkOutboundBuffer;

  private final int minimumApplicationBufferSize;

  private final ByteBuffer unwrapBuffer;

  private final ByteBuffer wrapBuffer;

  private final Logger log;

  private final boolean logDebug;
  
 
  
  
  

  protected SSLEngineBuffer(SocketChannel socketChannel, SSLEngine sslEngine, Logger log)
  {
    this.socketChannel = socketChannel;
    this.sslEngine = sslEngine;
   
    this.log = log;

    logDebug = log != null;

    SSLSession session = sslEngine.getSession();
    int networkBufferSize = session.getPacketBufferSize();

    networkInboundBuffer = ByteBuffer.allocate(networkBufferSize);

    networkOutboundBuffer = ByteBuffer.allocate(networkBufferSize);
    networkOutboundBuffer.flip();


    minimumApplicationBufferSize = session.getApplicationBufferSize();
    unwrapBuffer = ByteBuffer.allocate(minimumApplicationBufferSize);
    wrapBuffer = ByteBuffer.allocate(minimumApplicationBufferSize);
    wrapBuffer.flip();
  }

  int unwrap(ByteBuffer applicationInputBuffer) throws IOException
  {
    if (applicationInputBuffer.capacity() < minimumApplicationBufferSize)
    {
      throw new IllegalArgumentException("Application buffer size must be at least: " + minimumApplicationBufferSize);
    }

    if (unwrapBuffer.position() != 0)
    {
      unwrapBuffer.flip();
      while (unwrapBuffer.hasRemaining() && applicationInputBuffer.hasRemaining())
      {
        applicationInputBuffer.put(unwrapBuffer.get());
      }
      unwrapBuffer.compact();
    }

    int totalUnwrapped = 0;
    int unwrapped, wrapped;

    do
    {
      totalUnwrapped += unwrapped = doUnwrap(applicationInputBuffer);
      wrapped = doWrap(wrapBuffer);
    }
    while (unwrapped > 0 || wrapped > 0 && (networkOutboundBuffer.hasRemaining() && networkInboundBuffer.hasRemaining()));

    return totalUnwrapped;
  }

  int wrap(ByteBuffer applicationOutboundBuffer) throws IOException
  {
    int wrapped = doWrap(applicationOutboundBuffer);
    doUnwrap(unwrapBuffer);
    return wrapped;
  }

  int flushNetworkOutbound() throws IOException
  {
    return send(socketChannel, networkOutboundBuffer);
  }

  int send(SocketChannel channel, ByteBuffer buffer) throws IOException
  {
    int totalWritten = 0;
    while (buffer.hasRemaining())
    {
      int written = channel.write(buffer);

      if (written == 0)
      {
        break;
      }
      else if (written < 0)
      {
        return (totalWritten == 0) ? written : totalWritten;
      }
      totalWritten += written;
    }
    if (logDebug) log.info("sent: " + totalWritten + " out to socket");
    return totalWritten;
	  
	 
  }

  void close()
  {
    try
    {
      sslEngine.closeInbound();
    }
    catch (Exception e)
    {}

    try
    {
      sslEngine.closeOutbound();
    }
    catch (Exception e)
    {}
  }
  
  
  public static SSLEngineBuffer init(SSLContext sslContext, 
									 SocketChannel channel, 
									 boolean useClientMode,
									 boolean wantClientAuthentication,
									 boolean needClientAuthentication,
									 Logger logger) throws IOException
	{
	
		//channel.configureBlocking(blockingMode);
		
		SSLEngine sslEngine = sslContext.createSSLEngine();
		sslEngine.setUseClientMode(useClientMode);
	
		//sslEngine.setWantClientAuth(wantClientAuthentication);
		//sslEngine.setNeedClientAuth(needClientAuthentication);
		//sslEngine.setEnabledProtocols(filterArray(sslEngine.getEnabledProtocols(), includedProtocols, excludedProtocols));
		//sslEngine.setEnabledCipherSuites(filterArray(sslEngine.getEnabledCipherSuites(), includedCipherSuites, excludedCipherSuites));
		return new SSLEngineBuffer(channel, sslEngine, logger);
		
	}
  
  
  public long read(ByteBuffer[] applicationByteBuffers, int offset, int length) throws IOException, IllegalArgumentException
  {
    long totalRead = 0;
    for (int i = offset; i < length; i++)
    {
      ByteBuffer applicationByteBuffer = applicationByteBuffers[i];
      if (applicationByteBuffer.hasRemaining())
      {
        int read = read(applicationByteBuffer);
        if (read > 0)
        {
          totalRead += read;
          if (applicationByteBuffer.hasRemaining())
          {
            break;
          }
        }
        else
        {
          if ((read < 0) && (totalRead == 0))
          {
            totalRead = -1;
          }
          break;
        }
      }
    }
    return totalRead;
  }
  
  
  /**
   * <p>Writes a sequence of bytes to this channel from the given buffer.</p>
   *
   * <p>An attempt is made to write up to r bytes to the channel, where r is the number of bytes remaining in the buffer, that is, src.remaining(), at the moment this method is invoked.</p>
   *
   * <p>Suppose that a byte sequence of length n is written, where 0 <= n <= r. This byte sequence will be transferred from the buffer starting at index p, where p is the buffer's position at the moment this method is invoked; the index of the last byte written will be p + n - 1. Upon return the buffer's position will be equal to p + n; its limit will not have changed.</p>
   *
   * <p>Unless otherwise specified, a write operation will return only after writing all of the r requested bytes. Some types of channels, depending upon their state, may write only some of the bytes or possibly none at all. A socket channel in non-blocking mode, for example, cannot write any more bytes than are free in the socket's output buffer.</p>
   *
   * <p>This method may be invoked at any time. If another thread has already initiated a write operation upon this channel, however, then an invocation of this method will block until the first operation is complete.</p>
   *
   * @param applicationBuffer The buffer from which bytes are to be retrieved
   * @return The number of bytes written, possibly zero
   * @throws java.nio.channels.NotYetConnectedException If this channel is not yet connected
   * @throws java.nio.channels.ClosedChannelException If this channel is closed
   * @throws java.nio.channels.AsynchronousCloseException If another thread closes this channel while the read operation is in progress
   * @throws java.nio.channels.ClosedByInterruptException If another thread interrupts the current thread while the read operation is in progress, thereby closing the channel and setting the current thread's interrupt status
   * @throws IOException If some other I/O error occurs
   * @throws IllegalArgumentException If the given applicationBuffer capacity ({@link ByteBuffer#capacity()} is less then the application buffer size of the {@link SSLEngine} session application buffer size ({@link SSLSession#getApplicationBufferSize()} this channel was constructed was.
   */

  synchronized public int write(ByteBuffer applicationBuffer) throws IOException, IllegalArgumentException
  {
    if (logDebug) log.info("write:");

    int intialPosition = applicationBuffer.position();
    int writtenToChannel = wrap(applicationBuffer);

    if (writtenToChannel < 0)
    {
      if (logDebug) log.info("write: channel closed");
      return writtenToChannel;
    }
    else
    {
      int totalWritten = applicationBuffer.position() - intialPosition;
      if (logDebug) log.info("write: total written: " + totalWritten + " amount available in network outbound: " + applicationBuffer.remaining());
      return totalWritten;
    }
  }
  
  /**
   * <p>Writes a sequence of bytes to this channel from a subsequence of the given buffers.</p>
   *
   * <p>An attempt is made to write up to r bytes to this channel, where r is the total number of bytes remaining in the specified subsequence of the given buffer array, that is,</p>
   * <pre>
   * {@code
   * srcs[offset].remaining()
   *   + srcs[offset+1].remaining()
   *   + ... + srcs[offset+length-1].remaining()
   * }
   * </pre>
   * <p>at the moment that this method is invoked.</p>
   *
   * <p>Suppose that a byte sequence of length n is written, where 0 <= n <= r. Up to the first srcs[offset].remaining() bytes of this sequence are written from buffer srcs[offset], up to the next srcs[offset+1].remaining() bytes are written from buffer srcs[offset+1], and so forth, until the entire byte sequence is written. As many bytes as possible are written from each buffer, hence the final position of each updated buffer, except the last updated buffer, is guaranteed to be equal to that buffer's limit.</p>
   *
   * <p>Unless otherwise specified, a write operation will return only after writing all of the r requested bytes. Some types of channels, depending upon their state, may write only some of the bytes or possibly none at all. A socket channel in non-blocking mode, for example, cannot write any more bytes than are free in the socket's output buffer.</p>
   *
   * <p>This method may be invoked at any time. If another thread has already initiated a write operation upon this channel, however, then an invocation of this method will block until the first operation is complete.</p>
   *
   * @param applicationByteBuffers The buffers from which bytes are to be retrieved
   * @param offset offset - The offset within the buffer array of the first buffer from which bytes are to be retrieved; must be non-negative and no larger than <code>srcs.length</code>
   * @param length The maximum number of buffers to be accessed; must be non-negative and no larger than <code>srcs.length - offset</code>
   * @return The number of bytes written, possibly zero
   * @throws java.nio.channels.NotYetConnectedException If this channel is not yet connected
   * @throws java.nio.channels.ClosedChannelException If this channel is closed
   * @throws java.nio.channels.AsynchronousCloseException If another thread closes this channel while the read operation is in progress
   * @throws java.nio.channels.ClosedByInterruptException If another thread interrupts the current thread while the read operation is in progress, thereby closing the channel and setting the current thread's interrupt status
   * @throws IOException If some other I/O error occurs
   * @throws IllegalArgumentException If one of the given applicationBuffers capacity ({@link ByteBuffer#capacity()} is less then the application buffer size of the {@link SSLEngine} session application buffer size ({@link SSLSession#getApplicationBufferSize()} this channel was constructed was.
   */
  
  public long write(ByteBuffer[] applicationByteBuffers, int offset, int length) throws IOException, IllegalArgumentException
  {
    long totalWritten = 0;
    for (int i = offset; i < length; i++)
    {
      ByteBuffer byteBuffer = applicationByteBuffers[i];
      if (byteBuffer.hasRemaining())
      {
        int written = write(byteBuffer);
        if (written > 0)
        {
          totalWritten += written;
          if (byteBuffer.hasRemaining())
          {
            break;
          }
        }
        else
        {
          if ((written < 0) && (totalWritten == 0))
          {
            totalWritten = -1;
          }
          break;
        }
      }
    }
    return totalWritten;
  }

  
  synchronized public int read(ByteBuffer applicationBuffer) throws IOException, IllegalArgumentException
  {
    if (logDebug) log.info("read: " + applicationBuffer.position() + " " + applicationBuffer.limit());
    int intialPosition = applicationBuffer.position();

    int readFromChannel = unwrap(applicationBuffer);
    if (logDebug) log.info("read: from channel: " + readFromChannel);

    if (readFromChannel < 0)
    {
      if (logDebug) log.info("read: channel closed.");
      return readFromChannel;
    }
    else
    {
      int totalRead = applicationBuffer.position() - intialPosition;
      if (logDebug) log.info("read: total read: " + totalRead);
      return totalRead;
    }
  }

  private int doUnwrap(ByteBuffer applicationInputBuffer) throws IOException
  {
    if (logDebug) log.info("unwrap:");

    int totalReadFromChannel = 0;

    // Keep looping until peer has no more data ready or the applicationInboundBuffer is full
    UNWRAP: do
    {
      // 1. Pull data from peer into networkInboundBuffer

      int readFromChannel = 0;
      while (networkInboundBuffer.hasRemaining())
      {
        int read = socketChannel.read(networkInboundBuffer);
        if (logDebug) log.info("unwrap: socket read " + read + "(" + readFromChannel + ", " + totalReadFromChannel + ")");
        if (read <= 0)
        {
          if ((read < 0) && (readFromChannel == 0) && (totalReadFromChannel == 0))
          {
            // No work done and we've reached the end of the channel from peer
            if (logDebug) log.info("unwrap: exit: end of channel");
            return read;
          }
          break;
        }
        else
        {
          readFromChannel += read;
        }
      }


      networkInboundBuffer.flip();
      if (!networkInboundBuffer.hasRemaining())
      {
        networkInboundBuffer.compact();
        //wrap(applicationOutputBuffer, applicationInputBuffer, false);
        return totalReadFromChannel;
      }

      totalReadFromChannel += readFromChannel;

      try
      {
        SSLEngineResult result = sslEngine.unwrap(networkInboundBuffer, applicationInputBuffer);
        if (logDebug) log.info("unwrap: result: " + result);

        switch (result.getStatus())
        {
          case OK:
            SSLEngineResult.HandshakeStatus handshakeStatus = result.getHandshakeStatus();
            switch (handshakeStatus)
            {
              case NEED_UNWRAP:
                break;

              case NEED_WRAP:
                break UNWRAP;

              case NEED_TASK:
                runHandshakeTasks();
                break;

              case NOT_HANDSHAKING:
              default:
                break;
            }
            break;

          case BUFFER_OVERFLOW:
            if (logDebug) log.info("unwrap: buffer overflow");
            break UNWRAP;

          case CLOSED:
            if (logDebug) log.info("unwrap: exit: ssl closed");
            return totalReadFromChannel == 0 ? -1 : totalReadFromChannel;

          case BUFFER_UNDERFLOW:
            if (logDebug) log.info("unwrap: buffer underflow");
            break;
        }
      }
      finally
      {
        networkInboundBuffer.compact();
      }
    }
    while (applicationInputBuffer.hasRemaining());

    return totalReadFromChannel;
  }

  private int doWrap(ByteBuffer applicationOutboundBuffer) throws IOException
  {
    if (logDebug) log.info("wrap:");
    int totalWritten = 0;

    // 1. Send any data already wrapped out channel

    if (networkOutboundBuffer.hasRemaining())
    {
      totalWritten = send(socketChannel, networkOutboundBuffer);
      if (totalWritten < 0)
      {
        return totalWritten;
      }
    }

    // 2. Any data in application buffer ? Wrap that and send it to peer.

    WRAP: while (true)
    {
      networkOutboundBuffer.compact();
      SSLEngineResult result = sslEngine.wrap(applicationOutboundBuffer, networkOutboundBuffer);
      if (logDebug) log.info("wrap: result: " + result);

      networkOutboundBuffer.flip();
      if (networkOutboundBuffer.hasRemaining())
      {
        int written = send(socketChannel, networkOutboundBuffer);
        if (written < 0)
        {
          return totalWritten == 0 ? written : totalWritten;
        }
        else
        {
          totalWritten += written;
        }
      }

      switch (result.getStatus())
      {
        case OK:
          switch (result.getHandshakeStatus())
          {
            case NEED_WRAP:
              break;

            case NEED_UNWRAP:
              break WRAP;

            case NEED_TASK:
              runHandshakeTasks();
              if (logDebug) log.info("wrap: exit: need tasks");
              break;

            case NOT_HANDSHAKING:
              if (applicationOutboundBuffer.hasRemaining())
              {
                break;
              }
              else
              {
                break WRAP;
              }
            case FINISHED:
            	// not present 
            	break;
          }
          break;

        case BUFFER_OVERFLOW:
          if (logDebug) log.info("wrap: exit: buffer overflow");
          break WRAP;

        case CLOSED:
          if (logDebug) log.info("wrap: exit: closed");
          break WRAP;

        case BUFFER_UNDERFLOW:
          if (logDebug) log.info("wrap: exit: buffer underflow");
          break WRAP;
      }
    }

    if (logDebug) log.info("wrap: return: " + totalWritten);
    return totalWritten;
  }

  private void runHandshakeTasks ()
  {
    while (true)
    {
      final Runnable runnable = sslEngine.getDelegatedTask();
      if (runnable == null)
      {
        break;
      }
      else
      {
    	  new Thread(runnable).start();
      }
    }
  }
}
