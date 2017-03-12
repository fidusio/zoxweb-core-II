/*
 * Copyright (c) 2012-May 5, 2014 ZoxWeb.com LLC.
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
package org.zoxweb.server.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to parse a message in a buffer with or without initiating 
 * this class. First, a constructor is defined with a given delimiter value that
 * cannot be changed once the object is created. Second, a method parses the message 
 * based on a delimiter that can be changed and can be accessed outside the class.
 * @author mzebib
 *
 */
public class ByteBufferParser 
{
	private UByteArrayOutputStream uboas = new UByteArrayOutputStream();
	private byte[] delimiter;
	
	
	/**
	 * This constructor initiates a ByteBufferParser object based 
	 * on uboas and delimiter values. This constructor throws an 
	 * exception for null input for both parameters and when the 
	 * byte array is equal to zero.
	 * @param uboas
	 * @param delimeter
	 */
	public ByteBufferParser(UByteArrayOutputStream uboas, byte[] delimeter)
	{
		
		if(uboas == null || delimeter == null)
			throw new NullPointerException("uboas and/or delimiter cannot be null.");
		
		if(delimeter.length == 0)
			throw new IllegalArgumentException("Empty delimiter is not accepted.");
		
		this.uboas = uboas;
		this.delimiter = delimeter;
	}

	/**
	 * This method returns the output byte array stream.
	 * @return the byte array out stream
	 */
	public final UByteArrayOutputStream getOutputByteArrayStream() 
	{
		return uboas;
	}

	/**
	 * This method returns the delimiter value. 
	 * @return the delimiter 
	 */
	public final byte[] getDelimiter() 
	{
		return delimiter;
	}

	/**
	 * This method parses the data based on the delimiter
	 * value of the ByteBufferParser object.
	 * @return the parsed byte arrays
	 */
	public List<byte[]> parse()
	{
		
		return parse(delimiter);
	}
	
	/**
	 * This method parses the data based on given delimiter
	 * value as parameter. The delimiter value can be changed
	 * from outside the class.
	 * @param delim
	 * @return the parsed byte arrays
	 */
	public List<byte[]> parse(byte[] delim)
	{
		
		return parse(uboas, delim);
	}

	/**
	 * This static method can be invoked without initiating the
	 * ByteBufferObject class and takes uboas and delimiter 
	 * values as parameters. This method throws an exception 
	 * for null input for both parameters and when the 
	 * byte array is equal to zero. This method parses based on
	 * a match found between the delimiter and the message and
	 * exits the loop when no match is found.
	 * @param uboas
	 * @param delim
	 * @return the parsed byte arrays
	 */
	public static List<byte[]> parse(UByteArrayOutputStream uboas, byte[] delim)
	{
		
		if(uboas == null || delim == null)
			throw new NullPointerException("uboas and/or delimiter cannot be null.");
		
		if(delim.length == 0)
			throw new IllegalArgumentException("Empty delimiter is not accepted.");
		
		int match = 0;
		ArrayList<byte[]> matchedTokens = new ArrayList<byte[]>();
		
		synchronized (uboas) {

			while((match = uboas.indexOf(delim)) != -1)
			{
				byte[] message = Arrays.copyOf(uboas.getInternalBuffer(),match);
				uboas.removeAt(0, match + delim.length);
				matchedTokens.add(message);
			}
		}
				
		return matchedTokens;
	}
	
	
	
}
