package org.zoxweb.server.http.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zoxweb.shared.http.URIScheme;

public class SecureOnlyFilter 
implements Filter
{
	private static final Logger log = Logger.getLogger(SecureOnlyFilter.class.getName());
	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException 
	{
		// TODO Auto-generated method stub
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if (!req.isSecure())
		{
			String uri = req.getRequestURI();
			URIScheme uriScheme = URIScheme.match(req.getScheme());
			String getDomain = req.getServerName();
			String getPort = Integer.toString(req.getServerPort());
			 
			if (uriScheme == URIScheme.HTTP || uriScheme == URIScheme.WS) {
			 
				// Set response content type
				res.setContentType("text/html");
				 
				// New location to be redirected
				String httpsPath = URIScheme.HTTPS + "://" + getDomain + ":" + getPort
				+ uri;
				 
				String site = new String(httpsPath);
				res.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				res.setHeader("Location", site);
				log.info("Redirect:" + httpsPath);
				return;
			
			
			}
		}
		 
		// Pass request back down the filter chain
		filterChain.doFilter(req, res);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException 
	{
		// TODO Auto-generated method stub
		
	}

}
