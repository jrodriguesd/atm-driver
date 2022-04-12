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
    public void println(String str) 
    {
    	staticPrintln(str);
    }

    public static void printStackTrace(Exception e)
	{
        staticPrintln(e.getClass().getCanonicalName() + ": " + e.getMessage());
    	StackTraceElement[] stack = e.getStackTrace();
        for (StackTraceElement s : stack) 
        {
            staticPrintln("\t" + s.toString());
        }
	}

    public static void staticPrintln(String str)
	{
        // outOld.println(str);
        // outOld.flush();
    	String[] parts = str.split("\n");
    	for (String part : parts) 
    	{
    		org.jpos.util.Log.getLog (LOGGER, FlatLogListener.REALM).debug(part);
    	}
	}
}
