package org.jpos.atmc;

public class ATMRunState
{
	public ATMCustomization atmCustomization;
	public enum ATMState 
	{
		NONE,
        OFFLINE,
        CONECTED,
        GOING_INTO_SERVICE,
        IN_SERVICE,
		SENDING_CONFIGURATION_INFORMATION,
    };
	public ATMState atmState = ATMState.NONE;
}
