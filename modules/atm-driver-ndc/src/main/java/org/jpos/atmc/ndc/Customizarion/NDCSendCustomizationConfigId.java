package org.jpos.atmc.ndc.Customizarion;

import org.jpos.atmc.model.ATM;

public class NDCSendCustomizationConfigId implements NDCSendCustomization 
{
	private String lastConfigIdSend = "0000";

	@Override
	public String getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
		final String x1c = new String(new byte[] { 0x1c });

		StringBuilder sb = new StringBuilder();
		sb.append("3 16");
		sb.append(x1c + atm.getConfigId());
		return sb.toString();
	}

	@Override
	public String getLastKeySend() 
	{
		return this.lastConfigIdSend;
	}

	@Override
	public String getLastKey(String configId) 
	{
		return this.lastConfigIdSend;
	}


}
