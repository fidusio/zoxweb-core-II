/*
 * Copyright (c) 2012-2015 ZoxWeb.com LLC.
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
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public interface BytesValue<V>
{
	byte[] toBytes(V v);
	
	V toValue(byte[] bytes);
	V toValue(byte[] bytes, int offset, int length);
	
	public static final BytesValue<Integer> INT = new  BytesValue<Integer>()
	{
		public byte[] toBytes(Integer in)
		{
			int val = in.intValue();
			byte buffer[] = new byte[Integer.SIZE/Byte.SIZE];
			for (int i = buffer.length; i > 0; i--)
			{
				buffer[i-1] = (byte)val;
				val = val >> 8;
			}
			return buffer;
		}
		
		public Integer toValue(byte buffer[], int offset, int length)
		{
			int value = 0;
			for (int i = offset; i < length; i++)
			{
				value = (value << 8) + (buffer[i] & 0xff);
			}
			return value;
		}

		@Override
		public Integer toValue(byte[] bytes) 
		{
			// TODO Auto-generated method stub
			return toValue(bytes, 0, bytes.length);
		}
	};
	
	public static final BytesValue<Long> LONG = new BytesValue<Long>()
	{
		public byte[] toBytes(Long in)
		{
			long val = in.longValue();
			byte buffer[] = new byte[Long.SIZE/Byte.SIZE];
			for (int i = buffer.length; i > 0; i--)
			{
				buffer[i-1] = (byte)val;
				val = val >> 8;
			}
			return buffer;
			
		}
	
		
		
		/**
		 * @param bytes array
		 * @param offset
		 * @param length
		 * @return Long converted value
		 */
		public Long toValue(byte[] bytes, int offset, int length)
		{
			long value = 0;
			for (int i = offset; i < length; i++)
			{
				value = (value << 8) + (bytes[i] & 0xff);
			}
			return value;
		}

		@Override
		public Long toValue(byte[] bytes)
		{
			// TODO Auto-generated method stub
			return toValue(bytes, 0, bytes.length);
		}
	};
	
	
	public static final BytesValue<Float> FLOAT = new BytesValue<Float>()
	{
		public byte[] toBytes(Float in)
		{
			int val = Float.floatToIntBits(in);
			byte buffer[] = new byte[Float.SIZE/Byte.SIZE];
			for (int i = buffer.length; i > 0; i--)
			{
				buffer[i-1] = (byte)val;
				val = val >> 8;
			}
			return buffer;	
		}
	
		
		
		/**
		 * @param bytes
		 * @param offset
		 * @param length
		 * @return float value
		 */
		public Float toValue(byte bytes[], int offset, int length)
		{
			int value = 0;
			for (int i = offset; i < length; i++)
			{
				 value = (value << 8) + (bytes[i] & 0xff);
			}
			return Float.intBitsToFloat(value);
		}
		
		public Float toValue(byte[] bytes)
		{
			return toValue(bytes, 0, bytes.length);
		}
	};
	
	
	
	public static final BytesValue<Double> DOUBLE = new BytesValue<Double>()
	{
		public byte[] toBytes(Double in)
		{
			long val = Double.doubleToLongBits(in);
			byte buffer[] = new byte[Double.SIZE/Byte.SIZE];
			for (int i = buffer.length; i > 0; i--)
			{
				buffer[i-1] = (byte)val;
				val = val >> 8;
			}
			return buffer;
		}
	
	
		public Double toValue(byte bytes[], int offset, int length)
		{
			long value = 0;
			for (int i = offset; i < length; i++)
			{
				 value = (value << 8) + (bytes[i] & 0xff);
			}
			return Double.longBitsToDouble(value);
		}
		
		public Double toValue(byte[] bytes)
		{
			return toValue(bytes, 0, bytes.length);
		}
	};

	
		
}
