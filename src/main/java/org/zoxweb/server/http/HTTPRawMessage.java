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
package org.zoxweb.server.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zoxweb.server.io.UByteArrayOutputStream;

import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.protocol.ProtocolDelimiter;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.SharedUtil;

public class HTTPRawMessage 
{
	private UByteArrayOutputStream ubaos;
	private int endOfHeadersIndex = -1;
	private int contentLength = -1;
	private List<GetNameValue<String>> headers = null;
	private String firstLine = null;
	
	public HTTPRawMessage(byte fullMessage[])
	{
		this(fullMessage, 0 , fullMessage.length);
	}
	
	public HTTPRawMessage(byte fullMessage[], int offset, int len)
	{
		ubaos = new UByteArrayOutputStream( len);
		ubaos.write( fullMessage, offset, len);
		parse();
	}
	
	public HTTPRawMessage()
	{
		this(new UByteArrayOutputStream());
	}
	

	public HTTPRawMessage(UByteArrayOutputStream ubaos)
	{
		this.ubaos = ubaos;
	}
	
	public String getFirstLine()
	{
		return firstLine;
	}
	
	public List<GetNameValue<String>> getHearders()
	{
		return headers;
	}

	public UByteArrayOutputStream getUBAOS()
	{
		return ubaos;
	}

	private void parseRawHeaders()
	{
		if (endOfHeadersIndex != -1)
		{
			headers = new ArrayList<GetNameValue<String>>();
			int lineCounter =0;
			for (int i=0; i < endOfHeadersIndex;)
			{
				int endOfCurrentLine = ubaos.indexOf(i, ProtocolDelimiter.CRLF.getBytes(), 0, ProtocolDelimiter.CRLF.getBytes().length);
				
				if (endOfCurrentLine != -1)
				{
					lineCounter++;
					String oneLine = new String(Arrays.copyOfRange(ubaos.getInternalBuffer(), i, endOfCurrentLine));

					if (lineCounter > 1)
					{
						headers.add(SharedUtil.toNVPair(oneLine, ":", true));
					}
					else
					{
						firstLine = oneLine;
					}
				}

				i=endOfCurrentLine+ProtocolDelimiter.CRLF.getBytes().length;
			}
		}
		
	}
	
	public synchronized boolean isMessageComplete()
	{
		if (endOfHeadersIndex != -1)
		{
			if (contentLength !=-1)
			{
				return ((endOfHeadersIndex + contentLength + ProtocolDelimiter.CRLFCRLF.getBytes().length) == endOfMessageIndex());
			}
			
			return true;
		}
		return false;
	}
	
	public int endOfMessageIndex()
	{
		return ubaos.size();
	}
	
	
	public int endOfHeadersIndex()
	{
		return endOfHeadersIndex;
	}
	
	public byte[] getRawHeaders()
	{
		if (endOfHeadersIndex != -1 )
		{
			return Arrays.copyOfRange(ubaos.getInternalBuffer(), 0, endOfHeadersIndex);
		}
		
		return null;
	}

	public synchronized void parse()
	{
		if (endOfHeadersIndex() == -1)
		{
			// detect end of message
			endOfHeadersIndex = ubaos.indexOf(ProtocolDelimiter.CRLFCRLF.getBytes());

			if (endOfHeadersIndex != -1)
			{
				parseRawHeaders();
			
				GetNameValue<String> clNV = SharedUtil.lookupNV( headers, HTTPHeaderName.CONTENT_LENGTH.getName());

				if (clNV != null)
				{
					try
					{
						contentLength = Integer.parseInt(clNV.getValue().trim() );
					}
					catch( Exception e)
					{	
						e.printStackTrace();
					}
				}
			}
		}
	}

	public int getContentLength()
	{
		return contentLength;
	}
	
	public byte[] getRawContent()
	{
		if (endOfHeadersIndex !=-1)
		{
			return Arrays.copyOfRange(ubaos.getInternalBuffer(), endOfHeadersIndex+4, endOfMessageIndex());
		}
		
		return null;
	}

	@Override
	public String toString()
	{
		return "HTTPRawMessage [endOfMessage=" + endOfHeadersIndex
				+ ", contentLength=" + contentLength + ", headers=" + headers
				+ ", firstLine=" + firstLine + ", baos=" +ubaos.size()+"]";
	}

}