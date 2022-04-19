/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * atm-driver is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jpos.atmc.ndc.Customizarion;

import java.util.List;

import org.jpos.atmc.dao.FitManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.Fit;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;

public class GetFits implements GetSection  
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
			e.printStackTrace(Log.out);
		}
		return null;
	}

	@Override
	public String getLastKeySend() {
		return this.lastNumberSend;
	}

	@Override
	public String getLastKey(ATM atm, String configId)
	{
		try 
		{
		    Fit fit =  DB.exec(db -> new FitManager(db).getByConfigIdLast(configId) );
		    return fit.getNumber();
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
		}
		return null;
	}

}
