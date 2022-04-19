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

public class Log extends PrintStream
{
	public static final String LOGGER   = "atm-logger"; // Debe Ser el Nombre de un Logger Real

	private static FileOutputStream file;
    public  static PrintStream outOld;
    public  static Log out;  

    static
	{
        try	
        {
        	String fileName = "";
        	String os = System.getProperty("os.name").toLowerCase();
        	if (os.contains("win")){
                fileName = "NUL:";
        	}
        	else if (os.contains("osx")){
                fileName = "/dev/null";
        	}      
        	else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){
                fileName = "/dev/null";
        	}
        	
			file = new FileOutputStream(fileName, true);
			// outOld = new PrintStream(file, true);
    	    out = new Log(file);
        } 
        catch (IOException ex) 
        {
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }

    public Log(FileOutputStream file) 
	{
        super(file);
	}

	@Override
    public void print(String str) 
    {
    	staticPrintln(str);
    }

	@Override
    public void println(String str) 
    {
    	staticPrintln(str);
    }

    public static void staticPrintln(String str)
	{
    	org.jpos.util.Log.getLog (LOGGER, FlatLogListener.REALM).debug(str);
	}
}
