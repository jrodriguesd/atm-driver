package org.jpos.atmc.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.jpos.atmc.model.ATMLog;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class ATMLogManager extends DBManager<ATMLog>
{
	public ATMLogManager(DB db) 
	{
		super(db, ATMLog.class);
	}

	public ATMLog getATMLog(Long id)
	{
		return getItemByParam("id", id);
	}	

	@SuppressWarnings("unchecked")
	public List<ATMLog> getAll(String whereCondition, String orderBy, int start, int limit)
	{
		String queryStr = "from ATMLog in class org.jpos.atmc.model.ATMLog " + whereCondition + " order by " + orderBy;
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " queryStr " + queryStr );
		Query<ATMLog> query = db.session().createQuery( queryStr );
		if (start > 0) query.setFirstResult(start);
		if (limit > 0) query.setMaxResults(limit);
		List<ATMLog> l = query.list();
		return l;
	}

	@SuppressWarnings("unchecked")
	public int getItemCount(String whereCondition)
	{
		String queryStr = "from ATMLog in class org.jpos.atmc.model.ATMLog " + whereCondition;
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " queryStr " + queryStr );
		Query<ATMLog> query = db.session().createQuery( queryStr );
		List<ATMLog> l = query.list();
		return l.size();
	}

	
}
