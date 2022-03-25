package org.jpos.atmc.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.atmc.model.ATMConfig;
import org.jpos.atmc.model.IsoError2ATM;
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
	public ATMConfig getATMConfig(String configId, Character languageATM) 
	{
        try 
        {
            Query<ATMConfig> query = db.session().createQuery 
            (
                "from ATMConfig in class org.jpos.atmc.model.ATMConfig where atmcnf_configid = :configId AND atmcnf_languageatm = :languageATM ORDER BY atmcnf_configid order by configId"
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
    					    + " configID " + configId 
    					    + " languageATM " + languageATM);
            else if (l.size() > 1)
    			Log.staticPrintln("JFRD "  + Util.fileName() + 
    				    " Line " + Util.lineNumber() + 
    				    " "      + Util.methodName() 
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
