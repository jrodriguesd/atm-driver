package org.jpos.atmc.ndc.Customizarion;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.util.Crypto;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

public class GetMACKeyChange implements GetSection 
{
	private String lastNumberSend = "000";

	@Override
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
		String Clearkey  = atm.getMasterKey();
		String EncKey    = Crypto.encypt( atm.getMacKey(), Clearkey);
		String decEncKey = Util.hex2dec(EncKey);
		String msgOut;

		StringBuilder sb = new StringBuilder();

		/***********************************************/
		/* 45 Extended Encryption Key Change (MAC Key) */
		/***********************************************/
		sb.append("3 45");

		if (decEncKey.length() == 48)
		{
		    msgOut = "030" + decEncKey;
		}
		else if (decEncKey.length() == 24)
		{
		    msgOut = "018" + decEncKey;
		}
		else
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Error de Clave" );
			return null;
		}

		sb.append(msgOut);
		
		return sb.toString();
	}

	@Override
	public String getLastKeySend() 
	{
		return this.lastNumberSend;
	}

	@Override
	public String getLastKey(ATM atm, String configId) 
	{
		return this.lastNumberSend;
	}
}
