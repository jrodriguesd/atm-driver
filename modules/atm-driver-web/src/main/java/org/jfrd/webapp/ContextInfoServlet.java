/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * atm-driver is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
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
