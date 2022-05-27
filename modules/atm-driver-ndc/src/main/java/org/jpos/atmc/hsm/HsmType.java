package org.jpos.atmc.hsm;

public enum HsmType {
    THALES,
    ATALLA,
    IBM4758,
    FUTUREX;

    public static HsmType getCurrent()
    {
    	return THALES;
    }
}
