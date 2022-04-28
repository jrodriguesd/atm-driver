package org.jpos.atmc.dao;

import org.jpos.atmc.model.Currency;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class CurrencyManager extends DBManager<Currency> 
{

	public CurrencyManager(DB db) 
	{
		super(db, Currency.class);
	}

	public Currency findByNumber(String number) 
	{
		return getItemByParam("number", number);
	}
	
}
