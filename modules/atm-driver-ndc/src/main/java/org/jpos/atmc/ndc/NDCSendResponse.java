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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

import org.jpos.atmc.dao.IsoError2ATMManager;
import org.jpos.atmc.dao.ReceiptManager;
import org.jdom2.JDOMException;
import org.jpos.atmc.ATMVariables;
import org.jpos.atmc.dao.ATMConfigManager;
import org.jpos.atmc.dao.ATMLogManager;
import org.jpos.atmc.dao.ATMManager;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.ATMConfig;
import org.jpos.atmc.model.ATMLog;
import org.jpos.atmc.model.IsoError2ATM;
import org.jpos.atmc.model.Receipt;
import org.jpos.atmc.model.TrnDefinition;
import org.jpos.atmc.util.Crypto;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.NDCISOMsg;
import org.jpos.atmc.util.Util;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.DB;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.FSDISOMsg;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.space.LocalSpace;
import org.jpos.space.SpaceSource;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.TransactionManager;

import org.jpos.transaction.participant.SendResponse;

import org.jpos.util.FSDMsg;

import freemarker.template.*;

@SuppressWarnings("unused")
public class NDCSendResponse implements AbortParticipant, Configurable
{
    private String source;
    private String request;
    private String response;
    private LocalSpace isp;
    private long timeout = 70000L;
    private HeaderStrategy headerStrategy;
    private freemarker.template.Configuration fmCfg; 

    public int prepare (long id, Serializable context) 
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
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        sendResponse(id, (Context) context);
    }

    public void abort (long id, Serializable context) 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        sendResponse(id, (Context) context);
    }

    public NDCFSDMsg createFSDMsg(ISOMsg isoMsgIn, NDCFSDMsg fsdMsgIn, String state, String screen)
	{
    	NDCFSDMsg msgOut = new NDCFSDMsg( fsdMsgIn.getBasePath() );

		msgOut.set("message-class",               "4");
		msgOut.set("luno",                        fsdMsgIn.get("luno") );
		msgOut.set("time-variant-number",         fsdMsgIn.get("time-variant-number") );
		msgOut.set("next-state-number",           state);
		msgOut.set("number-notes-dispense",       "00000000");

		if (isoMsgIn.getString(11) != null)
		    msgOut.set("transaction-serial-number",   isoMsgIn.getString(11).substring(2, 6));

		msgOut.set("function-identifier",         NDCConstants.NEXT_STATE_AND_PRINT);
		msgOut.set("screen-number",               screen);
		// msgOut.set("screen-display-update",       screen);
		msgOut.set("message-coordination-number", fsdMsgIn.get("message-coordination-number") );
		msgOut.set("card-return-retain-flag",     NDCConstants.RETURN_CARD);
		msgOut.set("printer-flag",                NDCConstants.RECEIPT_PRINTER_ONLY);
		
		return msgOut;
	}

    public static final int ISO_RESP_99_ERROR = 99;

    private NDCFSDMsg createErrorFSDMsg(Context ctx, int error)
    {
        ISOMsg m = (ISOMsg) ctx.get(request);
        NDCFSDMsg fsdMsgIn = (NDCFSDMsg) ctx.get("fsdMsgIn");

		ATM atm  = (ATM) ctx.get("atm"); 

		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		fsdMsgIn.dump(Log.out, "");

		try
		{
	        ATMConfig atmConfig = (ATMConfig) DB.exec(db -> new ATMConfigManager(db).getATMConfig( atm.getConfigId(), fsdMsgIn.get("operation-code-data") ) );
	        String language639 = atmConfig.getLanguage639();
	        IsoError2ATM isoError2ATM = (IsoError2ATM) DB.exec(db -> new IsoError2ATMManager(db).getIsoError2ATM(error, 
                                                                                                                 atm.getConfigId(),
                                                                                                                 language639) );
            if (isoError2ATM == null)
    	        isoError2ATM = (IsoError2ATM) DB.exec(db -> new IsoError2ATMManager(db).getIsoError2ATM(ISO_RESP_99_ERROR, 
                                                                                                        atm.getConfigId(),
                                                                                                        language639) );

            NDCFSDMsg fsdMsgResp = createFSDMsg(m, fsdMsgIn, isoError2ATM.getState(), isoError2ATM.getScreenReceipt() );

	    	return fsdMsgResp;
		}
		catch (Exception e)
		{
			Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + e.getMessage());
			e.printStackTrace(Log.out);
		}

		/*
		 * Devolver el Error Fatal para Esta Configuracion
		 */
		return null;
    }

    private void send(Context ctx, NDCFSDMsg fsdMsgResp) throws IOException, ISOException, JDOMException, GeneralSecurityException
    {
        ISOSource src = (ISOSource) ctx.get (source);
        ISOMsg m = (ISOMsg) ctx.get(request);
        NDCFSDMsg fsdMsgIn = (NDCFSDMsg) ctx.get("fsdMsgIn");

        /*
         * Queda Pendiente Sacr la Key de Algun Lado 
         */
        if (fsdMsgIn.get("mac") != null)
        {
          	String key = "0123456789ABCDEF";
      		byte[] bKey  = ISOUtil.hex2byte(key);
           
      		fsdMsgResp.set("mac", null);
      		byte bReply[] = fsdMsgResp.packToBytes();

            Util.printHexDump(Log.out, bReply);
      		
      		byte calculatedMAC[] = Crypto.calculateANSIX9_9MAC(bKey, bReply);

     		String strMAC = Util.byteDecode(calculatedMAC);

            fsdMsgResp.set("mac", strMAC);
        }

    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        fsdMsgResp.dump(Log.out, "");
        ctx.put("fsdMsgResp", fsdMsgResp);
        NDCISOMsg fsdISOMsgResp = new NDCISOMsg ( (NDCFSDMsg) fsdMsgResp );

        headerStrategy.handleHeader(m, fsdISOMsgResp);

        src.send(fsdISOMsgResp);
    }

    private void updateLog(Context ctx) 
    {
        ATMLog atmLog =  ctx.get ("atmLog");
        if (atmLog == null) return;
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + atmLog.toString() );
		try {
			boolean needUpdate = false;
			ISOMsg req = (ISOMsg) ctx.get(request);
			if (req != null) {
				atmLog.setIsoRequest(Util.dum2Str(req));
				atmLog.setIsoRequestDt(Instant.now());
				needUpdate = true;
			}

			ISOMsg resp = (ISOMsg) ctx.get(response);
			if (resp != null) {
				atmLog.setIsoReply(Util.dum2Str(resp));
				atmLog.setIsoReplyDt(Instant.now());
				needUpdate = true;
			}

			if (needUpdate) {
				DB.execWithTransaction(db -> {
					db.session().update(atmLog);
					return 1;
				});
			}

		} catch (Exception e) {
			e.printStackTrace(Log.out);
		}
    }

    private void sendResponse (long id, Context ctx) 
	{
    	
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        ISOSource src = (ISOSource) ctx.get (source);
        ISOMsg m = (ISOMsg) ctx.get(request);
        NDCFSDMsg fsdMsgIn = (NDCFSDMsg) ctx.get("fsdMsgIn");
        ISOMsg resp = (ISOMsg) ctx.get (response);

        updateLog(ctx);

        try {
            if (ctx.getResult().hasInhibit()) {
                ctx.log("*** RESPONSE INHIBITED ***");
            } else if (ctx.get (ContextConstants.TX.toString()) != null) {
                ctx.log("*** PANIC - TX not null - RESPONSE OMITTED ***");
            } else if (resp == null) {
	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " resp not present"  );

	            NDCFSDMsg fsdMsgResp = createErrorFSDMsg(ctx, ISO_RESP_99_ERROR);
				/*
				fsdMsgResp.dump(Log.out, "");
		        FSDISOMsg fsdISOMsgResp = new FSDISOMsg (fsdMsgResp);

                headerStrategy.handleHeader(m, fsdISOMsgResp);
                src.send(fsdISOMsgResp);
                */
				send(ctx, fsdMsgResp );
                ctx.log (response + " not present");
            } else if (src == null) {
                ctx.log (source + " not present");
            } else if (!src.isConnected())
                ctx.log (source + " is no longer connected");
            else {
                if (src instanceof SpaceSource)
                    ((SpaceSource)src).init(isp, timeout);
                if (src.isConnected() && resp != null) 
				{
	                Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );

                    if ( resp.getString(39).equals("00") )
		            {
                		ATM atm  = (ATM) ctx.get("atm"); 
                		TrnDefinition td = (TrnDefinition) ctx.get ("transactiondefinition");
                		Receipt rcp = DB.exec(db -> new ReceiptManager(db).getReceipt(atm.getConfigId(), td.getReceiptCode() ) );

                		NDCFSDMsg fsdMsgResp = createFSDMsg(m, fsdMsgIn, td.getState(), td.getScreen());

			    	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " td.getScreenUpdate() " + td.getScreenUpdate() );

			    	    ATMVariables vars = new ATMVariables(td, resp, fsdMsgIn, atm);
		        	    Map<String, Object> rootObject = new HashMap<>();
		        	    rootObject.put("vars", vars);

			    	    if (td.getScreenUpdate().length() > 0)
			    	    {
			        		String screenDataTemplate = td.getRespMaskScr();
			        		Template scrTemplate = new Template("Screen",
			                         new StringReader(screenDataTemplate), 
			                         fmCfg ); 
			        	    StringWriter stringWriter = new StringWriter();
			        	    scrTemplate.process(rootObject, stringWriter);
			        	    String screenData = td.getScreenUpdate() + stringWriter.toString();

			                fsdMsgResp.set("screen-display-update",  screenData);
			    	    }

			            String pattern = "dd-MM-yyyy H:m:s";
		                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		                String date = simpleDateFormat.format(new Date());

		        		String printerDataTemplate = rcp.getTemplate();

		        		Template rcpTemplate = new Template("Receipt",
		        				                            new StringReader(printerDataTemplate), 
		        				                            fmCfg ); 

		        	    StringWriter stringWriter = new StringWriter();
		        	    rcpTemplate.process(rootObject, stringWriter);

		        	    String printerData = stringWriter.toString();

		                fsdMsgResp.set("printer-data", printerData);

		                /*
						fsdMsgResp.dump(Log.out, "");
						
		                FSDISOMsg fsdISOMsgResp = new FSDISOMsg (fsdMsgResp);
					    
                        headerStrategy.handleHeader(m, fsdISOMsgResp);
                        src.send(fsdISOMsgResp);
                        */
		                send(ctx, fsdMsgResp);
		            }
                    else
                    {
                    	NDCFSDMsg fsdMsgResp = createErrorFSDMsg(ctx, Integer.parseInt(resp.getString(39)));
						/*
						fsdMsgResp.dump(Log.out, "");
		                FSDISOMsg fsdISOMsgResp = new FSDISOMsg (fsdMsgResp);
					    
                        headerStrategy.handleHeader(m, fsdISOMsgResp);
                        src.send(fsdISOMsgResp);
                        */
						send(ctx, fsdMsgResp);
                    }
                }
            }
        } catch (Throwable t) {
            ctx.log(t);
        }
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
