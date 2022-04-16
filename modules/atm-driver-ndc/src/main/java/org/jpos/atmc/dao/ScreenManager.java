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
 * Returns days to New Year.
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jpos.atmc.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.jpos.atmc.model.Screen;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class ScreenManager extends DBManager<Screen> {

	public ScreenManager(DB db) {
		super(db, Screen.class);
	}

	public Screen findById(Long id) {
		return getItemByParam("id", id);
	}

	@SuppressWarnings("unchecked")
	public List<Screen> getByConfigId(String configId) 
	{
		Query<Screen> query = db.session().createQuery
		(
		    "from Screen in class org.jpos.atmc.model.Screen where configId = :configId order by number"
		);
		query.setParameter("configId", configId);
		List<Screen> l = query.list();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<Screen> getByConfigId(int limit, String configId, String startNumber) 
	{
		Query<Screen> query = db.session().createQuery
		(
		    "from Screen in class org.jpos.atmc.model.Screen where configId = :configId and number > :startNumber order by number"
		);
		query.setParameter("configId", configId);
		query.setParameter("startNumber", startNumber);
		query.setMaxResults(limit);
		List<Screen> l = query.list();
		return l;
	}

	@SuppressWarnings("unchecked")
	public Screen getByConfigIdLast(String configId)
	{
		Query<Screen> query = db.session().createQuery
		(
		    "from Screen in class org.jpos.atmc.model.Screen where configId = :configId and number > '' order by number desc"
		);
		query.setParameter("configId", configId);
		query.setMaxResults(1);
		Screen screen = query.getSingleResult();
		return screen;
	}

	@SuppressWarnings("unchecked")
	public Screen getScreen(String configId, String number) 
	{
		Query<Screen> query = db.session().createQuery
		(
		    "from Screen in class org.jpos.atmc.model.Screen WHERE configId = :configId AND number = :number"
		);
		query.setParameter("configId", configId);
		query.setParameter("number", number);
		List<Screen> l = query.list();
		Iterator<Screen> iter = l.iterator();

		if (l.size() == 1)
			return (Screen) iter.next();
		else if (l.size() < 1)
			Log.staticPrintln("JFRD " 
		                      + Util.fileName() 
			                  + " Line " + Util.lineNumber() 
			                  + " " + Util.methodName()
					          + " No se Encontro Ninguna Screen configID " + configId 
					          + " scrNumber " + number);
		else if (l.size() > 1)
			Log.staticPrintln("JFRD " 
                              + Util.fileName() 
	                          + " Line " + Util.lineNumber() 
	                          + " " + Util.methodName()
					          + " Se Encontro mas de una Screen configID " + configId 
					          + " scrNumber " + number);
		return null;
	}

}
