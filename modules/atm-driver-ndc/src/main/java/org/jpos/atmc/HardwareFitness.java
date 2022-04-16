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

class HardwareFitness
{
    public static final String deviceNames[] = 
	{
        "Time-of-Day Clock",
        "High Order Communications",
        "System Disk",
        "Magnetic Card Reader/Writer",
        "Cash Handler",
        "Depository",
        "Receipt Printer",
        "Journal Printer",
        "09 Reserved",
        "Enhanced Thermal Statement Printer (SDC+)",
        "Night Safe Depository",
        "Encryptor",
        "Security Camera",
        "Door Access",
        "Flex Disk",
        "Cassette type 1",
        "Cassette type 2",
        "Cassette type 3",
        "Cassette type 4",
        "20 Reserved",
        "Statement Printer",
        "Signage Display",
        "24 Reserved",
        "System Display",
        "Media Entry Indicators",
        "Envelope Dispenser",
        "Document Processing Module",
        "Coin Dispensing Module Tamper Indication",
        "Document Processing Module, Module Tamper Indication",
        "32 Reserved",
        "Digital Audio Service",
        "34 Reserved",
        "Bunch Note Acceptor",
        "Cheque Processing Module",
        "37 Reserved",
        "38 Reserved",
    };

    public static final String statusDescription[] = 
	{
        "No Error",
        "Routine errors have occurred",
        "Warning conditions have occurred",
        "Suspend",
        "Fatal error condition exists",
	};

    public static void printHardwareFitness(String strData)
	{
		for (int i=0; i < deviceNames.length; i++ )
		{
			char deviceStatus = strData.charAt(i);
			if (deviceStatus != '0')
			{
				String deviceStatusDescription = statusDescription[deviceStatus - '0'];
	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + deviceNames[i] + " - " + deviceStatusDescription );
			}
		}
	}
}
