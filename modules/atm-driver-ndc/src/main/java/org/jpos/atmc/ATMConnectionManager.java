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
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */

package org.jpos.atmc;

import java.util.EventObject;
import java.util.HashMap;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOServerEventListener;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class ATMConnectionManager.
 */
public class ATMConnectionManager implements ISOServerEventListener, Configurable
{
    
    /** The name. */
    private final String name = "ATMConnectionManager"; 
    
    /** The channels. */
    private HashMap<String, Object> channels = new HashMap<String, Object>();

    /**
     * Gets the class name.
     *
     * @param o the o
     * @return the class name
     */
    private String getClassName(Object o)
	{
		return o.getClass().getSimpleName();
	}	

    /*
    private static void sendToChannel(ISOChannel isoChannel, String cmd)
	{
		String schema = "file:cfg/ndc-";
		FSDMsg msgOut = new FSDMsg(schema);
		
		msgOut.set("message-class", "1");
		msgOut.set("command-code",  cmd);
		msgOut.dump(Log.out, "");
		Util.send(isoChannel, msgOut);
	}
	*/	

    /** The Constant ISOSERVER_ACCEPT_EVENT. */
    private static final int ISOSERVER_ACCEPT_EVENT            = 0;
    
    /** The Constant ISOSERVER_CLIENT_DISCONNECT_EVENT. */
    private static final int ISOSERVER_CLIENT_DISCONNECT_EVENT = 1;
    
    /**
     * Handle ISO server event.
     *
     * @param event the event
     */
    public void handleISOServerEvent(EventObject event)
	{
		String eventClassName = getClassName(event);
		Object obj = event.getSource();
		String objClassName = getClassName(obj);
		String eventNames[] = {"ISOServerAcceptEvent", "ISOServerClientDisconnectEvent"};
		int eventCode = -1;

	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " event class >" + eventClassName + "<");
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " source class >" + objClassName + "<");

		ISOServer isoServer = null;
		if ( objClassName.equals("ISOServer") )
		    isoServer = (ISOServer) obj;
		else
			return;

        ISOChannel isoChannel = null;
		if (isoServer != null)
		{
			/*
			 * En un ambiente con muchas conecciones esta sera la forma segura de hacer esto ?
			 */
            isoChannel = isoServer.getLastConnectedISOChannel(); 
		}
		else
			return;
		
		for (int i = 0; i < eventNames.length; i++)
		{
			if (eventClassName.equals(eventNames[i]))
			{
				eventCode = i;
			    break;
			}
		}
		
		switch (eventCode)
		{
			case ISOSERVER_ACCEPT_EVENT:
                channels.put(isoChannel.getName(), isoChannel);
			    break;
			case ISOSERVER_CLIENT_DISCONNECT_EVENT:
	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + channels.toString() );
                channels.remove(isoChannel.getName());
			    break;
			default:
			    break;
		}
	}

    /**
     * Sets the configuration.
     *
     * @param cfg the new configuration
     * @throws ConfigurationException the configuration exception
     */
    public void setConfiguration (Configuration cfg) throws ConfigurationException 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	}

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() 
	{ 
        return this.name; 
    }  	
}
