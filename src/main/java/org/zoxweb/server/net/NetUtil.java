/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
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
package org.zoxweb.server.net;

import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Properties;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.shared.net.ConnectionPropDAO;
import org.zoxweb.shared.net.InetAddressDAO;
import org.zoxweb.shared.net.InetFilterDAO;
import org.zoxweb.shared.net.InetProp.NICategory;
import org.zoxweb.shared.net.InetProp.NIStatus;
import org.zoxweb.shared.net.InetProp.NIType;
import org.zoxweb.shared.net.InetSocketAddressDAO;
import org.zoxweb.shared.net.ProxyType;
import org.zoxweb.shared.security.SecurityStatus;
import org.zoxweb.shared.net.InetProp.IPVersion;
import org.zoxweb.shared.net.InetProp.InetProto;
import org.zoxweb.shared.util.SharedUtil;

public class NetUtil 
{
	private NetUtil()
	{
		
	}

	public static SecurityStatus checkSecurityStatus(InetFilterRulesManager ifrm, String host, Closeable sc) throws IOException
	{
		
		SecurityStatus ret =  SecurityStatus.ALLOW;
		if (ifrm != null) 
			
		{
			ret = ifrm.lookupSecurityStatus(host);
			
			if (ret != SecurityStatus.ALLOW)
			{
				IOUtil.close(sc);
			}
		}
		
		
		return ret;
	}
	
	
	public static SecurityStatus checkSecurityStatus(InetFilterRulesManager ifrm, SocketAddress host, Closeable sc) throws IOException
	{
		SecurityStatus ret =  SecurityStatus.ALLOW;
		if (ifrm != null) 	
		{
			ret = ifrm.lookupSecurityStatus(host);
			if (sc!=null && ret != SecurityStatus.ALLOW)
			{
				IOUtil.close(sc);
			}
		}
		
		
		return ret;
	}
	
	public static SecurityStatus checkSecurityStatus(InetFilterRulesManager ifrm, InetAddress host, Closeable sc) throws IOException
	{
		SecurityStatus ret =  SecurityStatus.ALLOW;
		if (ifrm != null) 
			
		{
			ret = ifrm.lookupSecurityStatus(host); 
			if (sc!=null && ret != SecurityStatus.ALLOW)
			{
				IOUtil.close(sc);
			}
		}
		
		
		return ret;
	}
	
	
	//private NIUtil(){}

	/**
	 * Return the main ip V4 address associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip address, null if no ip address is associated with the
	 *         current network interface.
	 * @throws IOException
	 */
	public static Inet4Address getIPV4MainAddress(String ni) throws IOException {
		return getIPV4MainAddress(NetworkInterface.getByName(ni));

	}
	
	
	
	public static Inet4Address getInet4Address( String host) throws UnknownHostException
	{
		//log.info("Host:" + host);
		InetAddress[] all = InetAddress.getAllByName(host);
		for ( InetAddress ip : all)
		{
			if ( ip instanceof Inet4Address)
			{
				return (Inet4Address) ip;
			}
		}
		
		//log.info("no IP v4 for host:" + host);
		return null;
	}

	/**
	 * Return the main ip V4 address associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip address, null if no ip address is associated with the
	 *         current network interface.
	 * @throws IOException
	 */
	public static Inet4Address getIPV4MainAddress(NetworkInterface ni)
        throws IOException
	{
		Inet4Address[] addresses = getIPV4AllAddresses( ni);

		if (addresses != null && addresses.length > 0)
		{
			return addresses[addresses.length -1];
		}
		
		return null;
	}
	
	
	
//	public static synchronized void updateFilter( InetFilterDAO ipf) 
//	{
//		if ( ipf.getIP() != null && ipf.getNetworkMask() != null)
//		{
//			try
//			{
//				String network = getNetwork( ipf.getIP(), ipf.getNetworkMask());
//			
//				String ipBytes[] = network.split("[.]");
//				String maskBytes[] = ipf.getNetworkMask().split("[.]");
//				StringBuilder sb = new StringBuilder();
//				for ( int i = ipBytes.length -1; i != -1; i--)
//				{
//					if ( ipBytes[i].equals("0") && maskBytes[i].equals("0"))
//					{
//						ipBytes[i] = null;
//					}
//					else
//					{
//						break;
//					}
//				}
//				
//				for ( int i = 0; i < ipBytes.length; i++)
//				{
//					String s = ipBytes[i];
//					if ( s != null)
//					{
//						sb.append(s);
//						if ( i + 1 != ipBytes.length)
//							sb.append('.');
//					}
//					else
//					{
//						break;
//					}
//				}
//				ipf.setFilter(sb.toString());
//				
//				
//			}
//			catch( IOException e)
//			{
//				
//				ipf.setFilter( null);
//			}
//			
//		}
//	}
	
	public static boolean belongsToNetwork(InetFilterDAO ipf, String ipAddress)
		throws IOException
	{
		String tempNetwork = getNetwork( ipAddress, ipf.getNetworkMask());
		return ( tempNetwork.equals( ipf.getNetwork()));
	}
	
	
	
	public static boolean belongsToNetwork(byte[] network, byte[] networkMask, String ipAddress)
			throws IOException
	{
		SharedUtil.checkIfNulls("Network or IP adress can't be null", network, ipAddress);
		byte[] tempNetwork = null;
		if ( networkMask != null)
		{
			tempNetwork = getNetwork( InetAddress.getByName(ipAddress).getAddress(), networkMask);
		}
		else
		{
			tempNetwork = InetAddress.getByName(ipAddress).getAddress();
		}
		return Arrays.equals(network, tempNetwork);
			
	}
	
	
	public static boolean belongsToNetwork(byte[] network, byte[] networkMask, byte[] ipAddress)
			throws IOException
	{
		SharedUtil.checkIfNulls("Network or IP adress can't be null", network, ipAddress);
		byte[] tempNetwork = null;
		if ( networkMask != null)
		{
			tempNetwork = getNetwork(ipAddress, networkMask);
		}
		else
		{
			//log.info("networkmask: null");
			tempNetwork = ipAddress;
		}
		return Arrays.equals(network, tempNetwork);
			
	}
	
	
	public static Proxy.Type lookup(ProxyType pt)
	{
		if (pt != null)
		{
			switch(pt)
			{
			case DIRECT:
				return Proxy.Type.DIRECT;
			case HTTP:
				return Proxy.Type.HTTP;
			case SOCKS:
				return Proxy.Type.SOCKS;
			
			
			}
		}
		
		return Proxy.Type.DIRECT;
	}
	
	
//	public static MACAddress getMACAddress(String ifName) throws IOException
//	{
//		NetworkInterface ni = NetworkInterface.getByName(ifName);
//		byte macAddr[] = ni.getHardwareAddress();
//		
//		return MACAddress.getByAddress(macAddr);
//		
//	}

	/**
	 * Return all the ip V4 addresses associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip addresses, empty array of no ips
	 * @throws IOException
	 */
	public static Inet4Address[] getIPV4AllAddresses(String ni)
			throws IOException {
		return getIPV4AllAddresses(NetworkInterface.getByName(ni));

	}

	/**
	 * Return all the ip V4 addresses associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip addresses, empty array of no ips
	 * @throws IOException
	 */
	public static Inet4Address[] getIPV4AllAddresses(NetworkInterface ni)
			throws IOException 
	{
		ArrayList<Inet4Address> v = new ArrayList<Inet4Address>();
		for (Enumeration<InetAddress> e = ni.getInetAddresses(); e.hasMoreElements();) {
			InetAddress addr =  e.nextElement();
			if (addr instanceof Inet4Address)
				v.add((Inet4Address)addr);
		}

		return (Inet4Address[]) v.toArray(new Inet4Address[v.size()]);
	}


	/**
	 * Return the main ip V6 address associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip address, null if no ip address is associated with the
	 *         current network interface.
	 * @throws IOException
	 */
	public static Inet6Address getIPV6MainAddress(String ni) throws IOException {
		return getIPV6MainAddress(NetworkInterface.getByName(ni));

	}

	/**
	 * Return the main ip V6 address associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip address, null if no ip address is associated with the
	 *         current network interface.
	 * @throws IOException
	 */
	public static Inet6Address getIPV6MainAddress(NetworkInterface ni)
			throws IOException 
	{
		Inet6Address[] addresses = getIPV6AllAddresses( ni);

		if (addresses != null && addresses.length > 0)
		{
			return addresses[addresses.length -1];
		}
		
		return null;
	}

	/**
	 * Return all the ip V6 addresses associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip addresses, empty array of no ips
	 * @throws IOException
	 */
	public static Inet6Address[] getIPV6AllAddresses(String ni)
			throws IOException {
		return getIPV6AllAddresses(NetworkInterface.getByName(ni));

	}

	/**
	 * Return all the ip V6 addresses associated with the ni
	 * 
	 * @param ni
	 *            network interface we are looking for
	 * @return the ip addresses, empty array of no ips
	 * @throws IOException
	 */
	public static Inet6Address[] getIPV6AllAddresses(NetworkInterface ni)
			throws IOException {
		ArrayList<Inet6Address> v = new ArrayList<Inet6Address>();
		for (Enumeration<InetAddress> e = ni.getInetAddresses(); e.hasMoreElements();) {
			InetAddress addr =  e.nextElement();
			if (addr instanceof Inet6Address)
				v.add((Inet6Address)addr);
		}

		return v.toArray(new Inet6Address[v.size()]);
	
		
	}
	
	
	
	public static InetAddress toNetmaskIPV4( short netPrefix) throws IOException
	{
		
		if ( netPrefix > 32)
		{
			throw new IllegalArgumentException("Invalid mask " + netPrefix+ " > 32" );
		}
		
		//dbg("mask short " + netPrefix);
		long maskLong = 0xffffffffL ;
		
		//dbg("mask long " + (long)maskLong);
		maskLong = maskLong<<(32 - netPrefix );
		//dbg("mask long " + maskLong);

		
		byte[] maskAddress = new byte[4];

		for (int i=0; i < maskAddress.length; i++)
		{
			maskAddress[  maskAddress.length - (1+i)] = (byte)maskLong;//maskAddress[ maskAddress.length - (1+i)] ;
			
			maskLong = maskLong>>8;
		}
		
		return InetAddress.getByAddress(maskAddress);
	}
	
	public static InetAddress getNetwork(InterfaceAddress ia) throws IOException
	{
		InetAddress address = ia.getAddress();
		byte[] addressBytes = address.getAddress();
		short mask = ia.getNetworkPrefixLength();
		
		long maskLong = (addressBytes.length == 4) ? 0xffffffffL : 0xffffffffffffL;
		
		maskLong = maskLong<<((addressBytes.length == 4) ? 32 - mask : 48-mask);
		
		for (int i=0; i < addressBytes.length; i++)
		{
			addressBytes[  addressBytes.length - (1+i)] = (byte) (addressBytes[ addressBytes.length - (1+i)] &maskLong) ;
			
			maskLong = maskLong>>8;
		}
		
		
		return InetAddress.getByAddress(addressBytes);
	}
	
	
	public static String getNetwork( String address, String mask) 
		throws IOException
	{
		
		InetAddress ret = null;
		if ( mask != null)
		{
			ret = getNetwork( InetAddress.getByName(address), InetAddress.getByName(mask));
		}
		else
		{
			return address;
		}
		return (ret != null) ? ret.getHostAddress() : null;
	}
	
	public static InetAddress getNetwork(InetAddress address, InetAddress mask) 
		throws IOException
	{
		return InetAddress.getByAddress(getNetwork(address.getAddress(), mask.getAddress()));	
	}
	
	public static byte[] getNetwork(byte[] addressBytes, byte[] maskBytes) 
			throws IOException
	{
		byte[] networkBytes = new byte[addressBytes.length];
		
		for (int i = 0; i < networkBytes.length; i++)
		{
			networkBytes[i] = (byte)(addressBytes[i] & maskBytes[i]);
		}
		return networkBytes;		
	}
	
	
	
	public static boolean ping(String host) throws IOException
	{
		return ping( host, null);
	}
	
	public static boolean ping(String host, String niName) throws IOException
	{
		return ping(InetAddress.getByName(host), (niName != null) ? NetworkInterface.getByName(niName): null);
	}
	
	public static boolean ping(InetAddress addr, NetworkInterface ni) throws IOException
	{
		return ping( addr, ni, 255, 5000);
	}
	
	
	
	public static boolean ping(InetAddress addr, NetworkInterface ni, int ttl, int timeout) throws IOException
	{
		return ping ( addr, ni, ttl, timeout, true);
	}
	
	public static boolean ping(InetAddress addr, NetworkInterface ni, int ttl, int timeout, boolean statOn) throws IOException
	{
		long delta = 0;
		if ( statOn)
		{
			delta = System.currentTimeMillis();
		}
		boolean ret =  addr.isReachable(ni, ttl, timeout);
		
		if (statOn)
		{
			delta = System.currentTimeMillis() - delta;
			if ( ret)
				System.out.println("it took " + delta + " millis to ping: " + addr.getHostName());
			else
				System.out.println("ping timed out after " + timeout + " millis to ping: " + addr.getHostName());
				
		}
		
		return ret;
	}
	
	public static String generateRoutingScript(ConnectionPropDAO[] nis) throws IOException
	{
		StringBuilder sb = new StringBuilder("#!/bin/sh\n");
		sb.append("# Automatically generated bonding script " + new Date() + "\n");
		sb.append("# The bonding script will apply to the following network interfaces:\n");
		
		for ( ConnectionPropDAO ni : nis)
		{
			if ( ni.getType() == NIType.WAN && ni.currentStatus() == NIStatus.OK)
				sb.append("# * " + ni +"\n");
		}
		sb.append("# delete the default route\n");
		sb.append("route del default\n\n");
		
		Formatter frm = new Formatter(sb);
		frm.format("ip route add default scope global");
		for ( ConnectionPropDAO ni : nis)
		{
			if ( ni.getType() == NIType.WAN && isActive(ni.getName()) && ni.currentStatus() == NIStatus.OK)
			{
				IPInfo ii = IPInfo.getV4IPInfo(ni.getNIName());
				frm.format(" nexthop via %s dev %s weight %s", ii.getGateway().getHostAddress(), ni.getNIName(), ""+ni.getBandwidthCapacity());
			}
		}
		sb.append("\n");
		sb.append("ip route flush cache\n");
		frm.close();
		return sb.toString();
		
		
	}
	
	
	/**
	 * Return the protocol type of network connection static or dhcp
	 * @param niName
	 * @return Inet protocol
	 * @throws IOException if protocol is different than static or dhcp
	 */
	public static InetProto getProtoType( String niName) throws IOException
	{
		Properties ifcfg = new Properties();
		FileReader fr = new FileReader(IPInfo.LINUX_NI_CFG_PREFIX+niName);
		ifcfg.load(fr);
		fr.close();
		
		String proto = ifcfg.getProperty( IPInfo.LINUX_BOOTPROTO);
		InetProto pt = InetProto.valueOf(proto.toUpperCase());
		switch( pt)
		{
		case STATIC:
		case DHCP:
			return pt;
		
		default:
			throw new IOException("Network "+niName + " has unsupported BOOTPROTO" + proto );
		}
		
		
		
	}
	
	
	
	
	
	/**
	 * Convert the property to a network interface
	 * @param niName 
	 * @return network interface
	 * @throws IOException if the network interface do not exist
	 */
	public static NetworkInterface toNI(String niName) throws IOException
	{
		return NetworkInterface.getByName(niName);
	}
	
	/**
	 * This method invoke the isUp for currently associated NetworkInterface
	 * @param niName 
	 * @return true if active
	 * @throws IOException
	 */
	public static boolean isActive(String niName) throws IOException
	{
		NetworkInterface ni = toNI( niName);
		if ( ni != null)
		{
			return ni.isUp();
		}
		
		return false;
	}
	
	
	public static InetAddressDAO toInetAddressDAO(String str) throws IOException
	{
		SharedUtil.checkIfNulls("Null address", str);
		InetAddressDAO ret = new InetAddressDAO();
		ret.setInetAddress(str);
		ret.setIPVersion(InetAddress.getByName(str) instanceof Inet4Address ? IPVersion.V4 : IPVersion.V6);
		
		return ret;
	}
	
	public static InetAddressDAO toInetAddressDAO(InetAddress addr) throws IOException
	{	
		SharedUtil.checkIfNulls("Null address", addr);
		InetAddressDAO ret = new InetAddressDAO();
		ret.setInetAddress(addr.getHostAddress());
		ret.setIPVersion(addr instanceof Inet4Address ? IPVersion.V4 : IPVersion.V6);
		
		return ret;
	}
	
	/**
	 * Convert the property to a network interface
	 * @param ni 
	 * @return network interface 
	 * @throws IOException if the network interface do not exist
	 */
	public static NetworkInterface toNI(NetworkInterface ni) throws IOException
	{
		return NetworkInterface.getByName(ni.getName());
	}
	
	/**
	 * This method invoke the isUp for currently associated NetworkInterface
	 * @param ni 
	 * @return true if active
	 * @throws IOException
	 */
	public static boolean isActive(NetworkInterface ni) throws IOException
	{
		
		if ( ni != null)
		{
			return ni.isUp();
		}
		
		return false;
	}
	
	public static String generateBondingScript( ConnectionPropDAO nis[]) throws IOException
	{
		StringBuilder sb = new StringBuilder("#!/bin/sh\n");
		sb.append("# Automatically generated bonding script " + new Date() + "\n");
		sb.append("# The bonding script will apply to the following network interfaces:\n");
		
		for ( ConnectionPropDAO ni : nis)
			sb.append("# * " + ni +"\n");
		sb.append("# delete the default route\n");
		sb.append("route del default\n\n");
		Formatter frm = new Formatter(sb);
		for ( ConnectionPropDAO ni : nis)
		{
			//dbg("Generating for " + ni);
			if ( ni.getType() == NIType.WAN && isActive(ni.getName()))
			{
				frm.format("# Generating routing tables info for %s\n", ni);
				frm.format("ip route del table %d\n",ni.getRouteID());
				IPInfo ii = IPInfo.getV4IPInfo(ni.getNIName());
				//dbg("IPInfo " + ii);
				// ip route add $P1_NET dev $IF1 src $IP1 table $T1
				frm.format("ip route add %s dev %s src %s table %d\n", 
						   ii.getNetwork().getHostAddress(), 
						   ii.getNetworkInterface().getName(), 
						   ii.getIPAddress().getHostAddress(),
						   ni.getRouteID());
				//ip route add default via $P1 table $T1
				frm.format("ip route add default via %s table %s\n",
						   ii.getGateway().getHostAddress(),
						   ni.getRouteID());
				// ip rule add from $IP1 table $T1
				frm.format("ip rule add from %s table %d\n\n",
						   ii.getIPAddress().getHostAddress(),
						   ni.getRouteID());
			}
		}
		
		
//		// to be removed just for testing now
//		//ip route add default scope global nexthop via $P1 dev $IF1 weight 1 nexthop via $P2 dev $IF2 weight 1
//		frm.format("ip route add default scope global");
//		for ( NIProp ni : nis)
//		{
//			if ( ni.getType() == NIType.WAN && ni.isActive())
//			{
//				IPInfo ii = IPInfo.getV4IPInfo(ni.getNIName());
//				frm.format(" nexthop via %s dev %s weight 1", ii.getGateway().getHostAddress(), ni.getNIName());
//			}
//		}
//		sb.append("\n");
		
		//ip route add $LAN_NET dev $LAN_IF src 0.0.0.0
		for ( ConnectionPropDAO ni : nis)
		{
			if ( ni.getType() == NIType.LAN && ni.getCategory() == NICategory.MAIN)
			{
				IPInfo ii = IPInfo.getV4IPInfo(ni.getNIName());
				frm.format("ip route add %s dev %s src 0.0.0.0\n",
						   ii.getNetwork().getHostAddress(),
						   ni.getNIName());
			}
		}
		sb.append("\n");
		frm.close();
		return sb.toString();
	}
	
	
	
	public static boolean areInetSocketAddressDAOEquals(InetSocketAddressDAO isad1, InetSocketAddressDAO isad2) throws UnknownHostException
	{	
		if (isad1 != null && isad2 != null)
		{
			if(isad1.getInetAddress() != null && isad2.getInetAddress() != null)
			{
				boolean ret = InetAddress.getByName(isad1.getInetAddress()).equals(InetAddress.getByName(isad2.getInetAddress()));
				
				return (ret && (isad1.getPort()==isad2.getPort()));
				
			}
		}
		
		
		return false;
	}
	
	

	
	
//	public static final String BONDING_FN = "bonding.sh";
//	public static final String ROUTING_FN = "routing.sh";
//	public static final String BONDINGMODE = "BONDING_MODE";
}
