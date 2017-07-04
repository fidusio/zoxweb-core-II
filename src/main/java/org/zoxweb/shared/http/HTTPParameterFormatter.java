package org.zoxweb.shared.http;

import org.zoxweb.shared.filters.ValueFilter;
import org.zoxweb.shared.util.GetNameValue;


public enum HTTPParameterFormatter
implements GetNameValue<Character>, ValueFilter<GetNameValue<String>, String>
{
	
	URL_ENCODED('=', '&')
	{
		

		@Override
		public String validate(GetNameValue<String> nvp) throws NullPointerException, IllegalArgumentException {
			// TODO Auto-generated method stub
			if (!isValid(nvp))
			{
				throw new IllegalArgumentException("Invalid NVP:" + nvp);
			}
			return nvp.getName() + getNameValueSep() + nvp.getValue();
		}

		@Override
		public boolean isValid(GetNameValue<String> nvp) 
		{
			// TODO Auto-generated method stub
			return nvp!=null && nvp.getValue()!=null && nvp.getName() != null;	
		}

		@Override
		public String toCanonicalID() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	URI_REST_ENCODED((char)0, '/')
	{

		@Override
		public String validate(GetNameValue<String> nvp) throws NullPointerException, IllegalArgumentException {
			if (!isValid(nvp))
			{
				throw new IllegalArgumentException("Invalid NVP:" + nvp);
			}
			return nvp.getValue();
		}

		@Override
		public boolean isValid(GetNameValue<String> nvp) {
			// TODO Auto-generated method stub
			return nvp!=null && nvp.getValue()!=null;
		}

		@Override
		public String toCanonicalID() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	
	;
	
	private final char paramSep;
	private final char nvSep;
	
	HTTPParameterFormatter(char nvSep, char sep)
	{
		paramSep = sep;
		this.nvSep = nvSep;
	}

	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name().toLowerCase();
	}

	/**
	 * Return the value of the parameter separator
	 */
	@Override
	public Character getValue() {
		// TODO Auto-generated method stub
		return paramSep;
	}
	
	public Character getNameValueSep()
	{
		return nvSep == 0 ? null : nvSep;
	}
	
	
	public StringBuilder format(StringBuilder sb, GetNameValue<String> nvp)
	{
		if (sb == null)
		{
			sb = new StringBuilder();
		}
		if (isValid(nvp))
		{
			if (sb.length() > 0)
			{
				if(sb.charAt(sb.length() - 1) != paramSep)
				{
					sb.append(paramSep);
				}
			}
			sb.append(validate(nvp));
		}
		return sb;
	}
	
	public String format(StringBuilder sb, GetNameValue<String>[] nvps)
	{
		for (GetNameValue<String> nvp :nvps)
		{ 
			sb = format(sb, nvp);
		}
		
		return sb.toString();
	}
	
}
