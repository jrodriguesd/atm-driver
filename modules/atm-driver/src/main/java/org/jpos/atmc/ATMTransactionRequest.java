package org.jpos.atmc;

import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;

import org.jpos.iso.*;
import org.jpos.util.FSDMsg;
import org.jpos.util.NameRegistrar;

import org.jpos.iso.packager.ISO87APackager;

import org.jpos.q2.iso.QMUX;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

public class ATMTransactionRequest
{
    /**
	 *
	 * Transaction Reply Command Function Identifier
	 *
	 */
	public static final String DEPOSIT_AND_PRINT                                              = "1";
	public static final String DISPENSE_AND_PRINT                                             = "2";
	public static final String DISPLAY_AND_PRINT                                              = "3";
	public static final String PRINT_IMMEDIATE                                                = "4";
	public static final String NEXT_STATE_AND_PRINT                                           = "5";
	public static final String NIGHT_SAFE_DEPOSIT_AND_PRINT                                   = "6";
	public static final String EJECT_CARD_DISPENSE_AND_PRINT                                  = "A";
	public static final String PARALLEL_DISPENSE_PRINT_AND_CARD_EJECT                         = "B";
	public static final String CARD_BEFORE_PARALLEL_DISPENSE_PRINT                            = "F";
	public static final String PRINT_STATEMENT_AND_WAIT                                       = "P";
	public static final String PRINT_STATEMENT_AND_NEXT_STATE                                 = "Q";
	public static final String REFUND_BNA_DEPOSITED_MONEY_AND_NEXT_STATE                      = "*";
	public static final String ENCASH_BNA_DEPOSITED_MONEY_RECEIPT_IF_REQUESTED_AND_NEXT_STATE = "-";
	public static final String ENCASH_BNA_DEPOSITED_MONEY_AND_WAIT_ANOTHER_REPLY              = "’";
	public static final String PROCESS_CPM_CHEQUE                                             = "-";

    /**
	 *
	 * Transaction Reply Card Return/Retain Flag
	 *
	 */
	public static final String RETURN_CARD                                                    = "0";
	public static final String RETAIN_CARD                                                    = "1";

    /**
	 *
	 * Transaction Reply Printer Flas
	 *
	 */
	public static final String DO_NOT_PRINT                                                   = "0";
	public static final String JOURNAL_PRINTER_ONLY                                           = "1";
	public static final String RECEIPT_PRINTER_ONLY                                           = "2";
	public static final String RECEIPT_AND_JOURNAL_PRINTER                                    = "3";

    /**
     * replace some caracters in a PIN received from ATM
     *
     */
    public static String normalizePIN(String PIN)
	{
		StringBuilder sb = new StringBuilder(PIN);
		for (int i = 0; i < sb.length(); i++)
		{
			if (sb.charAt(i) == ':') sb.setCharAt(i, 'A');

			if (sb.charAt(i) == ';') sb.setCharAt(i, 'B');

			if (sb.charAt(i) == '<') sb.setCharAt(i, 'C');

			if (sb.charAt(i) == '=') sb.setCharAt(i, 'D');

			if (sb.charAt(i) == '>') sb.setCharAt(i, 'E');

			if (sb.charAt(i) == '?') sb.setCharAt(i, 'F');
		}
		return sb.toString();
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
        // Log.staticPrintln("Orginal Amount {} and Formatted Amount {}", amount, formattedAmount);
        return formattedAmount;
    }

    public static void sendErrorState(BaseChannel source, FSDMsg msgIn, String state)
	{
	    String schema = "file:cfg/ndc-";
		FSDMsg msgOut = new FSDMsg(schema);
		
		msgOut.set("message-class",               "4");
		msgOut.set("luno",                        msgIn.get("luno") );
		msgOut.set("time-variant-number",         msgIn.get("time-variant-number") );
		msgOut.set("next-state-number",           state);
		msgOut.set("number-notes-dispense",       "00000000");
		msgOut.set("transaction-serial-number",   "8951");
		msgOut.set("function-identifier",         NEXT_STATE_AND_PRINT);
		msgOut.set("screen-number",               state);
		msgOut.set("screen-display-update",       state);
		msgOut.set("message-coordination-number", msgIn.get("message-coordination-number") );
		msgOut.set("card-return-retain-flag",     RETURN_CARD);
		msgOut.set("printer-flag",                RECEIPT_PRINTER_ONLY);
		msgOut.set("printer-data",                "0102");
		msgOut.dump(Log.out, "");
		Util.send(source, msgOut);
		return;
	}


    public static void processTransactionRequest(BaseChannel source, FSDMsg msgIn)
	{
        /* ISO87APackager packager = new ISO87APackager(); */

        // Create ISO Message
        ISOMsg m = new ISOMsg();
        m.setPackager( new ISO87APackager() );

		try
		{
            m.setMTI("0200");
		}
		catch (ISOException ex)
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
	        Log.printStackTrace(ex);
		}

        String normalizedPIN = normalizePIN( msgIn.get("buffer-A-pin") );
		
        m.set(  3, "301000");
		m.set(  7, ISODate.getANSIDate(new Date()));
        m.set( 11, "561786");
        m.set( 12, ISODate.getTime (new Date()));
		m.set( 13, ISODate.getExpirationDate(new Date()));
		m.set( 15, ISODate.getExpirationDate(new Date()));
		m.set( 17, ISODate.getExpirationDate(new Date()));
		m.set( 32, "1111");
        m.set( 35, msgIn.get("track2") );
        m.set( 41, "29110001");         // Card Acceptor Terminal Identification Code
        m.set( 42, "1234567");          // Card Acceptor Identification Code
        m.set( 49, "840");              // Currency Code
        m.set( 52, normalizedPIN );
        m.set(102, "1020166319");       // Primary Account Number

        //**********************************************************************		
        // final Context ctx  = new Context ();
        // if (remote)
        //     src = new SpaceSource((LocalSpace)sp, src, timeout);
        // ctx.put (timestamp, new Date(), remote);
        // ctx.put (source, src, remote);
        // ctx.put (request, m, remote);
        // if (additionalContextEntries != null) 
		// {
        //     additionalContextEntries.entrySet().forEach
		// 	(
        //         e -> ctx.put(e.getKey(), e.getValue(), remote)
        //     );
        // }
        // sp.out(queue, ctx, timeout);
        //**********************************************************************		

        ISOMsg response = null;
		try
		{
		    QMUX mux = (QMUX) NameRegistrar.get ("mux.jPOS-jPTS");
		    response = mux.request (m, 30000);
			if (response != null)
			    response.dump(Log.out, "  ");
			else
	            Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Sin Respuesta" );
		}
		catch (Exception ex)
		{
	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
	        Log.printStackTrace(ex);
		}

        if (response == null)
		{
			// Pantalla de error (En Este Momento Estamos Inhabilitados para completar su Transaccion por Favor Intente Mas Tarde)
			sendErrorState(source, msgIn, "361");
		    return;
		}

        if ( response.getString(39).equals("17") )
		{
			// Pantalla de error (350 Pin Incorrecto)
			sendErrorState(source, msgIn, "350");
		    return;
		}

        if ( response.getString(39).equals("00") )
		{
		    String schema = "file:cfg/ndc-";
		    FSDMsg msgOut = new FSDMsg(schema);
		    
		    msgOut.set("message-class",               "4");
		    msgOut.set("luno",                        msgIn.get("luno") );
		    msgOut.set("time-variant-number",         msgIn.get("time-variant-number") );
		    msgOut.set("next-state-number",           "171");
		    msgOut.set("number-notes-dispense",       "00000000");
		    msgOut.set("transaction-serial-number",   "8951");
		    msgOut.set("function-identifier",         NEXT_STATE_AND_PRINT);
		    msgOut.set("screen-number",               "171");

            String strBalance = response.getString(54).substring(8, 20);
			BigDecimal amt = new BigDecimal( strBalance );
			amt = amt.divide( new BigDecimal("100") );
			String formatedAmt = formatCurrency(amt, "en", "US");

		    msgOut.set("screen-display-update",       "171DN" + formatedAmt);
		    msgOut.set("message-coordination-number", msgIn.get("message-coordination-number") );
		    msgOut.set("card-return-retain-flag",     RETURN_CARD);
		    msgOut.set("printer-flag",                RECEIPT_PRINTER_ONLY);
		    msgOut.set("printer-data",                "0102");
		    msgOut.dump(Log.out, "");
		    Util.send(source, msgOut);
		    return;
		}
	}
}
