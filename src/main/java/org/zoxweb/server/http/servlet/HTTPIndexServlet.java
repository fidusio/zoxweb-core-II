package org.zoxweb.server.http.servlet;

import java.io.IOException;




import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.zoxweb.server.util.ApplicationConfigManager;
import org.zoxweb.shared.data.ApplicationConfigDAO;
import org.zoxweb.shared.data.ApplicationConfigDAO.ApplicationDefaultParam;
import org.zoxweb.shared.util.SharedStringUtil;




@SuppressWarnings("serial")
public class HTTPIndexServlet
extends HttpServlet
{
	
	private static final transient Logger log = Logger.getLogger("");
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException,
				   IOException
	{
		ApplicationConfigDAO acd = null;
		String url = null;
		try
		{
			acd = ApplicationConfigManager.SINGLETON.loadDefault();
			//req.getLocalAddr();
			String forwardTo = SharedStringUtil.embedText(SharedStringUtil.trimOrNull(acd.lookupValue(ApplicationDefaultParam.FORWARD_URL)), "$$LOCAL_IP$$",  SharedStringUtil.toLowerCase(req.getLocalAddr()));
			url = resp.encodeRedirectURL(forwardTo);
			
		}
		catch( Exception e)
		{
			log.info("error:" + e);
		}
		
		
		if ( url != null)
		{
			log.info("redirect:" + req.getRequestURL() + (req.getRequestURI() != null ? req.getRequestURI() :"")+ " from:"+ req.getHeader("User-Agent") + "-" + req.getRemoteAddr() + " Redirected URL:" + url);
			resp.sendRedirect( url);
		}
		
	}
}
