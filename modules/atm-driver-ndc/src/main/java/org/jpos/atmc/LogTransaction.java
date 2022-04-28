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
import java.time.Instant;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.ATMLog;
import org.jpos.atmc.dao.ATMLogManager;
import org.jpos.atmc.dao.ATMManager;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.DB;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.space.LocalSpace;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TransactionManager;

public class LogTransaction implements AbortParticipant, Configurable 
{
    private String source;
    private String request;
    private String response;
    private LocalSpace isp;
    private long timeout = 70000L;
    private HeaderStrategy headerStrategy;
    private freemarker.template.Configuration fmCfg; 

    private void updateLog(Context ctx) 
    {
        ATMLog atml =  ctx.get ("atmLog");
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + atml.toString() + " " + atml.toString()  );
    	try 
    	{
    		ATMLog atmLog = DB.exec(db -> new ATMLogManager(db).getATMLog( atml.getId() ) );

		    if (atmLog != null)
		    {
		    	// Update ATMLog
		    	NDCFSDMsg fsdMsgResp = ctx.get("fsdMsgResp");
		    	if (fsdMsgResp != null)
		    	{
		    		String fsdMsgRespDump = Util.dum2Str(fsdMsgResp);
			        atmLog.setAtmReply( fsdMsgRespDump );
			        atmLog.setAtmReplyDt( Instant.now() );

				    DB.execWithTransaction(db -> { 
	                    db.session().update(atmLog);
				    	return 1; 
				    } );
		    	}

		    	// Update ATM Last Transaction Log Id
		        ATM atm =  ctx.get ("atm");
				ATM atmReaded = DB.exec(db -> new ATMManager(db).findByIP( atm.getIp() ));
				if (atmReaded != null)
				{
					atmReaded.setLastTrnLogId( atmLog.getId() );
				    DB.execWithTransaction(db -> { 
	                    db.session().update(atmReaded);
				    	return 1; 
				    } );
				}

		    }
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace(Log.out);
		}
    }

	@Override
    public int prepare (long id, Serializable context) 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        Context ctx = (Context) context;
        ISOSource source = (ISOSource) ctx.get (this.source);

        if (source == null || !source.isConnected())
            return ABORTED | READONLY | NO_JOIN;

        return PREPARED | READONLY;
    }

	public void abort(long id, Serializable context) 
	{
        Context ctx = (Context) context;
        updateLog(ctx);
	}

	public void commit (long id, Serializable context) 
	{
        Context ctx = (Context) context;
        updateLog(ctx);
	}

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        source   = cfg.get ("source",   ContextConstants.SOURCE.toString());
        request =  cfg.get ("request",  ContextConstants.REQUEST.toString());
        response = cfg.get ("response", ContextConstants.RESPONSE.toString());
        timeout  = cfg.getLong ("timeout", timeout);
        fmCfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
        try {
            headerStrategy = HeaderStrategy.valueOf(
              cfg.get("header-strategy", "PRESERVE_RESPONSE").toUpperCase()
            );
        } catch (IllegalArgumentException e) {
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
