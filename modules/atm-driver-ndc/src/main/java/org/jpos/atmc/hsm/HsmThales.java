package org.jpos.atmc.hsm;

import java.util.UUID;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.core.Sequencer;
import org.jpos.core.VolatileSequencer;
import org.jpos.iso.FSDISOMsg;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOResponseListener;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.MUX;
import org.jpos.util.FSDMsg;
import org.jpos.util.NameRegistrar;

public class HsmThales implements HSM, ISOResponseListener  
{
    private Sequencer seq = new VolatileSequencer();

	private String generateFld0(int index)
	{
		String str = "BCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = str.length();
        int count = 1;
        for (int i = 0; i < len - 3; i++)
            for (int j = i + 1; j < len - 2; j++)
                for (int k = j + 1; k < len -1; k++)
                    for (int l = k + 1; l < len; l++)
                    {
                        if ((count % 14950) == index)
                        {
                            String retStr = "" + str.charAt(i) + str.charAt(j) + str.charAt(k) + str.charAt(l);
                        	return retStr;
                        }
                        count++;

                    }
	    return "ZZZZ";
	}

	private FSDMsg hsmRequest(FSDMsg fsdHsmMsgOut)
	{
        String ds = "jPOS-HSM";
        String muxName = "mux." + ds;
        MUX mux =  NameRegistrar.getIfExists (muxName);
        if (mux != null)
        {
        	UUID ruuid =  UUID.randomUUID();
    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " ruuid " + ruuid.toString() );
    		String fldZero = ruuid.toString().substring(9, 13);
    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " fldZero " + fldZero);

        	// String fldZero = ISOUtil.zeropad(seq.get("hsmSeq", 1), 4);
    		// fsdHsmMsgOut.set("0", fldZero);
    		fsdHsmMsgOut.set("0", generateFld0( seq.get("hsmSeq", 1) )); // "BEBE");
    		fsdHsmMsgOut.dump(Log.out, "");
    		
    		FSDISOMsg isoHsmMsgOut = new FSDISOMsg(fsdHsmMsgOut);
        	
    		FSDISOMsg isoHsmMsgIn = null;

    		try {
				isoHsmMsgIn = (FSDISOMsg) mux.request(isoHsmMsgOut, 18000L);
				// mux.request(isoHsmMsgOut, 18000L, this, null);
			} catch (ISOException e) {
				e.printStackTrace(Log.out);
			}

    		if (isoHsmMsgIn != null)
    		{
        		FSDMsg fsdHsmMsgIn = isoHsmMsgIn.getFSDMsg();
        		int rc = fsdHsmMsgIn.getInt("response-code");
        		if (rc == 0)
        		{
            		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
            		fsdHsmMsgIn.dump(Log.out, "");
            		return fsdHsmMsgIn;
        		}
        		else
            		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " rc " + rc );

    		}
    		else
        		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " isoHsmMsgIn == null " );

        }
        return null;
	}

	@Override
	public void echoTest()
	{
		FSDMsg fsdHsmMsgOut = new FSDMsg("file:cfg/hsm/hsm-");

		fsdHsmMsgOut.set("command", "NC");

		FSDMsg fsdHsmMsgIn = hsmRequest(fsdHsmMsgOut);
	}
	
	@Override
	public String getTMKUnderTMK(String tmkOld, String tmkNew) {
		FSDMsg fsdHsmMsgOut = new FSDMsg("file:cfg/hsm/hsm-");

		fsdHsmMsgOut.set("command", "AE");
		fsdHsmMsgOut.set("source-key", tmkOld);
		fsdHsmMsgOut.set("destination-key", tmkNew);
		fsdHsmMsgOut.set("delimiter", ";");
		fsdHsmMsgOut.set("key-scheme-tmk", "X");
		fsdHsmMsgOut.set("key-scheme-lmk", "0");
		fsdHsmMsgOut.set("key-check-value-type", "0");
		
		FSDMsg fsdHsmMsgIn = hsmRequest(fsdHsmMsgOut);

		if (fsdHsmMsgIn != null)
		{
    		int rc = fsdHsmMsgIn.getInt("response-code");
    		if (rc == 0)
    		{
    			String keyOut = fsdHsmMsgIn.get("stored-key");
    			return keyOut;
    		}
			
		}
		
		return null;
	}

	@Override
	public String getTPKUnderTMK(String tmk, String tpk) {
		return getTMKUnderTMK(tmk, tpk);
	}

	@Override
	public String getTAKUnderTMK(String tmk, String tak) {
		FSDMsg fsdHsmMsgOut = new FSDMsg("file:cfg/hsm/hsm-");

		fsdHsmMsgOut.set("command", "AG");
		fsdHsmMsgOut.set("TMK", tmk);
		fsdHsmMsgOut.set("TAK", tak);
		fsdHsmMsgOut.set("delimiter", ";");
		fsdHsmMsgOut.set("key-scheme-tmk", "X");
		fsdHsmMsgOut.set("key-scheme-lmk", "0");
		fsdHsmMsgOut.set("key-check-value-type", "0");
		
		FSDMsg fsdHsmMsgIn = hsmRequest(fsdHsmMsgOut);

		if (fsdHsmMsgIn != null)
		{
    		int rc = fsdHsmMsgIn.getInt("response-code");
    		if (rc == 0)
    		{
    			String keyOut = fsdHsmMsgIn.get("stored-key");
    			return keyOut;
    		}
			
		}
		
		return null;
	}

	@Override
	public String translatePin(String tpk, String zpk, String pinBlock, String accountNumber) {
		FSDMsg fsdHsmMsgOut = new FSDMsg("file:cfg/hsm/hsm-");

		fsdHsmMsgOut.set("command", "CA");
		fsdHsmMsgOut.set("source-tpk", tpk);
		fsdHsmMsgOut.set("destination-zpk", zpk);
		fsdHsmMsgOut.set("maximum-pin-length", "12");
		fsdHsmMsgOut.set("source-pin-block", pinBlock);
		fsdHsmMsgOut.set("Source PIN block format", "01");
		fsdHsmMsgOut.set("Destination PIN block format", "01");
		fsdHsmMsgOut.set("Account number", accountNumber);
		
		FSDMsg fsdHsmMsgIn = hsmRequest(fsdHsmMsgOut);

		if (fsdHsmMsgIn != null)
		{
    		int rc = fsdHsmMsgIn.getInt("response-code");
    		if (rc == 0)
    		{
    			String pinBlockOut = fsdHsmMsgIn.get("destination-pin-block");
    			return pinBlockOut;
    		}
			
		}
		
		return null;
	}

	@Override
	public String generateKey(KeyType keyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void responseReceived(ISOMsg resp, Object handBack) {
		FSDISOMsg isoHsmMsgIn = (FSDISOMsg) resp;
		
		if (isoHsmMsgIn != null)
		{
    		FSDMsg fsdHsmMsgIn = isoHsmMsgIn.getFSDMsg();
    		int rc = fsdHsmMsgIn.getInt("response-code");
    		if (rc == 0)
    		{
        		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        		fsdHsmMsgIn.dump(Log.out, "");
    		}
    		else
        		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " rc " + rc );

		}
		else
    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " isoHsmMsgIn == null " );
	}

}
