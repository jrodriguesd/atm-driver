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
 * Returns days to New Year.
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jfrd.webapp;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jpos.atmc.dao.ATMConfigManager;
import org.jpos.atmc.model.ATMConfig;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;

@Path("atmconfigs")
public class ATMConfigWSEndpoint 
{
	private static final String OBJECT_TYPE = "ATMConfig";

	@GET
    @Path("/unique")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<ATMConfig> getUnique() 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
    	try 
		{
			List<ATMConfig> atmconfigs = DB.exec(db -> new ATMConfigManager(db).getATMConfigUnique() );
	        return atmconfigs;
		} 
		catch (Exception e) 
		{
			Log.printStackTrace(e);
		}

        return null;
    }

	@GET
    @Path("/getall")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<ATMConfig> getAll() 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
    	try 
		{
    		//arreglar el order by
			List<ATMConfig> atmconfigs = DB.exec(db -> new ATMConfigManager(db).getAll() );
	        return atmconfigs;
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
    public Response create(ATMConfig atmCnf) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + atmCnf.toString() );
    	try 
    	{
    		ATMConfig atmconfig = DB.exec(db -> new ATMConfigManager(db).getATMConfig(atmCnf.getConfigId(), atmCnf.getLanguageATM() ) );

		    if (atmconfig == null)
            {
		        DB.execWithTransaction(db -> { 
                    db.session().persist(atmCnf);
			      	return atmCnf; 
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
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(ATMConfig atmCnf) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + atmCnf.toString() );
    	try 
    	{
    		ATMConfig atmconfig = DB.exec(db -> new ATMConfigManager(db).getATMConfig(atmCnf.getConfigId(), atmCnf.getLanguageATM() ) );

		    if (atmconfig != null)
		    {
		    	// Update Atmconfig
		    	atmconfig.setDesc(atmCnf.getDesc());
		    	atmconfig.setLanguage639(atmCnf.getLanguage639());
		    	atmconfig.setLanguageIndex(atmCnf.getLanguageIndex());
		    	// atmconfig.setScreenGroupBase(atmCnf.getScreenGroupBase());

			    DB.execWithTransaction(db -> { 
                    db.session().update(atmconfig);
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
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(ATMConfig atmCnf) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + atmCnf.toString() );

    	try 
    	{
    		ATMConfig atmconfig = DB.exec(db -> new ATMConfigManager(db).getATMConfig(atmCnf.getConfigId(), atmCnf.getLanguageATM() ) );

		    if (atmconfig != null)
		    {
		    	// Delete Atmconfig
			    DB.execWithTransaction(db -> { 
                    db.session().delete(atmconfig);
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
    	return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

}
