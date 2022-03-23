package org.jpos.atmc;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.TrnDefinition;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.Context;
import org.jpos.util.FSDMsg;

public class ATMVariables
{
	private TrnDefinition td;
	private ISOMsg isoMsg;
	private FSDMsg fsdMsgIn;
	private ATM atm;

    public ATMVariables(TrnDefinition td, ISOMsg isoMsg, FSDMsg fsdMsgIn, ATM atm)
	{
		this.td = td;
		this.isoMsg = isoMsg;
		this.fsdMsgIn = fsdMsgIn;
		this.atm = atm;
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
	 * @return Transaction Serial Number
	 */
	public String getTrnSerialNumber() 
	{
		/*
		 * Es un Campo de la Tabla ATMs atm_trn_ser_num secuencia de las Transacciones realizadas
		 */
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        String retStr = "    ";

        try 
        {
            int trnSerNum = atm.getTrnSerNum() + 1;
            retStr = ISOUtil.zeropad(Integer.toString( trnSerNum ), 4);
		} 
        catch (ISOException e) 
        {
			Log.printStackTrace(e);
		}
		return retStr;
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
	 * @return the Operation Description
	 */
	public String getOpDescription() 
	{
		return td.getDescription();
	}
	
}