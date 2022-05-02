package org.jpos.atmc.ndc.Customizarion;

import org.jpos.atmc.model.ATM;

public class GetLUNO implements GetSection 
{
	private String lastNumberSend = "000";

	@Override
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber) 
	{
		final String x1c = new String(new byte[] { 0x1c });

		StringBuilder sb = new StringBuilder();

		/***********************************************/
		/* 45 Extended Encryption Key Change (MAC Key) */
		/***********************************************/
		sb.append("3 1A");
		
		sb.append(x1c + atm.getLuno());

		return sb.toString();
	}

	@Override
	public String getLastKeySend() 
	{
		return this.lastNumberSend;
	}

	@Override
	public String getLastKey(ATM atm, String configId) 
	{
		return this.lastNumberSend;
	}
}
