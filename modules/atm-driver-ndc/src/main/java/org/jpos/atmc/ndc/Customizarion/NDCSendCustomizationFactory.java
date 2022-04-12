package org.jpos.atmc.ndc.Customizarion;

public class NDCSendCustomizationFactory 
{
	public static NDCSendCustomization getInstance(NDCCustomizarionSections type)
	{
		switch (type)
		{
		    case SCREENS:
		    	return new NDCSendCustomizationScreens();
		    case STATES:
		    	return new NDCSendCustomizationStates();
		    case FITS:
		    	return new NDCSendCustomizationFits();
		    case KEY_CHANGE:
		    	return new NDCSendCustomizationKeyChange();
		    case CONFIGID:
		    	return new NDCSendCustomizationConfigId();
		    default:
			    break; 
		}
		return null;
	}

}
