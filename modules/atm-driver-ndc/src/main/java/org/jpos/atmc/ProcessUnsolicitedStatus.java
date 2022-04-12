package org.jpos.atmc;

import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.jpos.atmc.ATMCustomizarion.ATMSendCustomizarionCoordinator;
import org.jpos.atmc.dao.ATMManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.util.Log;
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

public class ProcessUnsolicitedStatus implements AbortParticipant, Configurable 
{
    private String source;
    private String request;
    private String response;
    private LocalSpace isp;
    private long timeout = 70000L;
    private HeaderStrategy headerStrategy;
    private freemarker.template.Configuration fmCfg; 
    private boolean remote = false;

	public static final String GO_IN_SERVICE                  = "1";
	public static final String GO_OUT_SERVICE                 = "2";
	public static final String SEND_CONFIGURATION_ID          = "3";
	public static final String SEND_SUPPLY_COUNTERS           = "4";
	public static final String SEND_TALLY_INFORMATION         = "5";
	public static final String SEND_CONFIGURATION_INFORMATION = "7";
	public static final String SEND_DATE_AND_TIME_INFORMATION = "8";

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
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        
        Context ctx = (Context) context;
	    ISOSource source = (ISOSource) ctx.get (this.source);
        ISOMsg m = (ISOMsg) ctx.get (this.request);
        ATM atm = (ATM) ctx.get ("atm");

        BaseChannel baseChannel = (BaseChannel) source;

	    FSDMsg msgIn = ((FSDISOMsg) m).getFSDMsg();
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		msgIn.dump(Log.out, "");

		String deviceIdentificationGraphic = msgIn.get("device-identification-graphic");

		switch (deviceIdentificationGraphic.charAt(0))
		{
			case 'A':  //* Time-Of-Day Clock
			    break;
			case 'B':  //* Power Failure
		        String configID = msgIn.get("device-status");
		        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " configID >" + configID + "<");
				if ( (atm != null) && ( ! atm.getConfigId().equals(configID) ) )
				{
			        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Iniciar Envio de Configuracion");
			        ATMSendCustomizarionCoordinator.init( source, msgIn, atm);
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

				}
				else
				{
		            FSDMsg msgOut = new FSDMsg( msgIn.getBasePath() );

		            msgOut.set("message-class", "1");
		            msgOut.set("command-code",  GO_IN_SERVICE);
		            msgOut.dump(Log.out, "");
		            Util.send(source, msgOut);
					// atmRunState.atmState = ATMRunState.ATMState.GOING_INTO_SERVICE;
				}
			    break;
			case 'D':  //* Card Reader/Writer Status
			    break;
			case 'E':  //* Cash Handler Status
			    break;
			case 'F':  //* Depository Status
			    break;
			case 'G':  //* Receipt Printer Status
			    break;
			case 'H':  //* Journal Printer Status
			    break;
			case 'K':  //* Night Safe Depository Status
			    break;
			case 'L':  //* Encryptor Status
			    break;
			case 'M':  //* Camera Status
			    break;
			case 'P':  //* Sensors Status
			    break;
			case 'Q':  //* Touch Screen Keyboard Status
			    break;
			case 'R':  //* Supervisor Keys Status
			    break;
			case 'S':  //* Cardholder Display Alarm Status
			    break;
			case 'V':  //* Statement Printer Status
			    break;
			case 'Y':  //* Coin Dispenser Status
			    break;
			case 'a':  //* Voice Guidance Status
			    break;
			case 'q':  //* Cheque Processing Module (CPM) Status
			    break;
			case 'f':  //* Barcode Reader Status
			    break;
			case 'w':  //* Bunch Note Acceptor Status
			    break;
			case '\\': //* Envelope Dispenser Status
			    break;
			default:
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
