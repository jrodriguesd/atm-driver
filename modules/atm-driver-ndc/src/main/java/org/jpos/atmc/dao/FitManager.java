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

import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.jpos.atmc.model.Fit;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class FitManager extends DBManager<Fit> {

	public FitManager(DB db) {
		super(db, Fit.class);
	}

	public Fit findById(Long id) {
		return getItemByParam("id", id);
	}

	@SuppressWarnings("unchecked")
	public List<Fit> getByConfigId(String configId) 
	{
		Query<Fit> query = db.session().createQuery
		(
		    "from Fit in class org.jpos.atmc.model.Fit  where configId = :configId order by number"
		);
		query.setParameter("configId", configId);
		List<Fit> l = query.list();
		Iterator<Fit> iter = l.iterator();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<Fit> getByConfigId(int limit, String configId, String startNumber) 
	{
		Query<Fit> query = db.session().createQuery
		(
		    "from Fit in class org.jpos.atmc.model.Fit where configId = :configId and number > :startNumber order by number"
		);
		query.setParameter("configId", configId);
		query.setParameter("startNumber", startNumber);
		query.setMaxResults(limit);
		List<Fit> l = query.list();
		return l;
	}

	@SuppressWarnings("unchecked")
	public Fit getByConfigIdLast(String configId)
	{
		Query<Fit> query = db.session().createQuery
		(
		    "from Fit in class org.jpos.atmc.model.Fit where configId = :configId and number > '' order by number desc"
		);
		query.setParameter("configId", configId);
		query.setMaxResults(1);
		Fit fit = query.getSingleResult();
		return fit;
	}

	@SuppressWarnings("unchecked")
	public Fit getFit(String configId, String number) 
	{
		Query<Fit> query = db.session().createQuery
		(
		    "from Fit in class org.jpos.atmc.model.Fit WHERE configId = :configId AND number = :number"
		);
		query.setParameter("configId", configId);
		query.setParameter("number", number);
		List<Fit> l = query.list();
		Iterator<Fit> iter = l.iterator();

		if (l.size() == 1)
			return (Fit) iter.next();
		else if (l.size() < 1)
			Log.staticPrintln("JFRD " 
		                      + Util.fileName() 
			                  + " Line " + Util.lineNumber() 
			                  + " " + Util.methodName()
					          + " No se Encontro Ninguna Fit"
					          + " configID " + configId 
					          + " scrNumber " + number);
		else if (l.size() > 1)
			Log.staticPrintln("JFRD " 
                              + Util.fileName() 
	                          + " Line " + Util.lineNumber() 
	                          + " " + Util.methodName()
					          + " Se Encontro mas de una Fit" 
					          + "configID " + configId 
					          + " scrNumber " + number);
		return null;
	}

}
