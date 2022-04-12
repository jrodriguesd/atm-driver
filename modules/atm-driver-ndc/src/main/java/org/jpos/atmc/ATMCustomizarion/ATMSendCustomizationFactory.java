package org.jpos.atmc.ATMCustomizarion;

public class ATMSendCustomizationFactory 
{
	public static ATMSendCustomization getInstance(ATMCustomizarionSections type)
	{
		switch (type)
		{
		    case SCREENS:
		    	return new ATMSendCustomizationScreens();
		    case STATES:
		    	return new ATMSendCustomizationStates();
		    case FITS:
		    	return new ATMSendCustomizationFits();
		    case KEY_CHANGE:
		    	return new ATMSendCustomizationKeyChange();
		    case CONFIGID:
		    	return new ATMSendCustomizationConfigId();
		    default:
			    break; 
		}
		return null;
	}

}
