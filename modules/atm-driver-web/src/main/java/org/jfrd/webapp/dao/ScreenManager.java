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
import org.jfrd.webapp.model.Screen;
import org.jfrd.webapp.util.Log;
import org.jfrd.webapp.util.Util;
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
	public Screen getScreen(String configID, String scrNumber) 
	{
		Query<Screen> query = db.session().createQuery
		(
		    "from Screen in class org.jfrd.webapp.model.Screen WHERE scr_config_id = :configID AND scr_number = :scrNumber"
		);
		query.setParameter("configID", configID);
		query.setParameter("scrNumber", scrNumber);
		List<Screen> l = query.list();
		Iterator<Screen> iter = l.iterator();

		if (l.size() == 1)
			return (Screen) iter.next();
		else if (l.size() < 1)
			Log.staticPrintln("JFRD " 
		                      + Util.fileName() 
			                  + " Line " + Util.lineNumber() 
			                  + " " + Util.methodName()
					          + " No se Encontro Screen configID " + configID 
					          + " scrNumber " + scrNumber);
		else if (l.size() > 1)
			Log.staticPrintln("JFRD " 
                              + Util.fileName() 
	                          + " Line " + Util.lineNumber() 
	                          + " " + Util.methodName()
			                  + " Se Encontro mas de una Screen configID " + configID 
					          + " scrNumber " + scrNumber);
		return null;
	}

}
