package org.jpos.atmc.ATMCustomizarion;

import java.util.List;

import org.jpos.atmc.dao.ScreenManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.Screen;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;

public class ATMSendCustomizationScreens implements ATMSendCustomization 
{
	private String lastNumberSend;

	private static final int MAX_SCREENS_BY_CUSTOMIZATION_MSG = 17;

	public String createCustomizationMsg(List<Screen> screens) 
	{
		final String x1c = new String(new byte[] { 0x1c });

		final String ff  = new String(new byte[] { 0x0c });
		final String si  = new String(new byte[] { 0x0f });
		final String so  = new String(new byte[] { 0x0e });
		final String esc = new String(new byte[] { 0x1b });

		final String FF_GLITCH  = "\u240c";
		final String SI_GLITCH  = "\u240f";
		final String SO_GLITCH  = "\u240e";
		final String ESC_GLITCH = "\u241b";
		

		StringBuilder sb = new StringBuilder();
		sb.append("3 11");

        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        for (int i = 0; i < screens.size(); i++)
        {
        	Screen scr = screens.get(i);

        	String scrData = scr.getData();

        	scrData = scrData.replaceAll(FF_GLITCH,  ff);
        	scrData = scrData.replaceAll(SI_GLITCH,  si);
        	scrData = scrData.replaceAll(SO_GLITCH,  so);
        	scrData = scrData.replaceAll(ESC_GLITCH, esc);

	        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " scrData " + scrData);

        	// sb.append(x1c + scr.getNumber() + scrData + si);
        	sb.append(x1c + scr.getNumber() + scrData);

            this.lastNumberSend = scr.getNumber();
        	// Log.staticPrintln( scr.toString() );
        }

		return sb.toString();
	}

	@Override
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
    	try 
		{
			List<Screen> screens = DB.exec(db -> new ScreenManager(db).getByConfigId(MAX_SCREENS_BY_CUSTOMIZATION_MSG, configId, lastNumber) );       
	        return createCustomizationMsg(screens);
		} 
		catch (Exception e) 
		{
			Log.printStackTrace(e);
		}
		return null;
	}

	@Override
	public String getLastKey(String configId)
	{
		try 
		{
		    Screen screen = DB.exec(db -> new ScreenManager(db).getByConfigIdLast(configId) );
		    return screen.getNumber();
		} 
		catch (Exception e) 
		{
		    Log.printStackTrace(e);
		}
		return null;
	}

	@Override
	public String getLastKeySend()
	{
		return this.lastNumberSend;
	}

}
