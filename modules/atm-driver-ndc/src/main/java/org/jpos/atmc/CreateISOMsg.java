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

import java.io.Serializable;
import java.util.Date;

import org.jpos.atmc.dao.TrnDefinitionManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.TrnDefinition;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.Sequencer;
import org.jpos.core.VolatileSequencer;
import org.jpos.ee.DB;
import org.jpos.iso.FSDISOMsg;
import org.jpos.iso.ISODate;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.space.LocalSpace;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TransactionManager;
import org.jpos.util.FSDMsg;

public class CreateISOMsg  implements AbortParticipant, Configurable 
{
    private String source;
    private String request;
    private String response;
    private LocalSpace isp;
    private long timeout = 70000L;
    private Configuration cfg; 
    private HeaderStrategy headerStrategy;
    private freemarker.template.Configuration fmCfg; 
    private boolean remote = false;

    private Sequencer seq = new VolatileSequencer();

    /**
     * replace some characters in a PIN received from ATM
     *
     */
    public static String normalizePIN(String PIN)
	{
		StringBuilder sb = new StringBuilder(PIN);
		for (int i = 0; i < sb.length(); i++)
		{
			if (sb.charAt(i) == ':') sb.setCharAt(i, 'A');

			if (sb.charAt(i) == ';') sb.setCharAt(i, 'B');

			if (sb.charAt(i) == '<') sb.setCharAt(i, 'C');

			if (sb.charAt(i) == '=') sb.setCharAt(i, 'D');

			if (sb.charAt(i) == '>') sb.setCharAt(i, 'E');

			if (sb.charAt(i) == '?') sb.setCharAt(i, 'F');
		}
		return sb.toString();
	}

	public int prepareForAbort(long id, Serializable context) 
	{
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        return ABORTED | READONLY | NO_JOIN;
	}

	@Override
	public int prepare(long id, Serializable context) 
	{
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        Context ctx = (Context) context;
        ISOSource source = (ISOSource) ctx.get (this.source);

        NDCFSDMsg msgIn = (NDCFSDMsg) ctx.get("fsdMsgIn");

        if (source == null || !source.isConnected())
            return ABORTED | READONLY | NO_JOIN;

	    ATM atm = (ATM) ctx.get("atm");

        ISOMsg isoReqMsg = new ISOMsg();
        String normalizedPIN = normalizePIN( msgIn.get("buffer-A-pin") );
        TrnDefinition td = null;

        try
		{
            td = ctx.get("transactiondefinition");  

            isoReqMsg.setMTI(td.getMTI());
            isoReqMsg.set(  3, td.getProcesingCode());
		    isoReqMsg.set(  7, ISODate.getANSIDate(new Date()));
		    isoReqMsg.set( new ISOField(11, ISOUtil.zeropad(Integer.valueOf(seq.get("traceno", 1)).toString(), 6)));
            isoReqMsg.set( 12, ISODate.getTime (new Date()));
		    isoReqMsg.set( 13, ISODate.getExpirationDate(new Date()));
		    isoReqMsg.set( 15, ISODate.getExpirationDate(new Date()));
		    isoReqMsg.set( 17, ISODate.getExpirationDate(new Date()));
			isoReqMsg.set( 18, atm.getMerchType());        //  "6011");                                    // Merchant Type (MCC)                        atm_merchant_type
			isoReqMsg.set( 22, atm.getPosEntryMode());     //  "051");                                     // POS Entry Mode                             atm_pos_entry_mode
		    isoReqMsg.set( 32, atm.getInstitutionCode());  // "1111");                                     // Institution Code                           atm_institution_code
            isoReqMsg.set( 35, msgIn.get("track2") );
            isoReqMsg.set( 41, atm.getTerminalId());       // "29110001");                                 // Card Acceptor Terminal Identification Code atm_terminal_id
            isoReqMsg.set( 42, atm.getAceptorId());        // "1234567");                                  // Card Acceptor Identification Code
			isoReqMsg.set( 43, atm.getAddress());                                                          // ATM Address
            isoReqMsg.set( 49, td.getCurrencyCode());      // "937");                                      // Currency Code                              atm_currency 
            isoReqMsg.set( 52, normalizedPIN );
            isoReqMsg.set( 61, atm.getPointServData());    // "91000000025008620000000000" );              // Point-of-Service Data                      atm_point_serv_data 
            isoReqMsg.set( 63, atm.getNetworkData());      // "CI2000000000" );                            // Network Data                               atm_network_data
		}
		catch (Exception e)
		{
			Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + e.getMessage());
			e.printStackTrace(Log.out);
	        if (td == null)
	            return ABORTED | READONLY | NO_JOIN;
		}

        ctx.put(request, isoReqMsg, remote); // Para Poder Usar QueryHost

        return PREPARED | READONLY;
	}

    public void commit (long id, Serializable context) 
	{
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
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

    public void setTransactionManager(TransactionManager tm) {
        isp = (LocalSpace) tm.getInputSpace();
    }

    private interface HeaderHandler {
        void handleHeader (ISOMsg m, ISOMsg r);
    }

    @SuppressWarnings("unused")
    public enum HeaderStrategy implements HeaderHandler {
        PRESERVE_ORIGINAL() {
            @Override
            public void handleHeader(ISOMsg m, ISOMsg r) {
                r.setHeader(m.getHeader());
            }
        },
        PRESERVE_RESPONSE() {
            @Override
            public void handleHeader(ISOMsg m, ISOMsg r) { }
        },
        SET_TO_NULL() {
            @Override
            public void handleHeader(ISOMsg m, ISOMsg r) {
                r.setHeader((byte[]) null);
            }
        }
    }    
}
