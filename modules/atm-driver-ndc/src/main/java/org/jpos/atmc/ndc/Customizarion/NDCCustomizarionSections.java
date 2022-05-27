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

public enum NDCCustomizarionSections 
{
	LUNO("LUNO"), 
	SCREENS("SCREENS"), 
	STATES("STATES"), 
	FITS("FITS"),
	MASTER_KEY_CHANGE("PIN_KEY_CHANGE"),
	PIN_KEY_CHANGE("PIN_KEY_CHANGE"),
	MAC_KEY_CHANGE("MAC_KEY_CHANGE"),
	CONFIGID("CONFIGID"),
    CURRENCY_CASSETTE_MAPPING("CURRENCY_CASSETTE_MAPPING"), 
    GET_SUPPLY_COUNTERS("GET_SUPPLY_COUNTERS"), 
	GO_IN_SERVICE("GO_IN_SERVICE"),
	LAST("LAST");

	private final String description;
	private static final NDCCustomizarionSections sections[] = NDCCustomizarionSections.values();

	// private enum constructor
	private NDCCustomizarionSections(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}

    public static NDCCustomizarionSections getFirst()
    {
    	return sections[0];
    }
	
    public static NDCCustomizarionSections next(NDCCustomizarionSections acs)
    {
    	return sections[ acs.ordinal() + 1 ];
    }

}
