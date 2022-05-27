package org.jpos.atmc.hsm;

public class HsmFactory {

	public static HSM getInstance(HsmType hsmType)
	{
		switch (hsmType)
		{
	        case THALES:
	    	    return new HsmThales();
		    default:
			    break; 
		}
		return null;
	}

}
