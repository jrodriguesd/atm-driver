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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jpos.atmc.dao.ATMProtocolManager;
import org.jpos.atmc.model.ATMProtocol;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;

@Path("atmprotocols")
public class ATMProtocolWSEndpoint 
{

	@GET
    @Path("/unique")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<ATMProtocol> getUnique() 
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
    	try 
		{
			List<ATMProtocol> atmprotocols = DB.exec(db -> new ATMProtocolManager(db).getATMProtocolUnique() );
	        return atmprotocols;
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
		}

        return null;
    }

}
