package org.jpos.atmc.ATMCustomizarion;

import java.io.IOException;

import org.jpos.atmc.model.ATM;

public interface ATMSendCustomization     
{
	public String  getNextCustomizationMsg(ATM atm, String configId, String lastNumber); 
	public String  getLastKeySend();
	public String  getLastKey(String configId);
}
