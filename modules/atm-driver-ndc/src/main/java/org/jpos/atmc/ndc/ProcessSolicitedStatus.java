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
package org.jpos.atmc.ndc;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.dao.CassetteManager;
import org.jpos.atmc.model.Cassette;
import org.jpos.atmc.ndc.Customizarion.NDCCustomizarionSections;
import org.jpos.atmc.ndc.Customizarion.NDCSendCustomisationCoordinator;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.DB;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.FSDISOMsg;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.space.LocalSpace;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TransactionManager;
import org.jpos.util.FSDMsg;

public class ProcessSolicitedStatus implements AbortParticipant, Configurable 
{
    private String source;
    private String request;
    private String response;
    private LocalSpace isp;
    private long timeout = 70000L;
    private HeaderStrategy headerStrategy;
    private freemarker.template.Configuration fmCfg; 
    private boolean remote = false;

	// ver si los suply counters cuandran con los del atm
	// Si Cuandran Abrir el ATM
    private boolean countersMatch(ATM atm, NDCFSDMsg msgIn)
    {
		try 
		{
			List<Cassette> casssetes = DB.exec(db -> new CassetteManager(db).getByLuno(atm.getLuno() ) );
	        for (int i = 0; i < casssetes.size(); i++)
	        {
	        	Cassette cas = casssetes.get(i);
	        	
	        	String key = "notes_in_cassette-" + (i + 1);
	        	int notesIn = cas.getBegin() - cas.getDispensed() - cas.getRejected();

	        	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + 
	        			" i " + (i + 1) + 
	        			" notesIn " + notesIn +
	        			" msgIn.getInt(key) " + msgIn.getInt(key) );

	        	if ( notesIn != msgIn.getInt(key) )
	        		return false;
	        }
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
			return false;
		}
    	return true;
    }

    public void processCustomization(char statusDescriptor,  BaseChannel baseChannel, ATM atm, NDCFSDMsg msgIn) 
	{
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Continuacion Envio de Configuracion");
        NDCSendCustomisationCoordinator acc = NDCSendCustomisationCoordinator.get(baseChannel.getName());

        if ( (statusDescriptor == 'F') && (acc.getCurrentSection() == NDCCustomizarionSections.GET_SUPPLY_COUNTERS ) )
    	{
			if ( ! countersMatch(atm, msgIn) )
			{
			    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Contadores ATM != msgIn " );
			    return;
			}
    	}

        if (acc != null)
        {
			try
			{
				acc.sendNextCustomizationMsg();
			}
            catch (IOException e) 
            {
            	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + e.getMessage());
    			e.printStackTrace(Log.out);
            }
        }
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

    public void commit (long id, Serializable context) 
	{
        Context ctx = (Context) context;
	    ISOSource source = (ISOSource) ctx.get (this.source);
        BaseChannel baseChannel = (BaseChannel) source;

        NDCFSDMsg msgIn = (NDCFSDMsg) ctx.get("fsdMsgIn");
        NDCFSDMsg msgOut = new NDCFSDMsg( msgIn.getBasePath() );

        ATM atm = (ATM) ctx.get ("atm");
		
        char statusDescriptor = msgIn.get("status-descriptor").charAt(0);

		switch (statusDescriptor)
		{
			case 'A':  //* Command Reject
				Log.staticPrintln("************************************************");
				Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Command Reject - No Debria Pasar ");
				Log.staticPrintln("************************************************");
			    break;
			case 'B':  //* Ready - Transaction Reply was successfully completed
			    break;
			case 'C':  //* Specific Command Reject
			    break;
			case 'F':  //* Terminal State
				processCustomization(statusDescriptor, baseChannel, atm, msgIn);
			    break;
			case '8':  //* Device Fault
			    break;
			case '9':  //* Ready
				processCustomization(statusDescriptor, baseChannel, atm, msgIn);
			    break;
		}
	}

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException 
    {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
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
