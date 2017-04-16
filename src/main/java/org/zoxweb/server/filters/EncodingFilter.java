package org.zoxweb.server.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.SharedStringUtil;

public class EncodingFilter
	implements Filter
{

	private static final transient Logger log = Logger.getLogger(Const.LOGGER_NAME);
	
	private String encoding = SharedStringUtil.UTF_8;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException
	{
		request.setCharacterEncoding(encoding);
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig)
        throws ServletException
	{
		String encodingParam = filterConfig.getInitParameter("encoding");

		if (encodingParam != null)
		{
			encoding = encodingParam;
		}

		log.info("Encoding:" + encoding);
	}

	public void destroy()
	{

	}

}