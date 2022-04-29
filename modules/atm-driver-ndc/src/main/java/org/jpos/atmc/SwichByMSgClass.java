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
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;

import org.jdom2.JDOMException;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.ATMLog;
import org.jpos.atmc.model.Currency;
import org.jpos.atmc.model.TrnDefinition;
import org.jpos.atmc.dao.ATMLogManager;
import org.jpos.atmc.dao.ATMManager;
import org.jpos.atmc.dao.TrnDefinitionManager;
import org.jpos.atmc.dao.CurrencyManager;

import org.jpos.atmc.ndc.ProcessSolicitedStatus.HeaderStrategy;
import org.jpos.atmc.util.Crypto;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.InvalidCardException;
import org.jpos.core.Track2;
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

    private String getCard(String trk2) throws InvalidCardException
    {
		String validNums = "0123456789";
		char firstChar = trk2.charAt(0);
		if ( validNums.indexOf(firstChar) < 0)
		{
		    trk2 = trk2.substring(1, trk2.length() - 1);
		}

        Track2 t2 = Track2.builder()
            .track( trk2 ).build();        

	    return t2.getPan();
    }

    private void createATMLog(Context ctx, NDCFSDMsg msgIn) throws Exception
    {
    	ATM atm = ctx.get ("atm");
    	TrnDefinition td = DB.exec(db -> new TrnDefinitionManager(db).getTrnDefinition(atm.getConfigId(), msgIn.get("operation-code-data")));
    	if (td != null) ctx.put("transactiondefinition", td, remote);

    	ATMLog atmLog = new ATMLog();
        atmLog.setAtmRequest( Util.dum2Str(msgIn) );
        atmLog.setAtmRequestDt( Instant.now() );
        atmLog.setTimezone( ZoneId.systemDefault().toString() );
        atmLog.setIp(atm.getIp());
        atmLog.setLuno(atm.getLuno());
        atmLog.setMessageClass( msgIn.get("message-class") );
        
        if ( msgIn.get("message-class").equalsIgnoreCase("11") )
        {
        	atmLog.setCard( getCard( msgIn.get("track2") ) );
        	atmLog.setOpCode( msgIn.get("operation-code-data") );
        	if (td != null)
        	{
            	atmLog.setCurrencyCode(td.getCurrencyCode());
            	
            	String amount =  msgIn.get("amount-entered");
    			Currency currency = DB.exec(db -> new CurrencyManager(db).findByNumber( td.getCurrencyCode() ));
            	if (amount != null) atmLog.setAmount( new BigDecimal(amount).divide( new BigDecimal( currency.getExponent() ) ) );
        	}
        }

        // message-class 22 is confirmation or reply
        if (msgIn.get("message-class").equalsIgnoreCase("22") )
        {
    		ATMLog atmLogLastTrnLogId = DB.exec(db -> new ATMLogManager(db).getATMLog( atm.getLastTrnLogId() ) );

		    if ( (atmLogLastTrnLogId != null) && (atmLogLastTrnLogId.getAtmReply() != null) )
	    		ctx.put ("atmLog", atmLogLastTrnLogId, remote );
        }
        else
        {
    		DB.execWithTransaction(db -> { 
    		    db.session().persist(atmLog);
    		  	return atmLog; 
    		    } );
    		ctx.put ("atmLog", atmLog, remote );
        }

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

        Instant request_dt = Instant.now();
        String tz = ZoneId.systemDefault().toString();

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

		NDCFSDMsg protocolFSDmsg = new NDCFSDMsg(protocolSchema);

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

			createATMLog(ctx, protocolFSDmsg);

			String strMAC = protocolFSDmsg.get("mac");
			if (strMAC != null)
			{
			    bmsgMAC = strMAC.getBytes(StandardCharsets.ISO_8859_1);

			    // Quitarle el MAC al msg - Inicio
	            NDCFSDMsg wrkFSDmsg = (NDCFSDMsg) protocolFSDmsg.clone();
		  		wrkFSDmsg.set("mac", null);
		  		msgWrk = wrkFSDmsg.packToBytes();
		  		// Quitarle el MAC al msg - Fin
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
			return null;
		}

        ctx.put ("fsdMsgIn", protocolFSDmsg, remote);
        ctx.put (this.request, null, remote);

  		if (bmsgMAC != null)
		{
	  		byte bKey[]  = ISOUtil.hex2byte( atm.getMacKey() );

			byte calculatedMAC[] = null; 
	  		try 
	  		{
	  			calculatedMAC = Crypto.calculateANSIX9_MAC(bKey, msgWrk);
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

			    String groups = cfg.get ("error", null);
			    return groups;
	  		}
		}

		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		protocolFSDmsg.dump(Log.out, "");

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
