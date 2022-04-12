package org.jpos.atmc.ndc.Customizarion;

import org.jpos.atmc.model.ATM;

public interface NDCSendCustomization     
{
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber); 
	public String  getLastKeySend();
	public String  getLastKey(String configId);
}
