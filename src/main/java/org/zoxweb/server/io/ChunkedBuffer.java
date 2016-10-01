package org.zoxweb.server.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.zoxweb.shared.util.ReferenceID;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class ChunkedBuffer
implements ReferenceID<String>
{	
	private final int chunkSize;
	private final byte content[];
	private int currentChunk = 0;
	private final String referenceID;

	
	public ChunkedBuffer(byte content[], int chunkSize)
	{
		this(content, chunkSize, null);
	}
	public ChunkedBuffer(byte content[], int chunkSize, String referenceID) 
	{
		SharedUtil.checkIfNulls("Null content", content);
		if (chunkSize < 1)
		{
			throw new IllegalArgumentException("Chunk too small");
		}
		// TODO Auto-generated constructor stub
		this.chunkSize = chunkSize;
		this.content = content;
		this.referenceID = referenceID == null ? UUID.randomUUID().toString() : referenceID;		
	}
	
	public synchronized int write(OutputStream os) throws IOException
	{
		int length = currentChunkSize();
		if (currentChunk()*chunkSize+length <= content.length)
		{
			os.write(content, currentChunk()*chunkSize, length);
			currentChunk++;
			return length;
		}
		return -1;
	}
	
	
	
	
	public int currentChunk()
	{
		return currentChunk;
	}
	
	
	public synchronized int currentChunkSize()
	{
		int index = content.length - ( (currentChunk+1) * chunkSize);
		
		if (index >= 0 )
		{
			return chunkSize;
		}
		else
		{
			return content.length % chunkSize;
		}
	}
	
	
	
	public final byte[] getContent()
	{
		return content;
	}
	@Override
	public String getReferenceID() {
		// TODO Auto-generated method stub
		return referenceID;
	}
	@Override
	public void setReferenceID(String id) 
	{
		// TODO Auto-generated method stub
		
	}
	

	
	
}
