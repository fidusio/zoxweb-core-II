package org.zoxweb.server.http.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zoxweb.server.util.ApplicationConfigManager;
import org.zoxweb.shared.data.ApplicationConfigDAO.ApplicationDefaultParam;
import org.zoxweb.shared.http.URIScheme;

public class SecureOnlyFilter 
implements Filter
{
	private static final Logger log = Logger.getLogger(SecureOnlyFilter.class.getName());
	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		log.info("Destroyed");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException 
	{
		// TODO Auto-generated method stub
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if (ApplicationConfigManager.SINGLETON.loadDefault().isSecureEnabled() && !req.isSecure())
		{
			String uri = req.getRequestURI();
			URIScheme uriScheme = URIScheme.match(req.getScheme());
			String hostname = ApplicationConfigManager.SINGLETON.loadDefault().lookupValue("application_host");
			if (hostname == null)
				hostname = req.getServerName();
			//String getPort = Integer.toString(req.getServerPort());
			
			URIScheme redirectScheme = null;
			switch(uriScheme)
			{
			
			case HTTP:
				redirectScheme = URIScheme.HTTPS;
				break;
			case WS:
				redirectScheme = URIScheme.WSS;
				break;
			
			default:
				break;
			
			}
			
			
			if (redirectScheme != null) {
			 
				// Set response content type
				res.setContentType("text/html");
				 
				// New location to be redirected
				String httpsPath = redirectScheme + "://" + hostname + uri;
				if (ApplicationConfigManager.SINGLETON.loadDefault().lookupValue(ApplicationDefaultParam.SECURE_URL) != null)
				{
					httpsPath = ApplicationConfigManager.SINGLETON.loadDefault().lookupValue(ApplicationDefaultParam.SECURE_URL);
				}
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
		log.info(filterConfig.getFilterName());
		
		
		StringBuilder sb = new StringBuilder();
		Enumeration<String> e = filterConfig.getInitParameterNames();
		while(e.hasMoreElements())
		{
			if(sb.length() > 0)
			{
				sb.append(", ");
			}
			
			sb.append(e.nextElement());
			
		}
		log.info(sb.toString());
		
	}

}
