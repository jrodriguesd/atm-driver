package org.jpos.atmc.util;

import java.io.*;
import org.jpos.iso.*;
import org.jpos.util.FSDMsg;

public class Util
{
    public static int lineNumber() 
	{
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    public static String fileName() 
	{
        return Thread.currentThread().getStackTrace()[2].getFileName();
    }

    public static String methodName() 
	{
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * Convert String to int
     *
     */
    public static int str2Int(String str)
	{
		int number = 0;
        try
		{
            number = Integer.parseInt(str);
        }
        catch (NumberFormatException ex)
		{
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
	        Log.printStackTrace(ex);
        }
		return number;
	}

	public static int hexDigit2int(char digit)
	{
		int in = Character.toUpperCase(digit);
		int rc;

	    switch ( in )
		{
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			    rc = (in - 'A') + 10;
				break;
			default :
			    rc = in - '0';
				break;
		}
		return rc;
	}

    /**
     * Convert hex string to decimal string, e.g. 28C691C157CBC94CCAD8C0D3FBF0FBED to 040198145193087203201076202216192211251240251237
     * @param hexString hex string
     * @return String   dec string
     */
	public static String hex2dec(String hexString)
	{
        StringBuilder builder = new StringBuilder();

		for (int i = 0; i < hexString.length(); i += 2)
		{
			String strChunk = hexString.substring(i, i + 2);
			int iChunk = ( hexDigit2int( strChunk.charAt(0) ) * 16) + hexDigit2int( strChunk.charAt(1) );
			String partHex = String.format("%03d", iChunk);
			builder.append(partHex);
		}
		return builder.toString();
	}

    /**
     * Convert decimal string to hex string, e.g. 040198145193087203201076202216192211251240251237 to 28C691C157CBC94CCAD8C0D3FBF0FBED
     * @param  decString decimal string
     * @return String    hex string
     */
	public static String dec2hex(String decString)
	{
        StringBuilder builder = new StringBuilder();

		for (int i = 0; i < decString.length(); i += 3)
		{
			String strChunk = decString.substring(i, i + 3);
			byte bChunk = (byte) str2Int(strChunk);
			String partHex = String.format("%02X", bChunk);
			builder.append(partHex);
		}

		return builder.toString();
	}

    public static void send(ISOSource source, FSDMsg msgOut)
	{
		try
		{
		    source.send( new FSDISOMsg (msgOut) );
		}
		catch (IOException ex)
		{
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
	        Log.printStackTrace(ex);
		}
		catch (ISOException ex)
		{
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
	        Log.printStackTrace(ex);
		}
	}	





}