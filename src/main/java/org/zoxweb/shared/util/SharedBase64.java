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
package org.zoxweb.shared.util;

import java.util.Arrays;

/**
 * Contains utility methods to converts to/from base64 byte array.
 * @author mnael
 */
public class SharedBase64
{
	

	/**
	 * This byte array contains the base64 values. This array is used to
	 * convert to base64.
	 */
	public final static byte BASE_64[] =
	{
		//"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
		'0','1','2','3','4','5','6','7','8','9','+','/'
	};
	
	public final static byte URL_BASE_64[] =
		{
			//"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			'0','1','2','3','4','5','6','7','8','9','-','_'
		};
	
	/**
	 * This byte array contains the byte values based on base64 values.
	 * This array is used to covert from base64.
	 */
	public final static byte[] URL_REVERSE_BASE_64 =
		{
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
			 
			//                                                  -           0   1   2   3   4   5   6   7   8  9
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, 0, -1, -1, 
			
			//  A  B  C  D  E  F  G  H  I  J  K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z                   _
			-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, 

			//   a   b   c   d   e   f   g   h   i  j    k   l   m   n   o   p   q   r   s   t   u   v   w   x   y   z 
			-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1,
			// the rest 
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		};
	
	public final static byte[] REVERSE_BASE_64 =
		{
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
			 
			//                                          +               /   0   1   2   3   4   5   6   7   8  9
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, 0, -1, -1, 
			
			//  A  B  C  D  E  F  G  H  I  J  K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
			-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, 

			//   a   b   c   d   e   f   g   h   i  j    k   l   m   n   o   p   q   r   s   t   u   v   w   x   y   z 
			-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1,
			// the rest 
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		};
	
	
	
	public enum Base64Type
	{
		REGULAR(BASE_64, REVERSE_BASE_64),
		URL(URL_BASE_64, URL_REVERSE_BASE_64);
		
		
		Base64Type(byte[] encode, byte[] decode)
		{
			ENCODE_SET = encode;
			DECODE_SET = decode;
		}
		
		public final byte[] ENCODE_SET;
		public final byte[] DECODE_SET;
	}
	/**
	 * The constructor is declared private to prevent instantiation.
	 */
	private SharedBase64()
    {

	}

	/**
	 * Decodes a base64 array to a byte array.
	 * @param data
	 * @return decoded byte array
	 */
	public static byte[] decode(byte[] data)
    {
		return decode(Base64Type.REGULAR, data, 0, data.length);
	}
	
	public static byte[] decode(Base64Type bt, byte[] data)
    {
		return decode(bt, data, 0, data.length);
	}
	
	public static byte[] decode(byte[] data, int index, int len)
    {
		return decode(Base64Type.REGULAR, data, index, len);
    }

	/**
	 * Decodes a base64 array to a byte array.
	 * @param data
	 * @param index
	 * @param len
	 * @return decoded byte array
	 */
	public static byte[] decode(Base64Type bt, byte[] data, int index, int len)
    {

    	if (data == null || data.length == 0)
    	{
    		return new byte[0];
		}
    	if (bt == null)
    	{
    		bt = Base64Type.REGULAR;
    	}
	    
	    //int len = data.length;
	    if(Base64Type.REGULAR == bt && len % 4 != 0)
	    	throw new IllegalArgumentException("Invalid len not divisible by 4");
	    else if(Base64Type.URL == bt && len % 4 != 0)
	    {
	    	int temp = len + (len %4);
	    	byte tempArray[] = new byte[temp];
	    	System.arraycopy(data, index, tempArray, index, len);
	    	for(int i = len; i < temp; i++)
	    	{
	    		tempArray[i] = '=';
	    	}
	    	data = tempArray;
	    	len = temp;
	    }

	    //char[] chars = new char[len];
	    //data.getChars(0, len, chars, 0);

	    int olen = 3 * (len / 4);

	    if (data[len - 2] == '=')
	    {
	    	--olen;
	    }

	    if (data[len - 1] == '=')
	    {
	    	--olen;
	    }

	    byte[] bytes = new byte[olen];

	    int iidx = 0;
	    int oidx = 0;
	    
	    //int c0,c1,c2,c3,c24;
	    
	    while (iidx < len)
        {
	    	int c0 = bt.DECODE_SET[data[index + iidx++] & 0xff];
	    	int c1 = bt.DECODE_SET[data[index + iidx++] & 0xff];
	    	int c2 = bt.DECODE_SET[data[index + iidx++] & 0xff];
	    	int c3 = bt.DECODE_SET[data[index + iidx++] & 0xff];
	    	int c24 = (c0 << 18) | (c1 << 12) | (c2 << 6) | c3;

	    	
	    	bytes[oidx++] = (byte) (c24 >> 16);

	    	if (oidx == olen)
	    	{
	    		break;
	    	}

	    	bytes[oidx++] = (byte) (c24 >> 8);

	    	if (oidx == olen)
	    	{
	    		break;
	    	}

	    	bytes[oidx++] = (byte) c24;
	    }

	    return bytes;
	}
	
	
	/**
	 * Decode a string read it getByte("utf-8:)
	 * @param str to be decoded
	 * @return decoded byte array
	 */
	public static byte[] decode(String str)
	{
		return decode(SharedStringUtil.getBytes(str));
	}
	
	public static byte[] decode(Base64Type bt, String str)
	{
		return decode(bt, SharedStringUtil.getBytes(str));
	}
	
	/**
	 * Encode a string to base64 
	 * @param str to be encoded
	 * @return encoded byte array
	 */
	public static byte[] encode(String str)
	{
		return encode(SharedStringUtil.getBytes(str));
	}
	
	public static byte[] encode(Base64Type bt, String str)
	{
		return encode(bt, SharedStringUtil.getBytes(str));
	}

    /**
     * Encodes a byte array to base64 array.
     * @param data
     * @return base64 encoded array
     */
    public static byte[] encode(byte[] data)
    {
    	return encode(Base64Type.REGULAR, data, 0, data.length);
    }
    
    public static byte[] encode(Base64Type bt, byte[] data)
    {
    	return encode(bt, data, 0, data.length);
    }
    
    public static byte[] encode(byte[] data, int index, int len)
    {
    	return encode(Base64Type.REGULAR, data, index, len);
    }
    
	/**
	 * Encodes a byte array to base64 array.
	 * @param bt type
	 * @param data
	 * @param index
	 * @param len
	 * @return base64 encoded array
	 */
	public static byte[] encode(Base64Type bt, byte[] data, int index, int len)
    {
    	if (data == null || data.length == 0)
    	{
			return new byte[0];
		}

    	if (index < 0 || len > data.length - index )
    	{
			throw new IllegalArgumentException("Invalid parameter " + index + "," + len);
		}
    	if (bt == null)
    	{
    		bt = Base64Type.REGULAR;
    	}

	    //int len = data.length;

	    int olen = 4 * ((len + 2) / 3);
	    
	    byte[] bytes = new byte[olen];

	    int iidx = index;
	    int oidx = 0;
	    int charsLeft = len;
	    //int b0,b1,b2,b24,c0,c1,c2,c3;
	    
	    
	    while (charsLeft > 0)
        {
	    	int b0 = data[iidx++] & 0xff;
	    	int b1 = (charsLeft > 1) ? data[iidx++] & 0xff : 0;
	    	int b2 = (charsLeft > 2) ? data[iidx++] & 0xff : 0;
	    	int b24 = (b0 << 16) | (b1 << 8) | b2;

	    	int c0 = (b24 >> 18) & 0x3f;
	    	int c1 = (b24 >> 12) & 0x3f;
	    	int c2 = (b24 >> 6) & 0x3f;
	    	int c3 = b24 & 0x3f;

	    	bytes[oidx++] = bt.ENCODE_SET[c0];
	    	bytes[oidx++] = bt.ENCODE_SET[c1];
	    	bytes[oidx++] = (byte)((charsLeft > 1) ? bt.ENCODE_SET[c2] : '=');
	    	bytes[oidx++] = (byte)((charsLeft > 2) ? bt.ENCODE_SET[c3] : '=');

	    	charsLeft -= 3;
	    }
	    
	    if (bt == Base64Type.URL && charsLeft < 0)
	    {
	    	int sub = 0;
	    	if (bytes[bytes.length -1] == '=')
	    	{
	    		sub++;
	    	}
	    	if (bytes[bytes.length -2] == '=')
	    	{
	    		sub++;
	    	}
	    	return Arrays.copyOf(bytes,  bytes.length - sub);
	    }

	    return bytes;
    }
 
}