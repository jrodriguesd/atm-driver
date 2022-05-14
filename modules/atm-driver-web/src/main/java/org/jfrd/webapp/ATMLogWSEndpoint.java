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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.jfrd.jwt.JWTTokenNeeded;
import org.jfrd.webapp.dto.AtmLogGrid;
import org.jpos.atmc.dao.ATMLogManager;

import org.jpos.atmc.model.ATMLog;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

import org.jpos.ee.DB;

@Path("atmlogs")
public class ATMLogWSEndpoint {

	@GET
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ATMLog getById(@PathParam("id")Long id) 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " id " + id );

    	try 
		{
			ATMLog atmLog = DB.exec(db -> new ATMLogManager(db).getATMLog(id) );
			
			// atmlog_atm_confirmation_dt
			// Instant instantZ = atmLog.getAtmConfirmationDt();
	    	// Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " instantZ " + instantZ );
			// ZonedDateTime ecTime = instantZ.atZone(ZoneId.of(atmLog.getTimezone()));
	    	// Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " ecTime " + ecTime );
	    	// Instant instantec = ecTime.toInstant();
	    	// Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " instantec " + instantec );
	    	// atmLog.setAtmConfirmationDt(instantec);
	        return atmLog;
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
		}

        return null;
	}

	@GET
    @Path("/atmLoGrid")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@JWTTokenNeeded
    public AtmLogGrid atmLoGrid(@Context UriInfo uriInfo) 
	{
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 

        AtmLogGrid atmLogGrid = AtmLogGrid.getAtmLogGrid(queryParams);
        
		return atmLogGrid;
	}
	
}
