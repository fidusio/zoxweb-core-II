package org.zoxweb.shared.util;

import java.util.Comparator;

public abstract class ValueGetterComparator<I,O>
    implements Comparator<I>
{
    public static class StringValueGetterComparator<I>
        extends ValueGetterComparator<I,String>
    {

		public StringValueGetterComparator(boolean accending, ValueGetter<I, String> vg)
        {
		    super(accending, vg);
		}

		@Override
		public int compare(I o1, I o2)
        {
			String st1 = o1 != null ?  vg.getValue(o1) : null;
			String st2 = o2 != null ?  vg.getValue(o2) : null;
			int ret = NVConfigComparators.STRING.COMPARATOR.compare(st1, st2);

			if (!accending)
			{
				ret = - ret;
			}
			
			return ret;
		}
	}

	protected final ValueGetter<I,O> vg;
	protected final boolean accending;

	public ValueGetterComparator(boolean accending, ValueGetter<I,O> vg)
    {
		this.vg = vg;
		this.accending = accending;
	}

}