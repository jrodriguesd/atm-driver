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
	public ATMConfig getATMConfig(String configID, String atmOperationCode) 
	{
        try 
        {
            Query<ATMConfig> query = db.session().createQuery 
            (
                "from atmconfigs in class org.jpos.atmc.model.ATMConfig where atmcnf_configid = :configID order by atmcnf_id"
            );
            query.setParameter ("configID", configID);
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
