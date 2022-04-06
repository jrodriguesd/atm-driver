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

import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.atmc.model.TrnDefinition;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class TrnDefinitionManager extends DBManager<TrnDefinition> 
{

	public TrnDefinitionManager(DB db) 
	{
		super(db, TrnDefinition.class);
	}

	@SuppressWarnings("unchecked")
	public TrnDefinition getTrnDefinition(String configId, String atmTrnCode)
	{
		Query<TrnDefinition> query = db.session().createQuery 
		(
		    "from trndefs in class org.jpos.atmc.model.TrnDefinition WHERE trndef_config_id = :configId AND trndef_atm_trn_code = :atmTrnCode order by trndef_id"
		);
		query.setParameter ("configId", configId);
		query.setParameter ("atmTrnCode", atmTrnCode);
		List<TrnDefinition> l = query.list();
		Iterator<TrnDefinition> iter = l.iterator();

		if (l.size() == 1)
			return (TrnDefinition)  iter.next();
		else if (l.size() < 1)
			Log.staticPrintln("JFRD "  + Util.fileName() + 
					" Line " + Util.lineNumber() + 
					" "      + Util.methodName() 
					+ " No se Encontro TrnDefinition" 
					+ " configId " + configId 
					+ " atmTrnCode >"  + atmTrnCode + "<");
		else if (l.size() > 1)
			Log.staticPrintln("JFRD "  + Util.fileName() + 
					" Line " + Util.lineNumber() + 
					" "      + Util.methodName() 
					+ " Se Encontro mas de un TrnDefinition" 
					+ " configId " + configId 
					+ " atmTrnCode >"  + atmTrnCode + "<");
		return null;
	}

}
