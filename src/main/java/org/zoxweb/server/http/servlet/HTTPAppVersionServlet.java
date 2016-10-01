package org.zoxweb.server.http.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.util.ApplicationConfigManager;
import org.zoxweb.shared.data.ApplicationConfigDAO.ApplicationDefaultParam;
import org.zoxweb.shared.http.HTTPMimeType;


@SuppressWarnings("serial")
public class HTTPAppVersionServlet 
	extends HttpServlet
{
	
	private AtomicReference<String> version = new AtomicReference<String>();
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			  throws ServletException, IOException 
	{
		if (version.get() == null)
		{
			version.set(IOUtil.inputStreamToString(this.getClass().getResourceAsStream(ApplicationConfigManager.SINGLETON.loadDefault().lookupValue(ApplicationDefaultParam.APPLICATION_VERSION_RESOURCE)), true));
		}
		
		
		resp.setContentType(HTTPMimeType.APPLICATION_JSON.getValue());
		resp.getWriter().write(version.get());
		
	}

	
	
	
}
