package org.jpos.atmc.ndc.Customizarion;

public enum NDCCustomizarionSections 
{
	
	SCREENS("SCREENS"), 
	STATES("STATES"), 
	FITS("FITS"),
	KEY_CHANGE("KEY_CHANGE"),
	CONFIGID("CONFIGID"),
	LAST("LAST");

	private final String description;
	private static final NDCCustomizarionSections sections[] = NDCCustomizarionSections.values();

	// private enum constructor
	private NDCCustomizarionSections(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}

    public static NDCCustomizarionSections next(NDCCustomizarionSections acs)
    {
    	return sections[ acs.ordinal() + 1 ];
    }
	

}
