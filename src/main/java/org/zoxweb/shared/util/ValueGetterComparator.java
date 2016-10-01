package org.zoxweb.shared.util;

import java.util.Comparator;

abstract public class ValueGetterComparator<T,O>
implements Comparator<T>
{
	
	public static class StringValueGetterComparator<T> 
		extends ValueGetterComparator<T,String>
	{

		public StringValueGetterComparator(boolean accending, ValueGetter<T, String> vg) 
		{
			super(accending, vg);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int compare(T o1, T o2) 
		{
			String st1 = o1 !=null ?  vg.getValue(o1) : null;
			String st2 = o2 !=null ?  vg.getValue(o2) : null;
			int ret = NVConfigComparators.STRING.COMPARATOR.compare(st1, st2);
			if (!accending)
			{
				ret = - ret;
			}
			
			return ret;
		}
		
	}
	
	
	
	
	
	
	
	
	protected  final ValueGetter<T,O> vg;
	protected  final boolean accending;
	public ValueGetterComparator(boolean accending, ValueGetter<T,O> vg)
	{
		this.vg = vg;
		this.accending = accending;
	}
}
