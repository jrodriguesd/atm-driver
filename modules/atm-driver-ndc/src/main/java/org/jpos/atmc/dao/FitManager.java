package org.jpos.atmc.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;
import org.jpos.atmc.model.Fit;
import org.jpos.atmc.model.Screen;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class FitManager extends DBManager<Fit> {

	public FitManager(DB db) {
		super(db, Fit.class);
	}

	public Fit findById(Long id) {
		return getItemByParam("id", id);
	}

	@SuppressWarnings("unchecked")
	public List<Fit> getByConfigId(String configId) 
	{
		Query<Fit> query = db.session().createQuery
		(
		    "from Fit in class org.jpos.atmc.model.Fit  where configId = :configId order by number"
		);
		query.setParameter("configId", configId);
		List<Fit> l = query.list();
		Iterator<Fit> iter = l.iterator();
		return l;
	}

	@SuppressWarnings("unchecked")
	public Fit getFit(String configId, String number) 
	{
		Query<Fit> query = db.session().createQuery
		(
		    "from Fit in class org.jpos.atmc.model.Fit WHERE configId = :configId AND number = :number"
		);
		query.setParameter("configId", configId);
		query.setParameter("number", number);
		List<Fit> l = query.list();
		Iterator<Fit> iter = l.iterator();

		if (l.size() == 1)
			return (Fit) iter.next();
		else if (l.size() < 1)
			Log.staticPrintln("JFRD " 
		                      + Util.fileName() 
			                  + " Line " + Util.lineNumber() 
			                  + " " + Util.methodName()
					          + " No se Encontro Ninguna Fit"
					          + " configID " + configId 
					          + " scrNumber " + number);
		else if (l.size() > 1)
			Log.staticPrintln("JFRD " 
                              + Util.fileName() 
	                          + " Line " + Util.lineNumber() 
	                          + " " + Util.methodName()
					          + " Se Encontro mas de una Fit" 
					          + "configID " + configId 
					          + " scrNumber " + number);
		return null;
	}

}
