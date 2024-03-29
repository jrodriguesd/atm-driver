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

import org.jpos.atmc.hsm.HsmFactory;
import org.jpos.atmc.hsm.HsmThales;
import org.jpos.atmc.hsm.HsmType;
import org.jpos.atmc.hsm.KeyType;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.util.Crypto;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.transaction.Context;

public class GetPinKeyChange implements GetSection 
{
	private String lastNumberSend = "000";

	@Override
	public String getNextCustomizationMsg(Context ctx, String lastNumber) 
	{
        ATM atm = (ATM) ctx.get ("atm");
		// String Clearkey  = atm.getMasterKey();
		// String EncKey    = Crypto.encypt( atm.getPinKey(), Clearkey);
		String newPinKey = "U23F6C66EF9134D69638EC04F87CD2C9A";
		String msgOut;

		String generatedPinKey = HsmFactory.getInstance(HsmType.getCurrent()).generateKey(KeyType.TPK);
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " generatedPinKey " + generatedPinKey );
        if (generatedPinKey != null)
        	newPinKey = generatedPinKey;

        String newKey = HsmFactory.getInstance(HsmType.getCurrent()).getTPKUnderTMK(atm.getMasterKey(), newPinKey);
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " newKey " + newKey );
		String decEncKey = null;
		if (newKey != null)
			decEncKey = Util.hex2dec(newKey);
		else
			return null;

		atm.setPinKey(newPinKey);
		ctx.put("atm", atm);

		StringBuilder sb = new StringBuilder();

		/**********************************************************/
		/* 42 Extended Encryption Key Change (Communications Key) */
		/* Used to PIN Encryption                                 */
		/**********************************************************/
		sb.append("3 42");

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
