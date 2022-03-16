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

public class ATMConnectionManager implements ISOServerEventListener, Configurable
{
    private final String name = "ATMConnectionManager"; 
    private HashMap<String, Object> channels = new HashMap<String, Object>();

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

    private static final int ISOSERVER_ACCEPT_EVENT            = 0;
    private static final int ISOSERVER_CLIENT_DISCONNECT_EVENT = 1;
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

    public void setConfiguration (Configuration cfg) throws ConfigurationException 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	}

    public String getName() 
	{ 
        return this.name; 
    }  	
}
