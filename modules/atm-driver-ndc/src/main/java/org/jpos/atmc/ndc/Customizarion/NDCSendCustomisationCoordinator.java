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
package org.jpos.atmc.ndc.Customizarion;

import java.io.IOException;
import java.util.HashMap;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.Util;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.util.FSDMsg;

public class NDCSendCustomisationCoordinator 
{
	private ISOSource source;
	private NDCFSDMsg msgIn;
	private ATM atm;
	private String lastKey;
	private String lastKeySend; 
	private NDCCustomizarionSections customizarionSection;
	
    private static final HashMap<String, NDCSendCustomisationCoordinator> atmsCustomizarioState = new HashMap<String, NDCSendCustomisationCoordinator>();

    public static void init(ISOSource source, NDCFSDMsg msgIn, ATM atm)
    {
        NDCSendCustomisationCoordinator customizarionCoordinator = new NDCSendCustomisationCoordinator();
        customizarionCoordinator.source = source;
        customizarionCoordinator.msgIn = msgIn;
        customizarionCoordinator.atm = atm;
        customizarionCoordinator.customizarionSection = NDCCustomizarionSections.getFirst();

    	GetSection customization = GetSectionFactory.getInstance( customizarionCoordinator.customizarionSection );
    	String configId = atm.getConfigId();
        customizarionCoordinator.lastKey = customization.getLastKey(atm, configId);
        customizarionCoordinator.lastKeySend = "";
        

        BaseChannel baseChannel = (BaseChannel) source;
        atmsCustomizarioState.put( baseChannel.getName(), customizarionCoordinator );
    }

    public NDCCustomizarionSections getCurrentSection()
    {
		return this.customizarionSection;
    	
    }

    public void setCustomizarionSection(NDCCustomizarionSections customizarionSection)
    {
    	GetSection customization = GetSectionFactory.getInstance( customizarionSection );

        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " customization Name " + customization.getClass().getSimpleName());

        this.customizarionSection = customizarionSection;
    	this.lastKey = customization.getLastKey(atm, this.atm.getConfigId());

    	BaseChannel baseChannel = (BaseChannel) this.source;
        atmsCustomizarioState.put( baseChannel.getName(), this );
    }

    public static NDCSendCustomisationCoordinator get(String atmName)
    {
    	return atmsCustomizarioState.get( atmName);
    }

    /**
     * Send cmd to client (ATM)
     *
     */
    public void sendMessage(Context ctx, String msgClass, String timeVariantNumber, String data) throws IOException
    {
    	NDCFSDMsg msgOut = new NDCFSDMsg( this.msgIn.getBasePath() );

		msgOut.set("message-class",       msgClass);
		msgOut.set("luno",                this.msgIn.get("luno") );
		msgOut.set("time-variant-number", this.msgIn.get("time-variant-number") );
		
		if ( msgClass.equals("3") ) msgOut.set("data", data);
		if ( msgClass.equals("1") ) msgOut.set("command-code", data.substring(0, 1) );

        ctx.put("fsdMsgResp", msgOut);
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
    public void sendCustomizationMsg(Context ctx, String strLine) throws IOException
    {
        String strMsgClas = strLine.substring(0, 1);
		String msgLine = strLine.substring(2);
		int msgClass = Util.str2Int(strMsgClas);
        /* String strMsgSubClas; */
		
		String msgSubClas = "";
		
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
		}

		if ( ( msgSubClas.equals("41") ) || ( msgSubClas.equals("42") ) )
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " 12" );
		}
	    sendMessage(ctx, strMsgClas, null, msgLine);
    }

    public String getNextCustomizationMsg(Context ctx)
    {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " this.customizarionSection " + this.customizarionSection);
        
    	GetSection customization = GetSectionFactory.getInstance( this.customizarionSection );
    	String configId = this.atm.getConfigId();
        if (customization != null)
        {
        	// String custMsg = customization.getNextCustomizationMsg(atm, configId, this.lastKeySend);
        	String custMsg = customization.getNextCustomizationMsg(ctx, this.lastKeySend);
        	this.lastKeySend = customization.getLastKeySend();

        	if ( this.lastKey.equals(this.lastKeySend) )
        	{
        		this.customizarionSection = NDCCustomizarionSections.next(this.customizarionSection);
        		customization = GetSectionFactory.getInstance( this.customizarionSection );
        		if (customization != null)
        		{
            	    this.lastKey = customization.getLastKey(atm, configId); 
            	    this.lastKeySend = "";
        		}
        	}
        	return custMsg;
        }
        return null;
    }

    public void sendNextCustomizationMsg(Context ctx) throws IOException
    {
    	String nextCustomizationMsg = getNextCustomizationMsg(ctx);
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " nextCustomizationMsg>" + nextCustomizationMsg + "<");
    	if (nextCustomizationMsg != null)
    	{
            Util.printHexDump(Log.out, nextCustomizationMsg);
        	sendCustomizationMsg( ctx, nextCustomizationMsg );
    	}
    }
    
}
