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
		    "from trndefs in class org.jpos.atmc.model.TrnDefinition WHERE trndef_configid = :configId AND trndef_atm_trn_code = :atmTrnCode order by trndef_id"
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
