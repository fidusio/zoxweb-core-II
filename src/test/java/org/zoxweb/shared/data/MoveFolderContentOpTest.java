package org.zoxweb.shared.data;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.util.NVPair;

public class MoveFolderContentOpTest 
{
	public static void main(String ...args)
	{
		
		try
		{
			MoveFolderContentOp mfco = new MoveFolderContentOp();
			
			
			mfco.setFromFolderRef("fromFolder");
			mfco.setToFolderRef("toFolder");
			mfco.getNVEs().add(new NVPair("file", "f1"));
			mfco.getNVEs().add(new NVPair("document", "d1"));
			String json = GSONUtil.toJSON(mfco, false, false, true);
			
			System.out.println(json);
			
			mfco = GSONUtil.fromJSON(json);
			json = GSONUtil.toJSON(mfco, false, false, true);
			
			System.out.println(json);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
