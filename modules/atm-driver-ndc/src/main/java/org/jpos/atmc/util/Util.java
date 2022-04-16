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

    public static String formatHexDump(byte[] array, int offset, int length) 
	{
        final int width = 16;

        StringBuilder builder = new StringBuilder();

        for (int rowOffset = offset; rowOffset < offset + length; rowOffset += width) 
		{
            builder.append(String.format("%06d:  ", rowOffset));

            for (int index = 0; index < width; index++) 
			{
                if (rowOffset + index < array.length) 
				{
                    builder.append(String.format("%02x ", array[rowOffset + index]));
                } 
				else 
				{
                    builder.append("   ");
                }
            }

            if (rowOffset < array.length) {
                int asciiWidth = Math.min(width, array.length - rowOffset);
                builder.append("  |  ");
                try 
				{
                    builder.append(new String(array, rowOffset, asciiWidth, "UTF-8").replaceAll("\r\n", " ").replaceAll("\n", " ").replaceAll("\\p{C}", "."));
                } 
				catch (UnsupportedEncodingException ignored) 
				{
                    //If UTF-8 isn't available as an encoding then what can we do?!
                }
            }

            builder.append("\n");
        }
        return builder.toString();
    }

    public static String formatHexDump(String str) 
	{
		return formatHexDump(str.getBytes(), 0, str.length());
	}

}
