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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.atmc.dao.StateManager;
import org.jpos.atmc.model.State;
import org.jpos.ee.DB;

@Path("states")
public class StateWSEndpoint 
{
	private static final String OBJECT_TYPE = "State";

	@GET
    @Path("/definition")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getDefinition() 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );

    	String filePath = "states.json";
    	StringBuilder builder = new StringBuilder();
        
        try (BufferedReader buffer = new BufferedReader( new FileReader(filePath) ) ) 
        {
            String str;

            while ((str = buffer.readLine()) != null) 
            {
 
                builder.append(str).append("\n");
            }    	
    	
		} 
	    catch (IOException e) 
	    {
			Log.printStackTrace(e);
		}
	    return builder.toString();
	}

	@GET
    @Path("/{configId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<State> getByConfigId(@PathParam("configId") String configId) 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " configId " + configId );
    	try 
		{
			List<State> states = DB.exec(db -> new StateManager(db).getByConfigId(configId) );
	        return states;
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
    public Response create(State std) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " std " + std.toString()  );
    	try 
    	{
		    State state = DB.exec(db -> new StateManager(db).getState(std.getConfigId(), std.getNumber() ) );

		    if (state == null)
            {
		        DB.execWithTransaction(db -> { 
                    db.session().persist(std);
			      	return std; 
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
    public Response update(State std) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " std " + std.toString()  );
    	try 
    	{
		    State state = DB.exec(db -> new StateManager(db).getState(std.getConfigId(), std.getNumber() ) );

		    if (state != null)
		    {
		    	// Update State
		    	state.setDesc( std.getDesc() );
		    	state.setType( std.getType() );

		    	state.setS1( std.getS1() );
		    	state.setS2( std.getS2() );
		    	state.setS3( std.getS3() );
		    	state.setS4( std.getS4() );
		    	state.setS5( std.getS5() );
		    	state.setS6( std.getS6() );
		    	state.setS7( std.getS7() );
		    	state.setS8( std.getS8() );

			    DB.execWithTransaction(db -> { 
                    db.session().update(state);
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
    public Response delete(State std) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " std " + std.toString()  );
    	try 
    	{
		    State state = DB.exec(db -> new StateManager(db).getState(std.getConfigId(), std.getNumber() ) );

		    if (state != null)
		    {
		    	// Delete State
			    DB.execWithTransaction(db -> { 
                    db.session().delete(state);
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
