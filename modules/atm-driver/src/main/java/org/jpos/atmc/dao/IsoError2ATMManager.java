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
            "from isoerr2atm in class org.jpos.atmc.model.IsoError2ATM WHERE isoerr2atm_isoresp = :error AND isoerr2atm_configid = :configID AND isoerr2atm_language639 = :language639 order by isoerr2atm_id"
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
