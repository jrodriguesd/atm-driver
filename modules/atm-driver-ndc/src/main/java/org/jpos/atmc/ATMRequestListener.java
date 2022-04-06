package org.jpos.atmc;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.Sequencer;
import org.jpos.core.VolatileSequencer;

import org.jpos.iso.*;

import org.jpos.util.FSDMsg;
import org.jpos.iso.packager.ISO87APackager;

import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;

import org.jpos.space.LocalSpace;
import org.jpos.space.SpaceSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;

import org.jpos.ee.DB;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.TrnDefinition;

import org.jpos.atmc.dao.ATMManager;
import org.jpos.atmc.dao.TrnDefinitionManager;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

public class ATMRequestListener extends org.jpos.util.Log implements ISORequestListener, Configurable
{
    long timeout;

    private final String name = "ATMRequestListener"; 
    private Space<String,Context> sp;
    private String queue;
    private String source;
    private String request;
    private String timestamp;
    private Map<String,String> additionalContextEntries = null;
    private boolean remote = false;

    private HashMap<String, ATMRunState> atms = new HashMap<String, ATMRunState>();
    private Sequencer seq = new VolatileSequencer();

	public static final String GO_IN_SERVICE                  = "1";
	public static final String GO_OUT_SERVICE                 = "2";
	public static final String SEND_CONFIGURATION_ID          = "3";
	public static final String SEND_SUPPLY_COUNTERS           = "4";
	public static final String SEND_TALLY_INFORMATION         = "5";
	public static final String SEND_CONFIGURATION_INFORMATION = "7";
	public static final String SEND_DATE_AND_TIME_INFORMATION = "8";

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
			    debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " IPv4: " + inetAddress.toString() );
				
			}
            else if (inetAddress instanceof Inet6Address)
			{
			    retStr = inetAddress.toString();
			    debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " IPv6: " + inetAddress.toString() );
			}
            else
			{
            	debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Not an IP address" );
			}
        } 
		else 
		{
			debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Not an internet protocol socket" );
		}
		return retStr.substring(1);
	}

    /**
     * replace some caracters in a PIN received from ATM
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

    /**
     * Process msg consumer request (11) received from ATM
     *
     */
    public void processTransactionRequest(ISOSource src, FSDMsg fsdMsgIn) throws Exception
	{
		BaseChannel baseChannel = (BaseChannel) src;
		Socket socket = baseChannel.getSocket();
		String ip = getIPAddr(socket);

		ATM atm = DB.exec(db -> new ATMManager(db).findByIP(ip));
		debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " atm " + atm.toString() );

        // Create ISO Message
        ISOMsg m = new ISOMsg();
        m.setPackager( new ISO87APackager() );
        String normalizedPIN = normalizePIN( fsdMsgIn.get("buffer-A-pin") );

        TrnDefinition td = DB.exec(db -> new TrnDefinitionManager(db).getTrnDefinition(atm.getConfigId(),
                                                                                       fsdMsgIn.get("operation-code-data")));

        try
		{
            m.setMTI(td.getMTI());
            m.set(  3, td.getProcesingCode());
		    m.set(  7, ISODate.getANSIDate(new Date()));
		    m.set( new ISOField(11, ISOUtil.zeropad(Integer.valueOf(seq.get("traceno", 1)).toString(), 6)));
            m.set( 12, ISODate.getTime (new Date()));
		    m.set( 13, ISODate.getExpirationDate(new Date()));
		    m.set( 15, ISODate.getExpirationDate(new Date()));
		    m.set( 17, ISODate.getExpirationDate(new Date()));
			m.set( 18, atm.getMerchType());        //  "6011");                                     // Merchant Type (MCC)                        atm_merchant_type
			m.set( 22, atm.getPosEntryMode());     //  "051");                                      // POS Entry Mode                             atm_pos_entry_mode
		    m.set( 32, atm.getInstitutionCode());  // "1111");                                     // Institution Code                           atm_institution_code
            m.set( 35, fsdMsgIn.get("track2") );
            m.set( 41, atm.getTerminalId());       // "29110001");                                 // Card Acceptor Terminal Identification Code atm_terminal_id
            m.set( 42, atm.getAceptorId());        // "1234567");                                  // Card Acceptor Identification Code
			m.set( 43, "MAIN BRANH             QUITO     ECUADOR"); // ATM Address
			debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " atm.getAddress() >" + atm.getAddress() + "<");
            m.set( 49, atm.getCurrencyCode());     // "937");                                      // Currency Code                              atm_currency 
            m.set( 52, normalizedPIN );
            m.set( 61, atm.getPointServData());    // "91000000025008620000000000" );              // Point-of-Service Data                      atm_point_serv_data 
            m.set( 63, atm.getNetworkData());      // "CI2000000000" );                            // Network Data                               atm_network_data
		}
		catch (ISOException ex)
		{
			debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
			Log.printStackTrace(ex);
		}

        //**********************************************************************		
        final Context ctx  = new Context ();
        if (remote)
            src = new SpaceSource((LocalSpace<String, Context>)sp, src, timeout);
        ctx.put (timestamp, new Date(), remote);
        ctx.put (source, src, remote);
        ctx.put (request, m, remote);
		ctx.put ("fsdMsgIn", fsdMsgIn, remote );
		ctx.put ("atm", atm, remote );
		ctx.put ("transactiondefinition", td, remote );
        if (additionalContextEntries != null) 
		{
            additionalContextEntries.entrySet().forEach
			(
                e -> ctx.put(e.getKey(), e.getValue(), remote)
            );
        }
        sp.out(queue, ctx, timeout);

        //**********************************************************************		
	}

    /**
     * Process unsolicited msg (12) received from ATM
     *
     */
    public void processUnsolicitedStatus(BaseChannel source, FSDMsg msgIn) throws Exception
	{
		BaseChannel baseChannel = (BaseChannel) source;
		Socket socket = baseChannel.getSocket();
		String ip = getIPAddr(socket);

		debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		msgIn.dump(Log.out, "");

		// ATM atm = ATMDao.getATM(ip);
		ATM atm = DB.exec(db -> new ATMManager(db).findByIP(ip));
		/* String luno = msgIn.get("luno"); */
		String deviceIdentificationGraphic = msgIn.get("device-identification-graphic");
		ATMRunState atmRunState = (ATMRunState) atms.get( source.getName() );
		
		switch (deviceIdentificationGraphic.charAt(0))
		{
			case 'A':  //* Time-Of-Day Clock
			    break;
			case 'B':  //* Power Failure
		        String configID = msgIn.get("device-status");
		        debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " configID >" + configID + "<");
				if ( (atm != null) && ( ! atm.getConfigId().equals(configID) ) )
				{
                    atmRunState.atmCustomization = new ATMCustomization(source, msgIn, atm);
					try
					{
					    atmRunState.atmCustomization.initSendCustomization();
					    debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " ars.atmCustomization.isSendingConfiguration " + atmRunState.atmCustomization.isSendingConfiguration);
						
					}
		            catch (IOException ex) 
		            {
		            	debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
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
					atmRunState.atmState = ATMRunState.ATMState.GOING_INTO_SERVICE;
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
		// De Donde Saco La IP ???
	}

    /**
     * Process Solicited msg (22) received from ATM
     *
     */
    public void processSolicitedStatus(BaseChannel source, FSDMsg msgIn)
	{
		char statusDescriptor = msgIn.get("status-descriptor").charAt(0);

		ATMRunState atmRunState = (ATMRunState) atms.get( source.getName() );
		
		switch (statusDescriptor)
		{
			case 'A':  //* Command Reject
				debug("************************************************");
				debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Command Reject - No Debria Pasar ");
				debug("************************************************");
			    break;
			case 'B':  //* Ready - Transaction Reply was successfully completed
			    break;
			case 'C':  //* Specific Command Reject
			    break;
			case 'F':  //* Terminal State
			    if ( atmRunState.atmState == ATMRunState.ATMState.SENDING_CONFIGURATION_INFORMATION )
				{
			    	debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Llego la CONFIGURATION_INFORMATION ");
                    atmRunState.atmState = ATMRunState.ATMState.IN_SERVICE;
				}
			    break;
			case '8':  //* Device Fault
			    break;
			case '9':  //* Ready
			    if ( (atmRunState.atmCustomization != null) && ( atmRunState.atmCustomization.isSendingConfiguration ) )
				{
					try
					{
			            atmRunState.atmCustomization.getNextCustomizationMsg();
						atms.put( source.getName(), atmRunState );
					}
		            catch (IOException ex) 
		            {
		            	debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
		            	Log.printStackTrace(ex);
                    }
				}
			    if ( atmRunState.atmState == ATMRunState.ATMState.GOING_INTO_SERVICE )
				{
                    atmRunState.atmState = ATMRunState.ATMState.IN_SERVICE;

		            FSDMsg msgOut = new FSDMsg( msgIn.getBasePath() );
		            
		            msgOut.set("message-class", "1");
		            msgOut.set("command-code",  SEND_CONFIGURATION_INFORMATION);
		            msgOut.dump(Log.out, "");
		            Util.send(source, msgOut);
					atmRunState.atmState = ATMRunState.ATMState.SENDING_CONFIGURATION_INFORMATION;
				}
			    break;
		}
	}

    public boolean process(ISOSource source, ISOMsg m)
	{
		try
		{
			debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " ID " + this );
		    BaseChannel bc = (BaseChannel) source;
		    Socket socket = bc.getSocket();
		    String ip = getIPAddr(socket);
		    debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " bc.getName() " + bc.getName() );
		    debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " bc.getTimeout () " + bc.getTimeout () );
		    
		    debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " ip " + ip );
		    
		    FSDMsg msgIn    = ((FSDISOMsg) m).getFSDMsg();
		    int    msgClass = msgIn.getInt("message-class");
		    
            ATMRunState ars = (ATMRunState) atms.get( bc.getName() );
	        if ( ars == null)
		    {
		    	ars = new ATMRunState();
                atms.put(bc.getName(), ars);
		    }	
		    
	        debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " msgClass " + msgClass);
            msgIn.dump(Log.out, " msgIn ");
		    
		    switch (msgClass)
		    {
		    	case 11 :
		    	    processTransactionRequest(bc, msgIn);
		    	    break;
		    	case 12 :
		    	    processUnsolicitedStatus(bc, msgIn);
		    	    break;
		    	case 22 :
		    	    processSolicitedStatus(bc, msgIn);
		    	    break;
		    	default :
		    	    break;
		    }
		}
		catch (Exception ex) 
		{
			debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
            Log.printStackTrace(ex);
        }
		
        return true;
    }

    public void setConfiguration (Configuration cfg) 
        throws ConfigurationException
    {
    	debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        timeout  = cfg.getLong ("timeout", 15000L);
        sp = (Space<String,Context>) SpaceFactory.getSpace (cfg.get ("space"));
        queue = cfg.get ("queue", null);
        if (queue == null)
            throw new ConfigurationException ("queue property not specified");
        source = cfg.get ("source", ContextConstants.SOURCE.toString());
        request = cfg.get ("request", ContextConstants.REQUEST.toString());
        timestamp = cfg.get ("timestamp", ContextConstants.TIMESTAMP.toString());
        remote = cfg.getBoolean("remote") || cfg.get("space").startsWith("rspace:");
        Map<String,String> m = new HashMap<>();
        cfg.keySet()
           .stream()
           .filter (s -> s.startsWith("ctx."))
           .forEach(s -> m.put(s.substring(4), cfg.get(s)));
        if (m.size() > 0)
            additionalContextEntries = m;
    }

    public String getName() 
	{ 
        return this.name; 
    }  	
}
