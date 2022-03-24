/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Returns days to New Year.
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jfrd.webapp;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfrd.webapp.dao.ScreenManager;

import org.jfrd.webapp.model.Screen;

import org.jfrd.webapp.util.Log;
import org.jfrd.webapp.util.Util;
import org.jpos.ee.DB;

@Path("screens")
public class ScreenWSEndpoint 
{
	@GET
    @Path("/{configId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Screen> getByConfigId(@PathParam("configId") String configId) 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " configId " + configId );
    	try 
		{
			List<Screen> screens = DB.exec(db -> new ScreenManager(db).getItemsByParam("configId", configId) );
	        return screens;
		} 
		catch (Exception e) 
		{
			Log.printStackTrace(e);
		}

        return null;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Screen scr) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + scr.toString() );
    	try 
    	{
		    Screen screen = DB.exec(db -> new ScreenManager(db).getScreen(scr.getConfigId(), scr.getNumber() ) );

		    if (screen == null)
            {
		        DB.execWithTransaction(db -> { 
                    db.session().persist(scr);
			      	return scr; 
			    } );
            }

		} 
    	catch (Exception e) 
    	{
			Log.printStackTrace(e);
		}

    	String json = "{\"msg:\":\"Ok\"}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Screen scr) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + scr.toString() );
    	try 
    	{
		    Screen screen = DB.exec(db -> new ScreenManager(db).getScreen(scr.getConfigId(), scr.getNumber() ) );

		    if (screen != null)
		    {
		    	// Update Screen
		    	screen.setData(scr.getData());
		    	screen.setDesc(scr.getDesc());
		    	screen.setNumber(scr.getNumber());

			    DB.execWithTransaction(db -> { 
                    db.session().update(screen);
			    	return 1; 
			    } );

		    }
		} 
    	catch (Exception e) 
    	{
			Log.printStackTrace(e);
		}

    	String json = "{\"msg:\":\"Ok\"}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Screen scr) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + scr.toString() );
    	try 
    	{
		    Screen screen = DB.exec(db -> new ScreenManager(db).getScreen(scr.getConfigId(), scr.getNumber() ) );

		    if (screen != null)
		    {
		    	// Delete Screen
			    DB.execWithTransaction(db -> { 
                    db.session().delete(screen);
			    	return 1; 
			    } );

		    }
		} 
    	catch (Exception e) 
    	{
			Log.printStackTrace(e);
		}

    	String json = "{\"msg:\":\"Ok\"}";
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    
}
