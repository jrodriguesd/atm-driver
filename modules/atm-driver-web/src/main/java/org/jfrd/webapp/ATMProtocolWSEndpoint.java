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
			Log.printStackTrace(e);
		}

        return null;
    }

}
