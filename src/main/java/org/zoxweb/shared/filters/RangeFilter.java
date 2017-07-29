package org.zoxweb.shared.filters;

@SuppressWarnings("serial")
abstract public class RangeFilter<V extends Number>
implements ValueFilter<V, V> 
{
	
	protected final V upperLimit;
	protected final boolean upperLimitInclusive;
	protected final V lowerLimit;
	
	protected final boolean lowerLimitInclusive;

	
	
	public V getLowerLimit()
	{
		return lowerLimit;
	}
	
	public V getUpperLimit()
	{
		return upperLimit;
	}
	
	
	protected RangeFilter(V lowerLimit, boolean lowerLimitInclusive, V upperLimit, boolean upperLimitInclusive)
	{
		this.lowerLimit = lowerLimit;
		this.lowerLimitInclusive = lowerLimitInclusive;
		this.upperLimit = upperLimit;
		this.upperLimitInclusive = upperLimitInclusive;
	}
	
	
	
	

	

	


}
