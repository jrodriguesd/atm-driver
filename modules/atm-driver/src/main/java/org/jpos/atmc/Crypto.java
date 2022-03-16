package org.jpos.atmc;

import java.security.Security;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.iso.ISOUtil;

/**
 * Crypto Operations Class
 *
 * @author
 */
public class Crypto
{
    public static String encypt(String strKey, String strData)
	{
        // convert hex string to byte array
        byte[] keyBytes = ISOUtil.hex2byte(strKey);
        byte[] input    = ISOUtil.hex2byte(strData);

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        SecretKeySpec key = new SecretKeySpec(keyBytes, "3DES");
		byte[] cipherText = null;
		int ctLength = 0;
		try
		{
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = new byte[cipher.getOutputSize(input.length)];
            ctLength = cipher.update(input, 0, input.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
		}
		catch (Exception ex) 
		{
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
	        Log.printStackTrace(ex);
        }

		return ISOUtil.byte2hex(cipherText);
	}
}
