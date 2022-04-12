package org.jpos.atmc.ndc.Customizarion;

import java.util.List;

import org.jpos.atmc.dao.StateManager;
import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.State;
import org.jpos.atmc.util.Log;
import org.jpos.ee.DB;

public class NDCSendCustomizationStates implements NDCSendCustomization 
{
	private String lastNumberSend;

	private static final int MAX_STATES_BY_CUSTOMIZATION_MSG = 136;

	public String createCustomizationMsg(List<State> states) 
	{
		final String x1c = new String(new byte[] { 0x1c });

		StringBuilder sb = new StringBuilder();
		sb.append("3 12");
        for (int i = 0; i < states.size(); i++)
        {
        	State sta = states.get(i);
        	sb.append(x1c + sta.getNumber() + sta.getType() + 
	                        sta.getS1() + sta.getS2() +
	                        sta.getS3() + sta.getS4() +
			                sta.getS5() + sta.getS6() +
	                        sta.getS7() + sta.getS8() );

        	this.lastNumberSend = sta.getNumber();
        }

		return sb.toString();
	}
	
	
	@Override
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
    	try 
		{
			List<State> states = DB.exec(db -> new StateManager(db).getByConfigId(MAX_STATES_BY_CUSTOMIZATION_MSG, configId, lastNumber) );       
	        return createCustomizationMsg(states);
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

	@Override
	public String getLastKey(String configId) 
	{
		try 
		{
		    State state = DB.exec(db -> new StateManager(db).getByConfigIdLast(configId) );
		    return state.getNumber();
		} 
		catch (Exception e) 
		{
		    Log.printStackTrace(e);
		}
		return null;
	}

}
