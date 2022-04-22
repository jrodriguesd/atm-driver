package org.jpos.atmc.util;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class NDCISOMsg extends ISOMsg implements Cloneable  
{
	NDCFSDMsg fsd;

    public NDCISOMsg (NDCFSDMsg fsd) 
    {
        super();
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        this.fsd = fsd;
    }
	
	public String getMTI() 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		return getString(0);
	}

	public byte[] pack() throws ISOException 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		try {
            return fsd.packToBytes();
		} catch (Exception e) {
			throw new ISOException(e);
		}
	}

	public int unpack(byte[] b) throws ISOException 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	    Util.printHexDump(Log.out, b);
		try {
            fsd.unpack (b);
			return b.length;
		} catch (Exception e) {
			throw new ISOException(e);
		}
	}

	/*
	public void unpack(InputStream in) throws IOException, ISOException 
	{
	    Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		synchronized (this) 
		{
            try {
				fsd.unpack(in);
			} catch (JDOMException e) {
				e.printStackTrace();
			}
		}
	}
	*/	

	public byte[] getBytes() throws ISOException
	{
		try {
			byte bfsd[] = fsd.packToBytes();
			byte bOut[] = new byte[bfsd.length - 8];
	        System.arraycopy(bfsd, 0, bOut, 0, bfsd.length - 8);
			return bOut;
		} catch (Exception e) {
			e.printStackTrace(Log.out);
		}
		return null;
	}

    public NDCFSDMsg getFSDMsg() 
    {
        return fsd;
    }

}
