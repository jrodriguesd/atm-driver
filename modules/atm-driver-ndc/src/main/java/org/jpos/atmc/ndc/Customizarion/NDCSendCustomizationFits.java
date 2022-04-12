package org.jpos.atmc.ndc.Customizarion;

import java.util.List;

import org.jpos.atmc.dao.FitManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.Fit;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;

public class NDCSendCustomizationFits implements NDCSendCustomization  
{
	private String lastNumberSend;

	private static final int MAX_FITS_BY_CUSTOMIZATION_MSG = 136;

	private String createCustomizationMsg(List<Fit> fits) 
	{
		final String x1c = new String(new byte[] { 0x1c });

		StringBuilder sb = new StringBuilder();
		sb.append("3 15");
        for (int i = 0; i < fits.size(); i++)
        {
        	Fit fit = fits.get(i);

        	sb.append(x1c + fit.getNumber() + "000" );

        	sb.append( Util.hex2dec( fit.getBinPrefix() ) );
        	sb.append( Util.hex2dec( fit.getIndirectstateidx() ) );
        	sb.append( Util.hex2dec( fit.getAlgoidx() ) );
        	sb.append( Util.hex2dec( fit.getMaxpinlen() ) );
        	sb.append( Util.hex2dec( fit.getLocalpinchecklen() ) );
        	sb.append( Util.hex2dec( fit.getPinpad() ) );
        	sb.append( Util.hex2dec( fit.getPanlocidx() ) );
        	sb.append( Util.hex2dec( fit.getPanlen() ) );
        	sb.append( Util.hex2dec( fit.getPanpad() ) );
        	sb.append( Util.hex2dec( fit.getPinretrycount() ) );
        	sb.append( Util.hex2dec( fit.getPinoffsetidx() ) );
        	sb.append( Util.hex2dec( fit.getDecimaltab() ) );
        	sb.append( Util.hex2dec( fit.getEncpinkey() ) );
        	sb.append( Util.hex2dec( fit.getIdxrefpoints() ) );
        	sb.append( Util.hex2dec( fit.getLangcodeidx() ) );

        	this.lastNumberSend = fit.getNumber();
        }
		return sb.toString();
	}

	@Override
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
    	try 
		{
			List<Fit> fits = DB.exec(db -> new FitManager(db).getByConfigId(MAX_FITS_BY_CUSTOMIZATION_MSG, configId, lastNumber) );       
	        return createCustomizationMsg(fits);
		} 
		catch (Exception e) 
		{
			Log.printStackTrace(e);
		}
		return null;
	}

	@Override
	public String getLastKeySend() {
		return this.lastNumberSend;
	}

	@Override
	public String getLastKey(String configId) {
		try 
		{
		    Fit fit =  DB.exec(db -> new FitManager(db).getByConfigIdLast(configId) );
		    return fit.getNumber();
		} 
		catch (Exception e) 
		{
		    Log.printStackTrace(e);
		}
		return null;
	}

}
