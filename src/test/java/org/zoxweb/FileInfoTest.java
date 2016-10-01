package org.zoxweb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.FileInfoDAO;
import org.zoxweb.shared.data.ZWDataFactory;
import org.zoxweb.shared.data.ticket.TicketContainerDAO;
import org.zoxweb.shared.data.ticket.TicketIssuerDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedUtil;

public class FileInfoTest 
{
	
	public enum CFFIParams
	implements GetNVConfig
	{
		
		NAME(NVConfigManager.createNVConfig("name", null,"Name", true, true, String.class)),
		CHILD_NAME( NVConfigManager.createNVConfig("CHILD_NAME", null,"ChildName",false, true, String.class)),
	;
	
		private final NVConfig cType;
		CFFIParams( NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			// TODO Auto-generated method stub
			return cType;
		}
	}
	
	@SuppressWarnings("serial")
	static public class ChildFileInfoDAO
		extends FileInfoDAO
	{
		
		
		
		private List<NVPair> tempo = new ArrayList<NVPair>();
		public static final NVConfigEntity  NVC_CHILD_FILE_INFO_DAO = new NVConfigEntityLocal("child_file_info_dao", null , "ChildFileInfoDAO", true, false, false, false,ChildFileInfoDAO.class, SharedUtil.extractNVConfigs(CFFIParams.values()), null, false, FileInfoDAO.NVC_FILE_INFO_DAO);//,SharedUtil.extractNVConfigs( new Params[]{Params.REFERENCE_ID, Params.NAME, Params.LENGTH}));
		
		
		public ChildFileInfoDAO()
		{
			super ( NVC_CHILD_FILE_INFO_DAO);
		}
		
		
		/**
		 * Get the the filename
		 * @return
		 */
		public String getChildName() 
		{
			// TODO Auto-generated method stub
			return lookupValue( CFFIParams.CHILD_NAME);
		}

		/**
		 * Set 
		 * @param name
		 */
		public void setChildName(String name)
			throws NullPointerException, IllegalArgumentException
		{
			setValue(  CFFIParams.CHILD_NAME, name);
		}


		public List<NVPair> getTempo() {
			return tempo;
		}


		public void setTempo(List<NVPair> tempo) {
			this.tempo = tempo;
		}
	}
	
	
	public static final NVConfigEntity  NVC = new NVConfigEntityLocal(null, null , "ChildFileInfoDAO", true, false, false, false, FileInfoDAO.class, SharedUtil.extractNVConfigs(CFFIParams.values()), null, false, null);//,SharedUtil.extractNVConfigs( new Params[]{Params.REFERENCE_ID, Params.NAME, Params.LENGTH}));
	
	public static void main( String ...args)
	{
		try
		{
			
			
			
			
			
			
			
			
		FileInfoDAO fi = new ChildFileInfoDAO();
		
		List<NVPair> props = new ArrayList<NVPair>(); 
		
		props.add( new NVPair("Marwan", "nael"));
		props.add( new NVPair("Imad", "NAEL"));
		long i = 100;
		long refID =0;
		fi.setReferenceID( ""+(++refID));
		fi.setCreationTime(i++);
		fi.setLastTimeRead(i++);
		fi.setLastTimeUpdated(i++);
		fi.setName("Child1");
		fi.setContentType("mime1");
		//fi.setResourceLocation("store1");
		((ChildFileInfoDAO)fi).setChildName("baby1");
		fi.setLength( i++);
		fi.setProperties( props);
		System.out.println( fi);
		
		
		fi = new ChildFileInfoDAO();
		fi.setReferenceID( ""+(++refID));
		fi.setCreationTime(i++);
		fi.setLastTimeRead(i++);
		fi.setLastTimeUpdated(i++);
		fi.setName("Child2");
		fi.setContentType("mime2");
		//fi.setResourceLocation("store2");
		((ChildFileInfoDAO)fi).setChildName("baby2");
		fi.setLength( i++);
		fi.setProperties( props);
		System.out.println( fi);
		System.out.println(  ((NVConfigEntity)fi.getNVConfig()).getAttributes());
		
		
		
		fi = new FileInfoDAO();
		fi.setReferenceID(  ""+(++refID));
		fi.setCreationTime(i++);
		fi.setLastTimeRead(i++);
		fi.setLastTimeUpdated(i++);
		fi.setName("parent1");
		fi.setContentType("exe");
		//fi.setResourceLocation("store3");
		
		fi.setLength( i++);
		fi.setProperties( props);
		System.out.println( fi);
		
		fi = new FileInfoDAO();
		fi.setReferenceID(  ""+(++refID));
		fi.setCreationTime(i++);
		fi.setLastTimeRead(i++);
		fi.setLastTimeUpdated(i++);
		fi.setName("/dir/parent2");
		fi.setContentType("exe");
		//fi.setResourceLocation("store4");
		
		fi.setLength( i++);
		fi.setProperties( props);
		
		FileInfoDAO remote = new FileInfoDAO();
		remote.setName("RemoteFile");
		System.out.println(GSONUtil.toJSON( fi, true));
		
		fi.setRemoteFileInfo(remote);
		System.out.println( fi);
		System.out.println(  ((NVConfigEntity)fi.getNVConfig()).getAttributes());
		
		System.out.println( fi.getProperties());
		System.out.println("remote:" + fi.getRemoteFileInfo());
		String json = null;
		long ts;
		try {
			json = GSONUtil.toJSON( fi.getOriginalFileInfo(), true);
			System.out.println( json);
			
			System.out.println( fi);
			FileInfoDAO fiTemp = GSONUtil.fromJSON( json, FileInfoDAO.class);
			String temp = GSONUtil.toJSON( fiTemp, true);
			System.out.println((temp.equals(json))+"[IMADS]:" +fiTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		try {
			File f = File.createTempFile("temp", "temp");
			f.deleteOnExit();
			System.out.println( f.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		
		
		long total = 0;
		long totalTempo=0;
		int repeat = 10;
		fi = new ChildFileInfoDAO();
		
		
		
		
		fi.setReferenceID(  ""+(++refID));
		fi.setCreationTime(System.nanoTime());
		fi.setLastTimeRead(System.nanoTime());
		fi.setLastTimeUpdated(System.nanoTime());
		fi.setName("Child5");
		fi.setContentType("mime1");
		//fi.setResourceLocation("store1");
		((ChildFileInfoDAO)fi).setChildName("baby1");
		fi.setLength( i++);
		fi.setProperties( props);
		
		for (  i = 0; i < repeat; i++)
		{
			ts = System.nanoTime();
			fi.getProperties();
			ts=  System.nanoTime() - ts;
			total+= ts;
			System.out.println("it took " + ts);
			ts = System.nanoTime();
			((ChildFileInfoDAO)fi).getTempo();
			ts=  System.nanoTime() - ts;
			totalTempo+=ts;
			
			System.out.println("it took " + ts);
		}
		System.out.println( "Average:" + (total/repeat) +  " " +(totalTempo/repeat));
		
		
		try {
			System.out.println( "json:" + GSONUtil.toJSON(fi, true, false, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(((NVConfigEntity) fi.getNVConfig()).getAttributes());
		
		
		
		System.out.println("Subsearch:" + TicketIssuerDAO.NVC_TICKET_ISSUER_DAO.lookup( "email"));	
		TicketContainerDAO ticket = new TicketContainerDAO();
		
		NVConfigEntity nvceTicket = (NVConfigEntity) ticket.getNVConfig();
		System.out.println(nvceTicket);
		System.out.println("Subsearch:" + TicketContainerDAO.NVC_TICKET_CONTAINER_DAO.lookup( "issuer.email"));
		
		
		//NVEntity nve = ZWDataFactory.SINGLETON.createNVEntity(nvceTicket.lookup("issuer").getName());
		NVConfigEntity nvceIssuer = (NVConfigEntity) nvceTicket.lookup("issuer");
		System.out.println("Issuer:" + ticket.getIssuerInfo());
		ticket.setValue("issuer.assignee", "mnael@zoxweb.com");
		
		
		
		ticket.setIssuerInfo((TicketIssuerDAO) ZWDataFactory.SINGLETON.createNVEntity("ticket_issuer_dao"));
		ticket.setValue("issuer.assignee", "mnael@zoxweb.com");
		System.out.println("Issuer:" + ticket.getIssuerInfo() + ":" + nvceIssuer.getName());
		
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
}
