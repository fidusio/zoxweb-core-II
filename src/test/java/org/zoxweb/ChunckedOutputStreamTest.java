package org.zoxweb;

import java.io.ByteArrayOutputStream;

import org.zoxweb.server.io.ChunkedBuffer;

public class ChunckedOutputStreamTest {

	public static void main(String[] args) 
	{
		try
		{
			// TODO Auto-generated method stub
			String test = "12345678901234567890123456789012345678901234567890123456789012345678901234567890size 51";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ChunkedBuffer cos = new ChunkedBuffer(test.getBytes(), 2, "A");
			
			while(cos.write(baos) != -1);
			
			System.out.println(baos.toString().equals(test));
			System.out.println(baos.toString());
			System.out.println(baos.size()+"," + test.length() + "," + cos.currentChunk());
		
			
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
