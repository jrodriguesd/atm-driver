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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jpos.util.*;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.util.function.RemoveNewLinesMapper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FlatLogListener implements LogListener, Configurable, Destroyable 
{
	public static final String REALM = "FlatLogListener";

	RemoveNewLinesMapper mapper = new RemoveNewLinesMapper();
    ByteArrayOutputStream captureStream = new ByteArrayOutputStream();
    PrintStream p = new PrintStream(captureStream);

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException 
    {
        mapper.setConfiguration(cfg);
    }

    private String strRegexReplace(String in, String replaceStr, String patternStr)
    {
        // Create a Pattern object
        Pattern pattern = Pattern.compile(patternStr);
	    
        // Now create matcher object.
        Matcher matcher = pattern.matcher(in);
        
        if ( matcher.find() )
        {
        	return in.replaceAll(matcher.group(1), replaceStr );
        }
        else
        	return in;
    }

    private String reformatLogEventStr(String str)
    {
    	str = strRegexReplace(str, "", "^.*(lifespan=\\\".*\\\").*$");
    	str = strRegexReplace(str, REALM, "^.*realm=\\\"(.*)\\\" at.*$");

    	String  pattStr = ".*at=\\\"(.*)\\\".*";
        Pattern pattern = Pattern.compile(pattStr);
        Matcher matcher = pattern.matcher(str);

        if ( matcher.find() )
        {
        	String mf = matcher.group(1);
			if (mf.length() < 29)
                return str.replaceAll(".*at=\\\"(.*)\\\"", "$0   ");
        }

        return str;
    }

    @Override
    public synchronized LogEvent log(LogEvent ev) 
    {
        ev.dump(p, "");
        byte[] result = mapper.apply(captureStream.toByteArray());
        captureStream.reset();
        String resultStr = new String(result) ;
        resultStr = reformatLogEventStr( resultStr );
        return new FrozenLogEvent(resultStr);
    }

    @Override
    public void destroy() 
    {
        if (p != null) {
            p.close();
            p = null;
            captureStream = null;
        }
    }
}
