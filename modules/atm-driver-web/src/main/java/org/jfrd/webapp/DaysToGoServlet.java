package org.jfrd.webapp;

import java.io.IOException;
import javax.servlet.http.*;

import org.jfrd.apputils.DateUtils;

public class DaysToGoServlet extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		resp.setContentType("text/plain");
		resp.getWriter().println(new DateUtils().daysToNewYear() + " days remaining in this year. Learn Gradle now!");
	}
}
