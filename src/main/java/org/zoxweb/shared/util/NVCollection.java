package org.zoxweb.shared.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class NVCollection<T>
	extends NVBase<Collection<T>>
{
	public NVCollection()
	{
		setValue(new ArrayList<T>());
	}
	
	public NVCollection(String name)
	{
		this(name,new ArrayList<T>());
	}
	
	public NVCollection(String name, List<T> values)
	{
		setName(name);
		setValue(values);
	}
	
	public NVCollection(String name, Set<T> values)
	{
		setName(name);
		setValue(values);
	}
}
