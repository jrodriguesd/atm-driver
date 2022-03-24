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
package org.jfrd.webapp.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.jfrd.webapp.model.ATMConfig;
import org.jfrd.webapp.model.Screen;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class ATMConfigManager extends DBManager<ATMConfig> 
{

	public ATMConfigManager(DB db) 
	{
		super(db, ATMConfig.class);
	}

	public ATMConfig getATMConfig(String a, char b)
	{
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public  List<ATMConfig>  getATMConfigUnique() 
	{
		Query<ATMConfig> query = db.session().createQuery
		(
		    "from ATMConfig in class org.jfrd.webapp.model.ATMConfig WHERE screenGroupBase = 0 ORDER BY configId"
		);
		List<ATMConfig> l = query.list();
		return l;
	}
	
	
}
