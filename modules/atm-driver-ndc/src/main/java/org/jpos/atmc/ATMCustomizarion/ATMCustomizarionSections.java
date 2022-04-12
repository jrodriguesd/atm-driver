package org.jpos.atmc.ATMCustomizarion;

public enum ATMCustomizarionSections 
{
	
	SCREENS("SCREENS"), 
	STATES("STATES"), 
	FITS("FITS"),
	KEY_CHANGE("KEY_CHANGE"),
	CONFIGID("CONFIGID"),
	LAST("LAST");

	private final String description;
	private static final ATMCustomizarionSections sections[] = ATMCustomizarionSections.values();

	// private enum constructor
	private ATMCustomizarionSections(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}

    public static ATMCustomizarionSections next(ATMCustomizarionSections acs)
    {
    	return sections[ acs.ordinal() + 1 ];
    }
	

}
