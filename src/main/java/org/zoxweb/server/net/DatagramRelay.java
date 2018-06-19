package org.zoxweb.server.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.logging.Logger;

import org.zoxweb.shared.util.DaemonController;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This a very simple and basic Datagram relay single threaded definitely not performant must be upgraded
 * how to use it
 * <code>
 * DatagramRelay dr = new DatagramRelay(53, "8.8.8.8", 53, 512);
 * //create a local dns relay to the google dns
 * new Thread(dr).start();
 * </code>
 * @author javaconsigliere
 *
 */
public class DatagramRelay
implements Runnable, DaemonController
{
	private static volatile Logger log = Logger.getLogger(DatagramRelay.class.getName());
	
	private InetAddress remoteHost;
	private int remotePort;
	private int packetSize;
	private DatagramSocket dsServer;
	private DatagramSocket dsRemote;
	private long requestCount = 0;
	
	/**
	 * Create a DatagramRelay.
	 * @param localPort local port. 
	 * @param remoteHost hostname of the server.
	 * @param remotePort port of the remote host
	 * @param packetSize in byte of the data buffer
	 * @throws IOException in case of error
	 */
	public DatagramRelay(int localPort, String remoteHost, int remotePort, int packetSize)
			throws IOException
	{
		this(localPort, InetAddress.getByName(remoteHost), remotePort, packetSize);
	}
	
	/**
	 * Create a DatagramRelay.
	 * @param localPort local port. 
	 * @param remoteHost hostname of the server.
	 * @param remotePort port of the remote host
	 * @param packetSize in byte of the data buffer
	 * @throws IOException in case of error
	 */
	public DatagramRelay(int localPort, InetAddress remoteHost, int remotePort, int packetSize)
			throws IOException
	{
	
		SharedUtil.checkIfNulls("null host", remoteHost);
		SharedUtil.illegalCondition("Invalid port values", (localPort > 0 && localPort < 65535 ), (remotePort > 0 && remotePort < 65535));
		SharedUtil.illegalCondition("Invalid packet size " + packetSize, (packetSize > 0 && packetSize < 65535 ));
		
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.packetSize = packetSize;
		dsServer = new DatagramSocket(localPort);
		dsRemote = new DatagramSocket();
		log.info("created@" + localPort +"<->" + remoteHost.getHostAddress() + ":" + remotePort +".Buffer Size:" + packetSize);
		
	}
	
	@Override
	public void run() 
	{
		log.info("started@" + dsServer.getLocalPort() +"<->" + remoteHost.getHostAddress() + ":" + remotePort);
		byte[] inBuffer = new byte[packetSize];
		byte[] outBuffer = new byte[packetSize];
		DatagramPacket inDP = new DatagramPacket(inBuffer, inBuffer.length);
		while(!dsServer.isClosed())
		{
			try
			{
				
				dsServer.receive(inDP);
				DatagramPacket received = sendToRemote(dsRemote, inDP, outBuffer, remoteHost, remotePort);
				received.setAddress(inDP.getAddress());
				received.setPort(inDP.getPort());
				dsServer.send(received);
				
				
				requestCount++;
			}
			catch (InterruptedIOException e) {
				continue;
			}
			catch(Exception e)
			{
				
			}
		}
		
	}

	@Override
	/**
	 * close and stop the relay
	 */
	public void close() throws IOException {
		// TODO Auto-generated method stub
		log.info("Closing@" + dsServer.getLocalPort() +"<->" + remoteHost.getHostAddress() + ":" + remotePort + " total requests:" + requestCount) ;
		dsServer.close();
	}

	/**
	 * Return the status
	 */
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return dsServer.isClosed();
	}
	
	private DatagramPacket sendToRemote(DatagramSocket ds, DatagramPacket incoming, byte buffer[], InetAddress remoteHost, int remotePort)
			throws IOException
	{
		

		
		DatagramPacket toSend = new DatagramPacket(incoming.getData(), incoming.getLength(), remoteHost, remotePort);
		ds.send(toSend);
		//ds.setSoTimeout(5000);
		Arrays.fill(buffer, (byte)0);
		DatagramPacket received = new DatagramPacket(buffer, buffer.length);
		//wait for the response
		ds.receive(received);
		return received;

	}
	
	public static void main(String ...args)
	{
		try
		{
			int index = 0;
			new Thread(new DatagramRelay(Integer.parseInt(args[index++]), args[index++], Integer.parseInt(args[index++]), Integer.parseInt(args[index++]))).start();
		}
		catch(Exception e)
		{
			System.err.println("usage: [localPort] [remoteHostName] [remotePort] [packet buffer size]");
			e.printStackTrace();
		}
	}

}
