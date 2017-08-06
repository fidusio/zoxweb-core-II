package org.zoxweb.server.http.servlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zoxweb.server.http.HTTPRequestAttributes;
import org.zoxweb.server.http.servlet.HTTPServletUtil;
import org.zoxweb.shared.api.APIError;
import org.zoxweb.shared.http.HTTPStatusCode;

public class HTTPRequestAttributesFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) 
			throws IOException, ServletException 
	{
		try
		{
			HttpServletRequest request = (HttpServletRequest)req;
			HTTPRequestAttributes hra = HTTPServletUtil.extractRequestAttributes(request); 
			// must validate with security manager
			request.setAttribute(HTTPRequestAttributes.HRA, hra);

			filterChain.doFilter(req, resp);
		}
		catch(Exception e)
		{			
			HTTPServletUtil.sendJSON(null, (HttpServletResponse)resp, HTTPStatusCode.BAD_REQUEST, new APIError(e));
		}
        

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
