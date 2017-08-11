package org.zoxweb.shared.filters;

import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class AppIDNameFilter 
implements ValueFilter<String, String> 
{
	
	public static final AppIDNameFilter SINGLETON  = new AppIDNameFilter();

	private AppIDNameFilter()
	{
		
	}
	
	@Override
	public String toCanonicalID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validate(String in) throws NullPointerException, IllegalArgumentException {
		// TODO Auto-generated method stub
		if(isValid(in))
			return in.trim().toLowerCase();
		SharedUtil.checkIfNulls("Null APP name", in);
		
		throw new IllegalArgumentException("Invalid app name " + in);
	}

	@Override
	public boolean isValid(String in) 
	{
		if (!SharedStringUtil.isEmpty(in))
		{
			in = in.trim();
			for (int i = 0; i < in.length(); i++)
			{
				if (!Character.isLetterOrDigit(in.charAt(i)))
				{
					return false;
				}	
			}
			return true;
		}
			
		// TODO Auto-generated method stub
		return false;
	}

}
