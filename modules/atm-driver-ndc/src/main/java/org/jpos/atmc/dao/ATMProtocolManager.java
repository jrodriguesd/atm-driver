/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Returns days to New Year.
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jpos.atmc.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.jpos.atmc.model.ATMProtocol;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class ATMProtocolManager extends DBManager<ATMProtocol> 
{

	public ATMProtocolManager(DB db) 
	{
		super(db, ATMProtocol.class);
	}

	@SuppressWarnings("unchecked")
	public  List<ATMProtocol>  getATMProtocolUnique() 
	{
		Query<ATMProtocol> query = db.session().createQuery
		(
		    "from ATMProtocol in class org.jpos.atmc.model.ATMProtocol order by name"
		);
		List<ATMProtocol> l = query.list();
		return l;
	}

}
