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
package org.jpos.atmc.dao;

import org.jpos.atmc.model.Currency;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class CurrencyManager extends DBManager<Currency> 
{

	public CurrencyManager(DB db) 
	{
		super(db, Currency.class);
	}

	public Currency findByNumber(String number) 
	{
		return getItemByParam("number", number);
	}
	
}
