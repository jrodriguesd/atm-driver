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

import java.util.List;

import org.hibernate.query.Query;
import org.jpos.atmc.model.ATMLog;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class ATMLogManager extends DBManager<ATMLog>
{
	public ATMLogManager(DB db) 
	{
		super(db, ATMLog.class);
	}

	public ATMLog getATMLog(Long id)
	{
		return getItemByParam("id", id);
	}	

	@SuppressWarnings("unchecked")
	public List<ATMLog> getAll(String whereCondition, String orderBy, int start, int limit)
	{
		String queryStr = "from ATMLog in class org.jpos.atmc.model.ATMLog " + whereCondition + " order by " + orderBy;
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " queryStr " + queryStr );
		Query<ATMLog> query = db.session().createQuery( queryStr );
		if (start > 0) query.setFirstResult(start);
		if (limit > 0) query.setMaxResults(limit);
		List<ATMLog> l = query.list();
		return l;
	}

	@SuppressWarnings("unchecked")
	public int getItemCount(String whereCondition)
	{
		String queryStr = "from ATMLog in class org.jpos.atmc.model.ATMLog " + whereCondition;
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " queryStr " + queryStr );
		Query<ATMLog> query = db.session().createQuery( queryStr );
		List<ATMLog> l = query.list();
		return l.size();
	}

	
}
