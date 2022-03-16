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
