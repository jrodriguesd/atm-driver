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

import org.jpos.atmc.dao.StateManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.State;
import org.jpos.atmc.util.Log;
import org.jpos.ee.DB;

public class GetStates implements GetSection 
{
	private String lastNumberSend;

	private static final int MAX_STATES_BY_CUSTOMIZATION_MSG = 136;

	public String createCustomizationMsg(List<State> states) 
	{
		final String x1c = new String(new byte[] { 0x1c });

		StringBuilder sb = new StringBuilder();
		sb.append("3 12");
        for (int i = 0; i < states.size(); i++)
        {
        	State sta = states.get(i);
        	sb.append(x1c + sta.getNumber() + sta.getType() + 
	                        sta.getS1() + sta.getS2() +
	                        sta.getS3() + sta.getS4() +
			                sta.getS5() + sta.getS6() +
	                        sta.getS7() + sta.getS8() );

        	this.lastNumberSend = sta.getNumber();
        }

		return sb.toString();
	}
	
	
	@Override
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
    	try 
		{
			List<State> states = DB.exec(db -> new StateManager(db).getByConfigId(MAX_STATES_BY_CUSTOMIZATION_MSG, configId, lastNumber) );       
	        return createCustomizationMsg(states);
		} 
		catch (Exception e) 
		{
			Log.printStackTrace(e);
		}
		return null;
	}

	@Override
	public String getLastKeySend() 
	{
		return this.lastNumberSend;
	}

	@Override
	public String getLastKey(String configId) 
	{
		try 
		{
		    State state = DB.exec(db -> new StateManager(db).getByConfigIdLast(configId) );
		    return state.getNumber();
		} 
		catch (Exception e) 
		{
		    Log.printStackTrace(e);
		}
		return null;
	}

}
