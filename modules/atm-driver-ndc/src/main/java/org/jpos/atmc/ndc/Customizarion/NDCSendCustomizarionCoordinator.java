package org.jpos.atmc.ndc.Customizarion;

import java.io.IOException;
import java.util.HashMap;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOSource;
import org.jpos.util.FSDMsg;

public class NDCSendCustomizarionCoordinator 
{
	private ISOSource source;
	private FSDMsg msgIn;
	private ATM atm;
	private String lastKey;
	private String lastKeySend; 
	private NDCCustomizarionSections customizarionSection;
	
    private static final HashMap<String, NDCSendCustomizarionCoordinator> atmsCustomizarioState = new HashMap<String, NDCSendCustomizarionCoordinator>();

    public static void init(ISOSource source, FSDMsg msgIn, ATM atm)
    {
        // ATMCustomizarionState customizarionState = new ATMCustomizarionState();

        NDCSendCustomizarionCoordinator customizarionCoordinator = new NDCSendCustomizarionCoordinator();
        customizarionCoordinator.source = source;
        customizarionCoordinator.msgIn = msgIn;
        customizarionCoordinator.atm = atm;
        customizarionCoordinator.customizarionSection = NDCCustomizarionSections.SCREENS;

    	NDCSendCustomization customization = NDCSendCustomizationFactory.getInstance( customizarionCoordinator.customizarionSection );
    	String configId = atm.getConfigId();
        customizarionCoordinator.lastKey = customization.getLastKey(configId);
        customizarionCoordinator.lastKeySend = "";
        

        BaseChannel baseChannel = (BaseChannel) source;
        atmsCustomizarioState.put( baseChannel.getName(), customizarionCoordinator );
    }

    public static NDCSendCustomizarionCoordinator get(String atmName)
    {
    	return atmsCustomizarioState.get( atmName);
    }

    /**
     * Send cmd to client (ATM)
     *
     */
    public void sendMessage(int msgClass, String timeVariantNumber, String data) throws IOException
    {
        FSDMsg msgOut = new FSDMsg( this.msgIn.getBasePath() );

		msgOut.set("message-class",       "" + msgClass);
		msgOut.set("luno",                this.msgIn.get("luno") );
		msgOut.set("time-variant-number", this.msgIn.get("time-variant-number") );
		msgOut.set("data",                data);
		Util.send(source, msgOut);
	}

    /**
     * Send a Configuration to the ATM
	 *
	 * 1A Enhanced Configutation                                  449 10-15
	 * 1C Date and Time                                           464 10-30
	 * 41 Extended Encryption Key Change (Master Key)             468 10-34
	 * 42 Extended Encryption Key Change (Communications Key)     468 10-34
     *
     */
    public void sendCustomizationMsg(String strLine) throws IOException
    {
        String strMsgClas = strLine.substring(0, 1);
		int msgClass = Util.str2Int(strMsgClas);
        /* String strMsgSubClas; */
		
		String msgSubClas = "";
		String msgLine = strLine.substring(2);
		
		if (msgClass == 3)
		{
            msgSubClas = strLine.substring(2, 4);
		}
		else if  (msgClass == 8)
		{
            msgSubClas = strLine.substring(2, 3);
		}

		if ( msgSubClas.equals("16") )
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " 16" );
		}

		if ( msgSubClas.equals("1A") )
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " 1A" );
			// sendEnhancedConfigutation();
			return;
		}

		if ( ( msgSubClas.equals("41") ) || ( msgSubClas.equals("42") ) )
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " 12" );
		}
	    sendMessage(msgClass, null, msgLine);
    }

    public String getNextCustomizationMsg()
    {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " this.customizarionSection " + this.customizarionSection);
        
    	NDCSendCustomization customization = NDCSendCustomizationFactory.getInstance( this.customizarionSection );
    	String configId = this.atm.getConfigId();
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " configId >" + configId + "<");
    	String custMsg = customization.getNextCustomizationMsg(atm, configId, this.lastKeySend);
    	this.lastKeySend = customization.getLastKeySend();

    	if ( this.lastKey.equals(this.lastKeySend) )
    	{
    		this.customizarionSection = NDCCustomizarionSections.next(this.customizarionSection);
    		customization = NDCSendCustomizationFactory.getInstance( this.customizarionSection );
    		if (customization != null)
    		{
        		this.lastKey = customization.getLastKey(configId); 
        		this.lastKeySend = "";
    		}
    	}
    	return custMsg;
    }

    public void sendNextCustomizationMsg() throws IOException
    {
    	String nextCustomizationMsg = getNextCustomizationMsg(); 
        Log.staticPrintln( Util.formatHexDump(nextCustomizationMsg) );
    	sendCustomizationMsg( nextCustomizationMsg );
    }
    
}
