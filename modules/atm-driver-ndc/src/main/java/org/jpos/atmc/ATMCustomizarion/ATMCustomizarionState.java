package org.jpos.atmc.ATMCustomizarion;

public class ATMCustomizarionState 
{
	private String LastKey;
	private ATMCustomizarionSections customizarionSection;

	public ATMCustomizarionState() 
	{
		this.customizarionSection = ATMCustomizarionSections.SCREENS;
		this.LastKey = "";
	}

}
