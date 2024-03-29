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

import org.jpos.atmc.model.ATM;
import org.jpos.transaction.Context;

public class GetLUNO implements GetSection 
{
	private String lastNumberSend = "000";

	@Override
	public String getNextCustomizationMsg(Context ctx, String lastNumber) 
	{
        ATM atm = (ATM) ctx.get ("atm");
		final String x1c = new String(new byte[] { 0x1c });

		StringBuilder sb = new StringBuilder();

		/***********************************************/
		/* 45 Extended Encryption Key Change (MAC Key) */
		/***********************************************/
		sb.append("3 1A");
		
		sb.append(x1c + atm.getLuno());

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
