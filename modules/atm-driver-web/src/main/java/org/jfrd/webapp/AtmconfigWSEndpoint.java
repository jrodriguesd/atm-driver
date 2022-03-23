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

import org.jfrd.webapp.dao.AtmconfigManager;
import org.jfrd.webapp.model.Atmconfig;

import org.jfrd.webapp.util.Log;
import org.jfrd.webapp.util.Util;
import org.jpos.ee.DB;

@Path("atmconfig")
public class AtmconfigWSEndpoint 
{
	@GET
    @Path("/{Unique}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Atmconfig> getUnique() 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
    	try 
		{
			List<Atmconfig> atmconfigs = DB.exec(db -> new AtmconfigManager(db).getAtmconfigUnique() );
	        return atmconfigs;
		} 
		catch (Exception e) 
		{
			Log.printStackTrace(e);
		}

        return null;
    }

    @POST
    @Path("/Create")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Atmconfig atmCnf) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + atmCnf.toString() );
    	try 
    	{
    		Atmconfig atmconfig = DB.exec(db -> new AtmconfigManager(db).getAtmconfig(atmCnf.getAtmcnf_configid(), atmCnf.getAtmcnf_languageatm() ) );

		    if (atmconfig == null)
            {
		        DB.execWithTransaction(db -> { 
                    db.session().persist(atmCnf);
			      	return atmCnf; 
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
    @Path("/Update")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Atmconfig atmCnf) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + atmCnf.toString() );
    	try 
    	{
    		Atmconfig atmconfig = DB.exec(db -> new AtmconfigManager(db).getAtmconfig(atmCnf.getAtmcnf_configid(), atmCnf.getAtmcnf_languageatm() ) );

		    if (atmconfig != null)
		    {
		    	// Update Atmconfig
		    	atmconfig.setAtmcnf_description(atmCnf.getAtmcnf_description());
		    	atmconfig.setAtmcnf_language639(atmCnf.getAtmcnf_language639());
		    	atmconfig.setAtmcnf_languageindex(atmCnf.getAtmcnf_languageindex());
		    	atmconfig.setAtmcnf_screengroupbase(atmCnf.getAtmcnf_screengroupbase());

			    DB.execWithTransaction(db -> { 
                    db.session().update(atmconfig);
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
    @Path("/Delete")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Atmconfig atmCnf) 
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + atmCnf.toString() );
    	try 
    	{
    		Atmconfig atmconfig = DB.exec(db -> new AtmconfigManager(db).getAtmconfig(atmCnf.getAtmcnf_configid(), atmCnf.getAtmcnf_languageatm() ) );

		    if (atmconfig != null)
		    {
		    	// Delete Atmconfig
			    DB.execWithTransaction(db -> { 
                    db.session().delete(atmconfig);
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
