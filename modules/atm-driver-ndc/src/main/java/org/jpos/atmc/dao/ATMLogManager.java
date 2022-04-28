package org.jpos.atmc.dao;

import org.jpos.atmc.model.ATMLog;
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

}
