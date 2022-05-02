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

public class GetSectionFactory 
{
	public static GetSection getInstance(NDCCustomizarionSections type)
	{
		switch (type)
		{
	        case LUNO:
	    	    return new GetLUNO();
		    case SCREENS:
		    	return new GetScreens();
		    case STATES:
		    	return new GetStates();
		    case FITS:
		    	return new GetFits();
		    case MASTER_KEY_CHANGE:
		    	return new GetMasterKeyChange();
		    case COMMUNICATIONS_KEY_CHANGE:
		    	return new GetCommunicationsKeyChange();
		    case MAC_KEY_CHANGE:
		    	return new GetMACKeyChange();
		    case CONFIGID:
		    	return new GetConfigId();
		    case GET_SUPPLY_COUNTERS:
		    	return new GetSupplyCountersCmd();
		    case CURRENCY_CASSETTE_MAPPING:
		    	return new GetCurrencyCassetteMapping();
		    case GO_IN_SERVICE:
		    	return new GoInServiceCmd();
		    default:
			    break; 
		}
		return null;
	}

}
