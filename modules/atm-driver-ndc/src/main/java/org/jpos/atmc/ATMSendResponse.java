package org.jpos.atmc;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.jpos.atmc.dao.IsoError2ATMManager;
import org.jpos.atmc.dao.ReceiptManager;
import org.jpos.atmc.dao.ATMConfigManager;
import org.jpos.atmc.dao.ATMManager;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.ATMConfig;
import org.jpos.atmc.model.IsoError2ATM;
import org.jpos.atmc.model.Receipt;
import org.jpos.atmc.model.TrnDefinition;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.DB;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.FSDISOMsg;
import org.jpos.iso.ISOSource;
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
public class ATMSendResponse implements AbortParticipant, Configurable
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

    public FSDMsg createFSDMsg(ISOMsg isoMsgIn, FSDMsg fsdMsgIn, String state, String screen)
	{
	    String schema = "file:cfg/ndc-";
		FSDMsg msgOut = new FSDMsg(schema);

		msgOut.set("message-class",               "4");
		msgOut.set("luno",                        fsdMsgIn.get("luno") );
		msgOut.set("time-variant-number",         fsdMsgIn.get("time-variant-number") );
		msgOut.set("next-state-number",           state);
		msgOut.set("number-notes-dispense",       "00000000");
		msgOut.set("transaction-serial-number",   isoMsgIn.getString(11).substring(2, 6));
		msgOut.set("function-identifier",         ATMTransactionRequest.NEXT_STATE_AND_PRINT);
		msgOut.set("screen-number",               screen);
		// msgOut.set("screen-display-update",       screen);
		msgOut.set("message-coordination-number", fsdMsgIn.get("message-coordination-number") );
		msgOut.set("card-return-retain-flag",     ATMTransactionRequest.RETURN_CARD);
		msgOut.set("printer-flag",                ATMTransactionRequest.RECEIPT_PRINTER_ONLY);
		
		return msgOut;
	}

    public static final int ISO_RESP_99_ERROR = 99;

    private FSDMsg createErrorFSDMsg(Context ctx, int error)
    {
        ISOMsg m = (ISOMsg) ctx.get(request);
		FSDMsg fsdMsgIn = (FSDMsg) ctx.get("fsdMsgIn");
		ATM atm  = (ATM) ctx.get("atm"); 

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

	        FSDMsg fsdMsgResp = createFSDMsg(m, fsdMsgIn, isoError2ATM.getState(), isoError2ATM.getScreenReceipt() );

	    	return fsdMsgResp;
		}
		catch (Exception ex)
		{
			Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
			Log.printStackTrace(ex);
		}

		/*
		 * Devolver el Error Fatal para Esta Configuracion
		 */
		return null;
    }

    private void sendResponse (long id, Context ctx) 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        ISOSource src = (ISOSource) ctx.get (source);
        ISOMsg m = (ISOMsg) ctx.get(request);
		FSDMsg fsdMsgIn = (FSDMsg) ctx.get("fsdMsgIn");
        ISOMsg resp = (ISOMsg) ctx.get (response);
        try {
            if (ctx.getResult().hasInhibit()) {
                ctx.log("*** RESPONSE INHIBITED ***");
            } else if (ctx.get (ContextConstants.TX.toString()) != null) {
                ctx.log("*** PANIC - TX not null - RESPONSE OMITTED ***");
            } else if (resp == null) {
	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " resp not present"  );
			    // Pantalla de error (En Este Momento Estamos Inhabilitados para completar su Transaccion por Favor Intente Mas Tarde)
				// Configuration_Orig.txt 361 361
				// Configuration_NCR_EMV.txt 050 017
	            // int ISO_RESP_99_ERROR = 99;  
	            // IsoError2ATM iso2Atm =IsoError2ATM.getIsoError2ATM(ISO_RESP_99_ERROR, atm); 
				// FSDMsg fsdMsgResp = createFSDMsg(m, fsdMsgIn, "361", "361");
				FSDMsg fsdMsgResp = createErrorFSDMsg(ctx, ISO_RESP_99_ERROR);
				fsdMsgResp.dump(Log.out, "");
		        FSDISOMsg fsdISOMsgResp = new FSDISOMsg (fsdMsgResp);

                headerStrategy.handleHeader(m, fsdISOMsgResp);
                src.send(fsdISOMsgResp);
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
                    	/*
                		try
                		{
                	        ATMConfig atmConfig = (ATMConfig) DB.exec(db -> new ATMConfigManager(db).getATMConfig( atm.getConfigID(), fsdMsgIn.get("operation-code-data") ) );
                	        String language639 = atmConfig.getLanguage639();
                	        

                	        IsoError2ATM isoError2ATM = (IsoError2ATM) DB.exec(db -> new IsoError2ATMManager(db).getIsoError2ATM(atm.getConfigID(),
                                                                                                                                 language639) );
                	        IsoError2ATM isoError2ATM = IsoError2ATM.getIsoError2ATM(ISO_RESP_99_ERROR, 
                	        		                                                atm.getConfigID(), 
                	        		                                                 language639); 
                			FSDMsg fsdMsgResp = createFSDMsg(m, fsdMsgIn, isoError2ATM.getState(), isoError2ATM.getScreen() );
                	    	return fsdMsgResp;
                		}
                		catch (Exception e)
                		{
                			Log.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + e.getMessage());
                            Log.printStackTrace(ex);
                		}
                		*/

                    	// Configuration_Orig.txt 171 171
				        // Configuration_NCR_EMV.txt 235 033
					    FSDMsg fsdMsgResp = createFSDMsg(m, fsdMsgIn, td.getState(), td.getScreen());

                        // String strBalance = resp.getString(54).substring(8, 20);
			            // BigDecimal amt = new BigDecimal( strBalance );
			            // amt = amt.divide( new BigDecimal("100") );
			            // String formatedAmt = formatCurrency(amt, "en", "US");

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

						fsdMsgResp.dump(Log.out, "");
						
		                FSDISOMsg fsdISOMsgResp = new FSDISOMsg (fsdMsgResp);
					    
                        headerStrategy.handleHeader(m, fsdISOMsgResp);
                        src.send(fsdISOMsgResp);
		            }
                    else
                    {
		            	// Pantalla de error (350 Pin Incorrecto)
				        // Configuration_Orig.txt 350 350
				        // Configuration_NCR_EMV.txt 235 033
					    // FSDMsg fsdMsgResp = createFSDMsg(m, fsdMsgIn, "350", "350");
						FSDMsg fsdMsgResp = createErrorFSDMsg(ctx, Integer.parseInt(resp.getString(39)));
						fsdMsgResp.dump(Log.out, "");
		                FSDISOMsg fsdISOMsgResp = new FSDISOMsg (fsdMsgResp);
					    
                        headerStrategy.handleHeader(m, fsdISOMsgResp);
                        src.send(fsdISOMsgResp);
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
