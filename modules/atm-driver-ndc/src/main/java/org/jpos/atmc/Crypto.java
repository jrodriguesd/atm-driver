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
