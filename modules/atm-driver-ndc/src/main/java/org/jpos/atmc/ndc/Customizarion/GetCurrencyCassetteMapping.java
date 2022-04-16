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

import java.util.List;

import org.jpos.atmc.dao.CassetteManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.Cassette;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.iso.ISOUtil;

public class GetCurrencyCassetteMapping implements GetSection 
{
	private String lastkeySend;

	public String createCustomizationMsg(List<Cassette> casssetes) throws Exception  
	{
		final String x1c = new String(new byte[] { 0x1c });

		StringBuilder sb = new StringBuilder();
		sb.append("3 1E" + x1c);
		String cassSize = ISOUtil.zeropad(casssetes.size(), 2);
		sb.append(cassSize);

        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        for (int i = 0; i < casssetes.size(); i++)
        {
        	Cassette casssete = casssetes.get(i);
        	
    		String currType     = ISOUtil.zeropad(casssete.getType(), 2);
    		String denomination = ISOUtil.zeropad(casssete.getDenomination(), 5);

    		sb.append( currType + casssete.getPosition() + denomination );
            this.lastkeySend = "" + casssete.getPosition();
		}
		return sb.toString();
	}

	@Override
	public String getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
		try 
		{
			List<Cassette> casssetes = DB.exec(db -> new CassetteManager(db).getByLuno(atm.getLuno() ) );
	        return createCustomizationMsg(casssetes);
		} 
		catch (Exception e) 
		{
			Log.printStackTrace(e);
		}
		return null;
	}

	@Override
	public String getLastKeySend() 
	{
		return lastkeySend;
	}

	@Override
	public String getLastKey(String configId) 
	{
		return "4";
 	}

}
