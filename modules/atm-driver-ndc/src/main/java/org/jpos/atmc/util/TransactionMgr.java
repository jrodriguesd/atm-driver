/*
 * https://arashmd.blogspot.com/2017/02/singleton-pattern.html
 */
package org.jpos.atmc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class TransactionMgr {
	private static TransactionMgr instance=new TransactionMgr();
	private java.sql.Driver driver=null;
	private java.util.HashMap<Long, java.sql.Connection> batches=new HashMap<Long,java.sql.Connection>();
	private final int maxTranInstance=16;
	private int runningTransaction=0;
	private long lastTranId=0;
	private final String connectionString="jdbc:postgresql://127.0.0.1:17001/_ds";
	private final Properties connectionPeroperties=new Properties();
//	private final String driverClassName="org.postgresql.Driver";
	private Object waitLock=new Object();

	private TransactionMgr(){
//		System.out.println("Connection Manager is initializing\nusing "+this.driverClassName+" as default driver...");
		try {
//			this.driver=(java.sql.Driver)Class.forName(driverClassName).newInstance();
			//setting default connection properties
			this.connectionPeroperties.put("user", "__arash_");
			this.connectionPeroperties.put("password", "one simple password!!!");
			System.out.println("Connection Manager has initialized successfully, ready for action!");
		} catch (Exception e) {
			System.err.println("Error! could not load the driver! "+e.getMessage());
			e.printStackTrace();
		}
	}
	public static TransactionMgr getInstance(){
		return TransactionMgr.instance;
	}

//	public synchronized void setDriver(String dName) throws Exception{
//		this.driver=(java.sql.Driver)Class.forName(dName).newInstance();
//	}

	public java.sql.Connection getANativeConnection() throws SQLException{
		return driver.connect(connectionString, connectionPeroperties);
	}

	public long beginTransaction() throws SQLException{
		synchronized (waitLock) {
			if(runningTransaction==maxTranInstance){
				try {waitLock.wait();} catch(InterruptedException e){e.printStackTrace();}
			}
			Connection c=driver.connect(connectionString, connectionPeroperties);
			c.setAutoCommit(false);
			batches.put(++lastTranId, c);
			runningTransaction++;
			return lastTranId;
		}
	}

	public int addToTransation(long tranId,String command) throws SQLException{
		if(!batches.containsKey(tranId)){return -2;}
		return batches.get(tranId).createStatement().executeUpdate(command);
	}

	public void commitTransaction(long tranId) throws SQLException{
		if(!batches.containsKey(tranId)){return;}
		batches.get(tranId).commit();
		removeTransaction(tranId);
	}

	public void rollbackTransaction(long tranId){
		if(!batches.containsKey(tranId)){return;}
		removeTransaction(tranId);
	}

	private void removeTransaction(long tranId){
		synchronized (waitLock) {
			if(!batches.containsKey(tranId)){return;}
			runningTransaction--;
			try {batches.remove(tranId).close();} catch (SQLException e) {e.printStackTrace();}
			try{waitLock.notify();}catch(Exception e){}
		}
	}
}
