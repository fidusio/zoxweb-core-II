package java.nio;



/**
 * This class was created to prevent unnecessary buffer, this class CAN NOT invoked or used in the code directly it will cause 
 * class load security exception, it must used in the context of ByteBufferUtil class in tandem with UByteArrayOutputStream  
 * @author mnael
 *
 */
public class CustomHeapByteBuffer 
extends HeapByteBuffer
//implements InternalBufferAccess
{	
	public CustomHeapByteBuffer(byte[] buf, int off, int len)
	{
		super(buf, off, len);
	}
	
	public CustomHeapByteBuffer(byte[] buf, int mark, int pos, int lim, int cap, int off)
	{
		super(buf, mark, pos, lim, cap, off);
		// TODO Auto-generated constructor stub
	}


	public byte[] getInternalBuffer() 
	{
		// TODO Auto-generated method stub
		return hb;
	}

	
//	public int size() 
//	{	
//		return limit();
//	}

}
