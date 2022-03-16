package org.jpos.atmc.dao;

import org.jpos.atmc.util.Util;
import org.jpos.atmc.util.Log;

import org.jpos.ee.DB;

import org.jpos.atmc.model.ATM;
import org.jpos.ee.DBManager;

public class ATMManager extends DBManager<ATM>
{
	public ATMManager(DB db) 
	{
		super(db, ATM.class);
	}

	public ATM findByIP(String ip) 
	{
		return getItemByParam("ip", ip);
	}

	public static ATM getATM(String ip)
	{

		try 
		{
			return DB.exec(db -> new ATMManager(db).findByIP(ip));
		} 
		catch (Exception e) 
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Exception" );
            Log.staticPrintln(e.getMessage());
		}

		return null;
	}
}