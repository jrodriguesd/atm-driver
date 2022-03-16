package jfrd;

import java.util.HashMap;
import java.util.Map;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

import org.jpos.iso.*;

import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;

import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;

import java.io.IOException;
import org.jpos.core.*;

public class MYISORequestListener implements ISORequestListener, Configurable
{
    long timeout;

    private final String name = "MYISORequestListener"; 
    private Space<String,Context> sp;
    private String queue;
    private String source;
    private String request;
    private String timestamp;
    private Map<String,String> additionalContextEntries = null;
    private boolean remote = false;

    public boolean process(ISOSource source, ISOMsg m) 
	{
		String retCode = "18";
	    System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        try 
        {
			String trk2 = m.getString(35);
			String validNums = "0123456789";
			char firstChar = trk2.charAt(0);
			if ( validNums.indexOf(firstChar) < 0)
			{
			    trk2 = trk2.substring(1, trk2.length() - 1);
			}

            Track2 t2 = Track2.builder()
                .track( trk2 ).build();        
		    
		    retCode = "00";
		    Card card = Card.getCard(t2.getPan());
		    if (card != null)
		    {
                String encryptedPIN = m.getString(52);
                if ( ! encryptedPIN.equals(card.getEncryptedPIN()) )
				{
		    		retCode = "17";
				}
                if ( ! t2.getExp().equals(card.getExp()) )
		    		retCode = "01";
                if ( ! t2.getServiceCode().equals(card.getServiceCode()) )
		    		retCode = "11";

		        if ( retCode.equals("00") )
		            m.set(54, "1001356C0000000630001002356C000000063000");
			}
		    else
		    	retCode = "18";
        }
        catch (InvalidCardException ex) 
        {
		    retCode = "18";
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }

        try 
        {
            m.setResponseMTI ();
            m.set (39, retCode);
            source.send (m);
        } 
        catch (ISOException ex) 
        {
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
            ex.printStackTrace(System.out);
        } 
        catch (IOException ex) 
        {
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }

        return true;
    }

    public void setConfiguration (Configuration cfg) 
        throws ConfigurationException
    {
	    System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        timeout  = cfg.getLong ("timeout", 15000L);
        sp = (Space<String,Context>) SpaceFactory.getSpace (cfg.get ("space"));
        queue = cfg.get ("queue", null);
        if (queue == null)
            throw new ConfigurationException ("queue property not specified");
        source = cfg.get ("source", ContextConstants.SOURCE.toString());
        request = cfg.get ("request", ContextConstants.REQUEST.toString());
        timestamp = cfg.get ("timestamp", ContextConstants.TIMESTAMP.toString());
        remote = cfg.getBoolean("remote") || cfg.get("space").startsWith("rspace:");
        Map<String,String> m = new HashMap<>();
        cfg.keySet()
           .stream()
           .filter (s -> s.startsWith("ctx."))
           .forEach(s -> m.put(s.substring(4), cfg.get(s)));
        if (m.size() > 0)
            additionalContextEntries = m;
    }

    public String getName() 
	{ 
        return this.name; 
    }  	
}
