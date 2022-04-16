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

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.atmc.model.ATMConfig;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class ATMConfigManager extends DBManager<ATMConfig> 
{
	public ATMConfigManager(DB db) 
	{
		super(db, ATMConfig.class);
	}

	public ATMConfig findById(Long id) 
	{
		return getItemByParam("id", id);
	}

	@SuppressWarnings("unchecked")
	public  List<ATMConfig>  getATMConfigUnique() 
	{
		Query<ATMConfig> query = db.session().createQuery
		(
		    "from ATMConfig in class org.jpos.atmc.model.ATMConfig where screenGroupBase = 0 order by configId"
		);
		List<ATMConfig> l = query.list();
		return l;
	}

	@SuppressWarnings("unchecked")
	public  List<ATMConfig>  getAll() 
	{
		Query<ATMConfig> query = db.session().createQuery
		(
		    "from ATMConfig in class org.jpos.atmc.model.ATMConfig order by configId"
		);
		List<ATMConfig> l = query.list();
		return l;
	}
	
	
	@SuppressWarnings("unchecked")
	public ATMConfig getATMConfig(String configId, Character languageATM) 
	{
        try 
        {
            Query<ATMConfig> query = db.session().createQuery 
            (
                "from ATMConfig in class org.jpos.atmc.model.ATMConfig where configId = :configId AND languageATM = :languageATM order by configId"
            );
            query.setParameter ("configId", configId);
            query.setParameter ("languageATM", languageATM);
            List<ATMConfig> l = query.list();
            Iterator<ATMConfig> iter = l.iterator();
            if (l.size() == 1)
                return (ATMConfig)  iter.next();
            else if (l.size() < 1)
    			Log.staticPrintln("JFRD "  + Util.fileName() + 
    					    " Line " + Util.lineNumber() + 
    					    " "      + Util.methodName() 
					        + " No se Encontro ninguna ATMConfig"
    					    + " configID " + configId 
    					    + " languageATM " + languageATM);
            else if (l.size() > 1)
    			Log.staticPrintln("JFRD "  + Util.fileName() + 
    				    " Line " + Util.lineNumber() + 
    				    " "      + Util.methodName() 
				        + " Se Encontro mas de una ATMConfig" 
					    + " configID " + configId 
					    + " languageATM " + languageATM);

        } 
        catch (HibernateException e) 
        {
            db.getLog().warn (e);
        }
        return null;
	}

	@SuppressWarnings("unchecked")
	public ATMConfig getATMConfig(String configId, String atmOperationCode) 
	{
        try 
        {
            Query<ATMConfig> query = db.session().createQuery 
            (
                "from ATMConfig in class org.jpos.atmc.model.ATMConfig where configId = :configId order by configId"
            );
            query.setParameter ("configId", configId);
            List<ATMConfig> l = query.list();
            Iterator<ATMConfig> iter = l.iterator();
            for (int i=0; iter.hasNext(); i++) 
            {
            	ATMConfig atmConfig = (ATMConfig) iter.next();
                if ( (i == 0) && (l.size() == 1) )
		        	/* MonoLanguage Configuration */
                	return atmConfig;
                else 
				{
		        	/* MultiLanguage Configuration */
				    char languageATM = atmOperationCode.charAt(atmConfig.getLanguageIndex());
					if (atmConfig.getLanguageATM() == languageATM )
					    return atmConfig;
				}
            }
        } 
        catch (HibernateException e) 
        {
            db.getLog().warn (e);
        }
        return null;
    }

}
