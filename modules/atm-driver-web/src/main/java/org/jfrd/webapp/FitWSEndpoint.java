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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jpos.atmc.dao.FitManager;
import org.jpos.atmc.model.Fit;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;

@Path("fits")
public class FitWSEndpoint 
{
	private static final String OBJECT_TYPE = "Fit";

	@GET
    @Path("/{configId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Fit> getByConfigId(@PathParam("configId") String configId) 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " configId " + configId );
    	try 
		{
			List<Fit> screens = DB.exec(db -> new FitManager(db).getByConfigId(configId) );
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
    public Response create(Fit fit) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + fit.toString() );
    	try 
    	{
		    Fit fitent = DB.exec(db -> new FitManager(db).getFit(fit.getConfigId(), fit.getNumber() ) );

		    if (fitent == null)
            {
		        DB.execWithTransaction(db -> { 
                    db.session().persist(fit);
			      	return fit; 
			    } );
            }
			else
			{
				String json = "{\"msg\":\"" + OBJECT_TYPE + " Already Exist\"}";
				return Response.status(Status.METHOD_NOT_ALLOWED).type(MediaType.APPLICATION_JSON).entity(json).build();
			}

		} 
    	catch (Exception e) 
    	{
			Log.printStackTrace(e);
			String json = "{\"msg\":\"General Error\"}";
			return Response.status(Status.METHOD_NOT_ALLOWED).type(MediaType.APPLICATION_JSON).entity(json).build();
		}

		String json = "{\"msg\":\"" + OBJECT_TYPE + " Created\"}";
    	return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Fit fit) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + fit.toString() );
    	try 
    	{
		    Fit fitent = DB.exec(db -> new FitManager(db).getFit(fit.getConfigId(), fit.getNumber() ) );

		    if (fitent != null)
		    {
		    	// Update Fit
		    	fitent.setAlgoidx( fit.getAlgoidx() );
		    	fitent.setBinPrefix( fit.getBinPrefix() );
		    	fitent.setDecimaltab( fit.getDecimaltab() );
		    	fitent.setDesc( fit.getDesc() );
		    	fitent.setEncpinkey( fit.getEncpinkey() );
		    	fitent.setIdxrefpoints( fit.getIdxrefpoints() );
		    	fitent.setIndirectstateidx( fit.getIndirectstateidx() );
		    	fitent.setLangcodeidx( fit.getLangcodeidx() );
		    	fitent.setLocalpinchecklen( fit.getLocalpinchecklen() );
		    	fitent.setMaxpinlen( fit.getMaxpinlen() );
		    	fitent.setPanlen( fit.getPanlen() );
		    	fitent.setPanlocidx( fit.getPanlocidx() );
		    	fitent.setPanpad( fit.getPanpad() );
		    	fitent.setPinblkformat( fit.getPinblkformat() );
		    	fitent.setPinoffsetidx( fit.getPinoffsetidx() );
		    	fitent.setPinpad( fit.getPinpad() );
		    	fitent.setPinretrycount( fit.getPinretrycount() );

			    DB.execWithTransaction(db -> { 
                    db.session().update(fitent);
			    	return 1; 
			    } );

		    }
		    else
		    {
				String json = "{\"msg\":\"" + OBJECT_TYPE + " Not Found\"}";
		    	return Response.status(Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(json).build();
		    }
		} 
    	catch (Exception e) 
    	{
			Log.printStackTrace(e);
			String json = "{\"msg\":\"General Error\"}";
			return Response.status(Status.METHOD_NOT_ALLOWED).type(MediaType.APPLICATION_JSON).entity(json).build();
		}

		String json = "{\"msg\":\"" + OBJECT_TYPE + " Updated\"}";
    	return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Fit fit) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + fit.toString() );
    	try 
    	{
		    Fit fitent = DB.exec(db -> new FitManager(db).getFit(fit.getConfigId(), fit.getNumber() ) );

		    if (fitent != null)
		    {
		    	// Delete Screen
			    DB.execWithTransaction(db -> { 
                    db.session().delete(fitent);
			    	return 1; 
			    } );

		    }
		    else
		    {
				String json = "{\"msg\":\"" + OBJECT_TYPE + " Not Found\"}";
		    	return Response.status(Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(json).build();
		    }
		} 
    	catch (Exception e) 
    	{
			Log.printStackTrace(e);
			String json = "{\"msg\":\"General Error\"}";
			return Response.status(Status.METHOD_NOT_ALLOWED).type(MediaType.APPLICATION_JSON).entity(json).build();
		}

		String json = "{\"msg\":\"" + OBJECT_TYPE + " Deleted\"}";
    	return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

}
