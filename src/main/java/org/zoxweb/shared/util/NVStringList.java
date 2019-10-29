package org.zoxweb.shared.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class NVStringList
extends NVBase<List<String>>
{
	/**
	 * The default constructor (Java Bean compliant).
	 */
	public NVStringList()
	{
		super((String)null, new ArrayList<String>());
	}
	
	
	
	/**
	 * This constructor instantiates NVLongList based on name value.
	 * @param name
	 * @param value
	 */
	public NVStringList(String name, List<String> value)
	{
		super(name, value);
	}
	
	/**
	 * This constructor instantiates NVLongList based on name value.
	 * @param name
	 */
	public NVStringList(String name)
	{
		super(name, new ArrayList<String>());
	}


	public void setValues(String ... vals)
	{
		value.clear();
		value.addAll(Arrays.asList(vals));
	}

	public String[] getValues()
	{
		return value.toArray(new String[value.size()]);
	}
	  
}
