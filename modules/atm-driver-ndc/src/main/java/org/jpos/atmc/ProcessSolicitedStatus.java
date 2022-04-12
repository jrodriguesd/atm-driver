package org.jpos.atmc;

import java.io.IOException;
import java.io.Serializable;

import org.jpos.atmc.ATMCustomizarion.ATMSendCustomizarionCoordinator;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
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

        ISOMsg m = (ISOMsg) ctx.get (this.request);
        FSDMsg msgIn    = ((FSDISOMsg) m).getFSDMsg();

        char statusDescriptor = msgIn.get("status-descriptor").charAt(0);

		// ATMRunState atmRunState = (ATMRunState) atms.get( source.getName() );
		
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
			    // if ( atmRunState.atmState == ATMRunState.ATMState.SENDING_CONFIGURATION_INFORMATION )
				// {
			    // 	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Llego la CONFIGURATION_INFORMATION ");
                //     atmRunState.atmState = ATMRunState.ATMState.IN_SERVICE;
				// }
			    break;
			case '8':  //* Device Fault
			    break;
			case '9':  //* Ready
		        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Continuacion Envio de Configuracion");
		        BaseChannel baseChannel = (BaseChannel) source;
		        ATMSendCustomizarionCoordinator acc = ATMSendCustomizarionCoordinator.get(baseChannel.getName());

				try
				{
					acc.sendNextCustomizationMsg();
				}
	            catch (IOException ex) 
	            {
	            	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
	                Log.printStackTrace(ex);
                }
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
