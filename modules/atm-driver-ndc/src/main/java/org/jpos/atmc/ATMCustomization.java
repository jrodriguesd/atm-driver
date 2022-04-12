/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
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

import java.io.*;
import org.jpos.iso.*;
import org.jpos.util.FSDMsg;

import org.jpos.atmc.model.ATM;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

public class ATMCustomization
{
	private ISOSource source;
	private FSDMsg msgIn;
	private ATM atm;
	private String strLine = "BEGIN";
    private String strProcessingMsgSubClas = "";
	
    // Configuration send variables
    private FileInputStream fstream;
    private BufferedReader br;

	public boolean isSendingConfiguration = false;
	public boolean isPuttingATMInService  = false;

	public ATMCustomization(ISOSource source, FSDMsg msgIn, ATM atm)
	{
		this.source = source;
		this.atm = atm;
		this.msgIn = msgIn;
	}

    /**
     * Send cmd to client (ATM)
     *
     */
    public void sendMessage(int msgClass, String timeVariantNumber, String data) throws IOException
    {
		String schema = "file:cfg/ndc-";
		FSDMsg msgOut = new FSDMsg(schema);

		msgOut.set("message-class",               "" + msgClass);
		msgOut.set("luno",                        msgIn.get("luno") );
		msgOut.set("time-variant-number",         msgIn.get("time-variant-number") );
		msgOut.set("data",                        data);
		Util.send(source, msgOut);

        // byte[] cmd = cmdStr.getBytes();
		// sendMessage(msgClass, timeVariantNumber, cmd);
	}

    /**
     * Send Enhanced Configuration to the ATM
	 * LUNO only for now
     *
     */
    public void sendEnhancedConfigutation() throws IOException
    {
		String msgOut = "1A" + atm.getLuno();
	    sendMessage(3, null, msgOut);
	}

    /**
     * Send Enhanced Configuration to the ATM
	 * LUNO only for now
     *
     */
    public void sendConfigutationID() throws IOException
    {
		String msgOut = "16" + atm.getConfigId();
	    sendMessage(3, null, msgOut);
	}

    /**
     *  41 Change Master Key
     *  42 Change Communications Key
     */

    private static final String setMasterKey         = "41";
    private static final String setCommunicationsKey = "42";

    /**
     * Send Extended Encryption Key Change to Change Communications Key
	 * LUNO only for now
     *
     */
    public void sendExtendedEncryptionKeyChange(String keyType) throws IOException
    {
		String Clearkey  = "0A0F0A0F0A0F0A0F0A0F0A0F0A0F0A0F";
		String EncKey    = Crypto.encypt( atm.getMasterKey(), Clearkey);
		String decEncKey = Util.hex2dec(EncKey);
		String msgOut;
		
		if (decEncKey.length() == 48)
		{
		    msgOut = keyType + "030" + decEncKey;
		}
		else if (decEncKey.length() == 24)
		{
		    msgOut = keyType + "018" + decEncKey;
		}
		else
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Error de Clave" );
			return;
		}
			
		// String msgOut = keyType + "030012188004210205204150243012188004210205204150243";
	    sendMessage(3, null, msgOut);
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
			sendConfigutationID();
			return;
		}

		if ( msgSubClas.equals("1A") )
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " 1A" );
			sendEnhancedConfigutation();
			return;
		}

		if ( ( msgSubClas.equals("41") ) || ( msgSubClas.equals("42") ) )
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " 12" );
			sendExtendedEncryptionKeyChange(msgSubClas);
			return;
		}
	    sendMessage(msgClass, null, msgLine);
    }

    /**
     * Get Next Customization Message. Skip Blank Lines and omments
     *
     */
    public void getNextCustomizationMsg() throws IOException
    {
        StringBuilder msgBuilder = new StringBuilder();
		final int maxBufferSize = 2000;

		if ( this.strLine.equals("BEGIN") )
		    this.strLine = this.br.readLine();

        do 
        {
		    if (this.strLine != null)
		    {
				/* Blank Lines */
		        if (this.strLine.length() < 2)
				{
		            this.strLine = this.br.readLine();
		        	continue;
				}

				/* Comments */
		        if (this.strLine.charAt(0) == '*')
				{
		            this.strLine = this.br.readLine();
		        	continue;
				}

 	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + strLine );

                // Aqui Acumular los Mensajes hasta una Longitud Dada
                String strMsgClas = this.strLine.substring(0, 1);
		        int msgClass = Util.str2Int(strMsgClas);
                String strMsgSubClas = "";

		        if (msgClass == 3)
		        {
                    strMsgSubClas = this.strLine.substring(2, 4);
		        }
		        else if  (msgClass == 8)
		        {
                    strMsgSubClas = this.strLine.substring(2, 3);
		        }

	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " msgBuilder.length() " + msgBuilder.length() + " strpmsc " + this.strProcessingMsgSubClas + " strMsgSubClas " + strMsgSubClas);
				if (    (msgBuilder.length() > maxBufferSize) 
					 || ( (! this.strProcessingMsgSubClas.equals("") ) && (! strMsgSubClas.equals(strProcessingMsgSubClas) ) )
				   )
				{
	                Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " msgBuilder " + msgBuilder + " strMsgSubClas " + strMsgSubClas);
		    	    sendCustomizationMsg( msgBuilder.toString() );
					msgBuilder.setLength(0);
					this.strProcessingMsgSubClas = strMsgSubClas;
		    	    this.isSendingConfiguration = true;
	                Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
				    break;
				}

				if (msgBuilder.length() == 0)
				{
				    msgBuilder.append(this.strLine);
					this.strProcessingMsgSubClas = strMsgSubClas;
				}
				else
				    msgBuilder.append(this.strLine.substring(4));

		    }
		    else
		    {
	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		        this.strProcessingMsgSubClas = "";
                // Close the input stream
                this.fstream.close();
		        this.isSendingConfiguration = false;
                // JFRD atmPanel.enableAllButtons();
		    }
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		    this.strLine = this.br.readLine();
		
        } while (strLine != null);

	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );

        if (strLine == null)
		{
		    if (msgBuilder.length() > 0)
		    {
		        sendCustomizationMsg( msgBuilder.toString() );
		        msgBuilder.setLength(0);
		    }
		    
		    this.strProcessingMsgSubClas = "";
            this.fstream.close();
		    this.isSendingConfiguration = false;
            // JFRD  atmPanel.enableAllButtons();
		}
	}

    /**
     * Send customization records to the ATM
     *
     */
    public void initSendCustomization() throws IOException
    {
        // Open the file
 	    String confiFileName = "Configuration_" + atm.getConfigId() + ".txt"; 
 	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " confiFileName " + confiFileName );
        this.fstream = new FileInputStream(confiFileName);
        this.br = new BufferedReader(new InputStreamReader(fstream));
 	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		getNextCustomizationMsg();
 	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	}

}
