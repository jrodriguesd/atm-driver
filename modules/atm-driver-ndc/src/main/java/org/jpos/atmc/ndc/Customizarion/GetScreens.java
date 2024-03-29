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
package org.jpos.atmc.ndc.Customizarion;

import java.util.List;

import org.jpos.atmc.dao.ScreenManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.Screen;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.transaction.Context;

public class GetScreens implements GetSection 
{
	private String lastNumberSend;

	private static final int MAX_SCREENS_BY_CUSTOMIZATION_MSG = 17;

	public String createCustomizationMsg(List<Screen> screens) 
	{
		final String x1c = new String(new byte[] { 0x1c });

		final String ff  = new String(new byte[] { 0x0c });
		final String si  = new String(new byte[] { 0x0f });
		final String so  = new String(new byte[] { 0x0e });
		final String esc = new String(new byte[] { 0x1b });

		final String FF_GLITCH  = "\u240c";
		final String SI_GLITCH  = "\u240f";
		final String SO_GLITCH  = "\u240e";
		final String ESC_GLITCH = "\u241b";
		

		StringBuilder sb = new StringBuilder();
		sb.append("3 11");

        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        for (int i = 0; i < screens.size(); i++)
        {
        	Screen scr = screens.get(i);

        	String scrData = scr.getData();

        	scrData = scrData.replaceAll(FF_GLITCH,  ff);
        	scrData = scrData.replaceAll(SI_GLITCH,  si);
        	scrData = scrData.replaceAll(SO_GLITCH,  so);
        	scrData = scrData.replaceAll(ESC_GLITCH, esc);
        	sb.append(x1c + scr.getNumber() + scrData);

            this.lastNumberSend = scr.getNumber();
        }

		return sb.toString();
	}

	@Override
	public String getNextCustomizationMsg(Context ctx, String lastNumber) 
	{
        ATM atm = (ATM) ctx.get ("atm");
    	String configId = atm.getConfigId();
    	try 
		{
			List<Screen> screens = DB.exec(db -> new ScreenManager(db).getByConfigId(MAX_SCREENS_BY_CUSTOMIZATION_MSG, configId, lastNumber) );       
	        return createCustomizationMsg(screens);
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
		}
		return null;
	}

	@Override
	public String getLastKey(ATM atm, String configId) 
	{
		try 
		{
		    Screen screen = DB.exec(db -> new ScreenManager(db).getByConfigIdLast(configId) );
		    return screen.getNumber();
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
		}
		return null;
	}

	@Override
	public String getLastKeySend()
	{
		return this.lastNumberSend;
	}

}
