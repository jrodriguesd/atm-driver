package org.jpos.atmc;

import java.io.Serializable;

import org.jpos.atmc.CreateISOMsg.HeaderStrategy;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.FSDISOMsg;
import org.jpos.space.LocalSpace;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.util.FSDMsg;

public class CreateHSMMsg  implements AbortParticipant, Configurable 
{
    private static final long DEFAULT_TIMEOUT = 30000L;
    private static final long DEFAULT_WAIT_TIMEOUT = 1000L;

    private long timeout;
    private long waitTimeout;
    private String requestName;
    private String responseName;
    private String destination;
    private boolean continuations;
    private Configuration cfg;
    private boolean ignoreUnreachable;
    private boolean checkConnected = true;
    private boolean remote = false;

	@Override
	public int prepare(long id, Serializable context) {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        Context ctx = (Context) context;
        
		FSDMsg fsdMsgOut = new FSDMsg("file:cfg/hsm/hsm-");
		fsdMsgOut.set("0", "1234");
		fsdMsgOut.set("command", "NC");
		
		FSDISOMsg msgOut = new FSDISOMsg(fsdMsgOut);
		
		/* Prepare Context to call QueryHost (Begin) */
        String originalDestination = ctx.getString(destination); 
        ctx.put("orig-destination", originalDestination, remote);

        ctx.put(requestName, msgOut, remote);
        ctx.put(destination, "jPOS-HSM", remote);
		/* Prepare Context to call QueryHost (End)   */

        return PREPARED | READONLY;
	}

	@Override
	public void setConfiguration(Configuration cfg) throws ConfigurationException {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        this.cfg = cfg;
        timeout = cfg.getLong ("timeout", DEFAULT_TIMEOUT);
        waitTimeout = cfg.getLong ("wait-timeout", DEFAULT_WAIT_TIMEOUT);
        requestName = cfg.get ("request", ContextConstants.REQUEST.toString());
        responseName = cfg.get ("response", ContextConstants.RESPONSE.toString());
        destination = cfg.get ("destination", ContextConstants.DESTINATION.toString());
        continuations = cfg.getBoolean("continuations", true);
        ignoreUnreachable = cfg.getBoolean("ignore-host-unreachable", false);
        checkConnected = cfg.getBoolean("check-connected", checkConnected);
	}


}
