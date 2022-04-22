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
package org.jpos.atmc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import org.jdom2.JDOMException;
import org.jpos.atmc.dao.ATMManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.ndc.ProcessSolicitedStatus.HeaderStrategy;
import org.jpos.atmc.util.Crypto;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.NDCISOMsg;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.DB;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.FSDISOMsg;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.space.LocalSpace;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.GroupSelector;
import org.jpos.util.FSDMsg;

public class SwichByMSgClass implements GroupSelector, Configurable 
{
    private String source;
    private String request;
    private String response;
    private LocalSpace isp;
    private long timeout = 70000L;
    private Configuration cfg; 
    private freemarker.template.Configuration fmCfg; 
    private HeaderStrategy headerStrategy;
    private boolean remote = false;

    /**
     * get IP Adddress of conected client
     *
     */
    public String getIPAddr(Socket socket)
	{
		String retStr = "";

        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        
        if (socketAddress instanceof InetSocketAddress) 
		{
            InetAddress inetAddress = ((InetSocketAddress)socketAddress).getAddress();
            if (inetAddress instanceof Inet4Address)
			{
			    retStr = inetAddress.toString();
			    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " IPv4: " + inetAddress.toString() );
				
			}
            else if (inetAddress instanceof Inet6Address)
			{
			    retStr = inetAddress.toString();
			    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " IPv6: " + inetAddress.toString() );
			}
            else
			{
            	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Not an IP address" );
			}
        } 
		else 
		{
			Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Not an internet protocol socket" );
		}
		return retStr.substring(1);
	}

    @Override
	public int prepare(long id, Serializable context) 
    {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        Context ctx = (Context) context;
        ISOSource source = (ISOSource) ctx.get (this.source);
        if (source == null || !source.isConnected())
            return ABORTED | READONLY | NO_JOIN;

        return PREPARED | READONLY;
	}

	@Override
	public String select(long id, Serializable context) 
	{
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        Context ctx = (Context) context;
        ISOSource source = (ISOSource) ctx.get (this.source);
        ISOMsg m = (ISOMsg) ctx.get (this.request);

        BaseChannel baseChannel = (BaseChannel) source;
		Socket socket = baseChannel.getSocket();
		String ip = getIPAddr(socket);
        
		ATM atm = null;
		try 
		{
			atm = DB.exec(db -> new ATMManager(db).findByIP(ip));
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
			return null;
		}
		ctx.put ("atm", atm, remote );

		String atmProtocol = atm.getProtocol().toLowerCase();
		String protocolSchema = "file:cfg/" + atmProtocol + "/" + atmProtocol + "-";
		
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		NDCFSDMsg protocolFSDmsg = new NDCFSDMsg(protocolSchema);
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );

		byte bmsgMAC[] = null;
		byte msg[] = null;
		byte msgWrk[] = null;
		try 
		{
			FSDISOMsg msgIn    = ((FSDISOMsg) m);
			FSDMsg fsdMsgIn = (FSDMsg) msgIn.getFSDMsg();
			// NDCFSDMsg fsdMsgIn = new NDCFSDMsg(msgIn.getFSDMsg());
			String strMessage  = fsdMsgIn.get("message");

			msg = fsdMsgIn.packToBytes();

 			Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
			fsdMsgIn.dump(Log.out, "");

			InputStream byteInputStream = new ByteArrayInputStream(strMessage.getBytes(StandardCharsets.ISO_8859_1));
			protocolFSDmsg.unpack(byteInputStream);

			String strMAC = protocolFSDmsg.get("mac");
			if (strMAC != null)
			{
			    bmsgMAC = strMAC.getBytes(StandardCharsets.ISO_8859_1);

			    // Quitarle el MAC al msg - Inicio
				Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	            Util.printHexDump(Log.out, msg);

	            NDCFSDMsg wrkFSDmsg = (NDCFSDMsg) protocolFSDmsg.clone();
		  		wrkFSDmsg.set("mac", null);
		  		msgWrk = wrkFSDmsg.packToBytes();
				
		  		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	            Util.printHexDump(Log.out, msgWrk);
		  		// Quitarle el MAC al msg - Fin
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
			return null;
		}

  		if (bmsgMAC != null)
		{
	      	String key = "0123456789ABCDEF";
	  		byte bKey[]  = ISOUtil.hex2byte(key);

			byte calculatedMAC[] = null; 
	  		try 
	  		{
	  			calculatedMAC = Crypto.calculateANSIX9_9MAC(bKey, msgWrk);
			} 
	  		catch (GeneralSecurityException e) 
	  		{
				Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
				e.printStackTrace(Log.out);
			}

	  		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " calculatedMAC " + ISOUtil.byte2hex(calculatedMAC) );
	  		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " bmsgMAC " + ISOUtil.byte2hex(bmsgMAC) );

	  		if (! Arrays.equals(calculatedMAC,bmsgMAC ) )
	  		{
				Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " ** MAC Error ** " );
	            Util.printHexDump(Log.out, msg);

		        ctx.put ("fsdMsgIn", protocolFSDmsg, remote);
			    String groups = cfg.get ("error", null);
			    return groups;
	  		}
		}

		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		protocolFSDmsg.dump(Log.out, "");

        ctx.put ("fsdMsgIn", protocolFSDmsg, remote);

        String key = atmProtocol + "." + protocolFSDmsg.get("message-class"); 
	    String groups = cfg.get (key, null);
	    return groups;
	}

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException 
    {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        this.cfg = cfg;
        source   = cfg.get ("source",   ContextConstants.SOURCE.toString());
        request  = cfg.get ("request",  ContextConstants.REQUEST.toString());
        response = cfg.get ("response", ContextConstants.RESPONSE.toString());
        timeout  = cfg.getLong ("timeout", timeout);
        fmCfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
        try 
        {
            headerStrategy = HeaderStrategy.valueOf(
            cfg.get("header-strategy", "PRESERVE_RESPONSE").toUpperCase() );
        } 
        catch (IllegalArgumentException e) 
        {
            throw new ConfigurationException (e.getMessage());
        }
    }

}
