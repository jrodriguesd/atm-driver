package org.jpos.atmc.hsm;

public interface HSM {
	public void echoTest();
	public String getTMKUnderTMK(String tmkOld, String tmkNew);
	public String getTPKUnderTMK(String TMK, String TPK);
	public String getTAKUnderTMK(String TAK, String TPK);
	public String translatePin(String tpk, String zpk, String pinBlock, String accountNumber);
	public String generateKey(KeyType keyType);
}
