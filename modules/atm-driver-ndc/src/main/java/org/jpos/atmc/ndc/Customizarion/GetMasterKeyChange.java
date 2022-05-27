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
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.util.Crypto;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

public class GetMasterKeyChange implements GetSection 
{
	private String lastNumberSend = "000";

	@Override
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
		// String Clearkey  = atm.getMasterKey();
		// String EncKey    = Crypto.encypt( atm.getMasterKey(), Clearkey);
		String newMasterKey = "U23F6C66EF9134D69638EC04F87CD2C9A";
		String msgOut;

		String newKey = HsmFactory.getInstance(HsmType.getCurrent()).getTMKUnderTMK(atm.getMasterKey(), newMasterKey);
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " newKey " + newKey );
		String decEncKey = null;
		if (newKey != null)
			decEncKey = Util.hex2dec(newKey);
		else
			return null;

		StringBuilder sb = new StringBuilder();

		/**************************************************/
		/* 41 Extended Encryption Key Change (Master Key) */
		/**************************************************/
		sb.append("3 41");

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
