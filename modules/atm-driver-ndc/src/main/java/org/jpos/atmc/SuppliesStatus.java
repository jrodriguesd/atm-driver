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
package org.jpos.atmc;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

class SuppliesStatus
{
    public static final String deviceNames[] = 
	{
        "Reserved",
        "Reserved",
        "Not used",
        "Card Capture Bin",
        "Cash Handler Reject Bin",
        "Deposit Bin",
        "Receipt Paper",
        "Journal Paper",
        "Not used",
        "Not used",
        "Night Safe",
        "Not used",
        "Not used",
        "Not used",
        "Not used",
        "Type 1 Currency Cassettes",
        "Type 2 Currency Cassettes",
        "Type 3 Currency Cassettes",
        "Type 4 Currency Cassettes",
        "Not used",
        "Not used",
        "Statement Paper",
        "Statement Ribbon",
        "Reserved",
        "Reserved",
        "Envelope Dispenser",
    };

    public static final String suppliesDescription[] = 
	{
        "Not configured",
        "Good state",
        "Media low",
        "Media out",
        "Overfill",
	};

    public static void printSuppliesStatus(String strData)
	{
		for (int i=0; i < deviceNames.length; i++ )
		{
		    char suppliesStatus = strData.charAt(i);
			String deviceSuppliesDescription = suppliesDescription[suppliesStatus - '0'];
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + deviceNames[i] + " - " + deviceSuppliesDescription );
		}
	}
}
