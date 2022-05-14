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
package org.jfrd.jwt.util;

import javax.crypto.spec.SecretKeySpec;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;

public class SimpleKeyGenerator implements KeyGenerator 
{
    private static String keyString = null;   

    public Key generateKey() 
	{
		if (keyString == null)
		{
    	    SecureRandom random = new SecureRandom();
    	    keyString = new BigInteger(256, random).toString(256);
            // keyString = "simplekey";
		}

		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " key " + keyString );
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}
