package org.jfrd.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextInfoServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/plain");

        PrintWriter out = resp.getWriter();
        ServletContext context = req.getServletContext();

        out.println("ServletContext attributes\n-----\n");
        Collections.list(context.getAttributeNames())
                .forEach((name) -> {
                    Object obj = context.getAttribute(name);
                    out.printf("%s: (%s) %s%n", name, obj.getClass().getName(), obj);
                });

        out.println();
        out.println("ServletContext init-params\n-----\n");
        Collections.list(context.getInitParameterNames())
                .forEach(name -> out.printf("%s: %s%n", name, context.getInitParameter(name)));

    }
}
