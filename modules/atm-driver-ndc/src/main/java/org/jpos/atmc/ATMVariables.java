package org.jpos.atmc;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jpos.atmc.model.TrnDefinition;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.util.FSDMsg;

public class ATMVariables
{
	private TrnDefinition td;
	private ISOMsg isoMsg;
	private FSDMsg fsdMsgIn;

    public ATMVariables(TrnDefinition td, ISOMsg isoMsg, FSDMsg fsdMsgIn)
	{
		this.td = td;
		this.isoMsg = isoMsg;
		this.fsdMsgIn = fsdMsgIn;
	}

    /**
     * Format currenies
     *
     */
    public static String formatCurrency(BigDecimal amount, String languageCode, String countryCode) 
	{

        Locale locale = new Locale(languageCode, countryCode);
        Format format = NumberFormat.getCurrencyInstance(locale);
        String formattedAmount = format.format(amount);
        return formattedAmount;
    }
    
    /**
	 * @return the luno
	 */
	public String getLuno() 
	{
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		return fsdMsgIn.get("luno");
	}

	/**
	 * @return the trace
	 */
	public String getTrace() 
	{
	    return this.isoMsg.getString(11).substring(2, 6);
	}

	/**
	 * @return the balance
	 */
	public String getBalance() 
	{
        String strBalance = this.isoMsg.getString(54).substring(8, 20);
        BigDecimal amt = new BigDecimal( strBalance );
        amt = amt.divide( new BigDecimal("100") );
        String formatedAmt = formatCurrency(amt, "en", "US");

        return formatedAmt.substring(1);
	}

	/**
	 * @return the Card Number
	 */
	public String getCardNumber() 
	{
	    return this.isoMsg.getString(35).substring(1, 15);
	}
	
	/**
	 * @return the Current Date
	 */
	public String getCurrentDate() 
	{
        String pattern = "dd-MM-yyyy H:m:s";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
	}

	/**
	 * @return the Current Date
	 */
	public String getOpDescription() 
	{
		return td.getDescription();
	}
	
}