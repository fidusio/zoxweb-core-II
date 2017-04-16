/*
 * Copyright (c) 2012-2014 ZoxWeb.com LLC.
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
	
	/**
	 * This byte array contains the byte values based on base64 values.
	 * This array is used to covert from base64.
	 */
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
		return decode( data, 0, data.length);
	}

	/**
	 * Decodes a base64 array to a byte array.
	 * @param data
	 * @param index
	 * @param len
	 * @return decoded byte array
	 */
	public static byte[] decode(byte[] data, int index, int len)
    {

    	if (data == null || data.length == 0)
    	{
    		return new byte[0];
		}
	    
	    //int len = data.length;
	    assert (len % 4) == 0;

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
	    
	    while (iidx < len)
        {
	    	int c0 = REVERSE_BASE_64[data[index + iidx++] & 0xff];
	    	int c1 = REVERSE_BASE_64[data[index + iidx++] & 0xff];
	    	int c2 = REVERSE_BASE_64[data[index + iidx++] & 0xff];
	    	int c3 = REVERSE_BASE_64[data[index + iidx++] & 0xff];
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
     * Encodes a byte array to base64 array.
     * @param data
     * @return base64 encoded array
     */
    public static byte[] encode(byte[] data)
    {
    	return encode(data, 0, data.length);
    }

	/**
	 * Encodes a byte array to base64 array.
	 * @param data
	 * @param index
	 * @param len
	 * @return base64 encoded array
	 */
	public static byte[] encode(byte[] data, int index, int len)
    {
    	if (data == null || data.length == 0)
    	{
			return new byte[0];
		}

    	if (index < 0 || len > data.length - index )
    	{
			throw new IllegalArgumentException("Invalid parameter " + index + "," + len);
		}

	    //int len = data.length;

	    int olen = 4 * ((len + 2) / 3);
	    byte[] bytes = new byte[olen];

	    int iidx = index;
	    int oidx = 0;
	    int charsLeft = len;
	    
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

	    	bytes[oidx++] = BASE_64[c0];
	    	bytes[oidx++] = BASE_64[c1];
	    	bytes[oidx++] = (byte)((charsLeft > 1) ? BASE_64[c2] : '=');
	    	bytes[oidx++] = (byte)((charsLeft > 2) ? BASE_64[c3] : '=');

	    	charsLeft -= 3;
	    }

	    return bytes;
    }
 
}