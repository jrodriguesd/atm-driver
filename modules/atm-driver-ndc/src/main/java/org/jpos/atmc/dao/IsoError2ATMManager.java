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
import org.jpos.atmc.model.IsoError2ATM;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class IsoError2ATMManager  extends DBManager<IsoError2ATM> 
{
	public IsoError2ATMManager(DB db) 
	{
		super(db, IsoError2ATM.class);
	}

	@SuppressWarnings("unchecked")
	public IsoError2ATM getIsoError2ATM(int    error,
			                            String configID,
			                            String language639)
	{
        Query<IsoError2ATM> query = db.session().createQuery 
        (
            "from isoerr2atm in class org.jpos.atmc.model.IsoError2ATM WHERE isoerr2atm_isoresp = :error AND isoerr2atm_config_id = :configID AND isoerr2atm_language639 = :language639 order by isoerr2atm_id"
        );
        query.setParameter ("error", error);
        query.setParameter ("configID", configID);
        query.setParameter ("language639", language639);
        List<IsoError2ATM> l = query.list();
        Iterator<IsoError2ATM> iter = l.iterator();
        
        if (l.size() == 1)
            return (IsoError2ATM)  iter.next();
        else if (l.size() < 1)
			Log.staticPrintln("JFRD "  + Util.fileName() + 
					    " Line " + Util.lineNumber() + 
					    " "      + Util.methodName() 
					    + " No se Encontro IsoError2ATM error " + error 
					    + " configID " + configID 
					    + " language639 " + language639);
        else if (l.size() > 1)
			Log.staticPrintln("JFRD "  + Util.fileName() + 
				    " Line " + Util.lineNumber() + 
				    " "      + Util.methodName() 
				    + " Se Encontro mas de un IsoError2ATM error " + error 
				    + " configID " + configID 
				    + " language639 " + language639);
        return null;
	}

}
