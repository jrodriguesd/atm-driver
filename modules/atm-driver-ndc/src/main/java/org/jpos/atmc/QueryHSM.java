package org.jpos.atmc;

import java.io.Serializable;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.*;
import org.jpos.rc.CMF;
import org.jpos.rc.Result;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.Caller;
import org.jpos.util.Chronometer;
import org.jpos.util.NameRegistrar;
import org.jpos.transaction.Context;

@SuppressWarnings("unused")
public class QueryHSM implements TransactionParticipant, ISOResponseListener, Configurable {
    private static final long DEFAULT_TIMEOUT = 30000L;
    private static final long DEFAULT_WAIT_TIMEOUT = 1000L;

    private long timeout;
    private long waitTimeout;
    private String requestName;
    private String responseName;
    private String destination;
    private boolean continuations;
    private Configuration cfg;
    private String request;
    private boolean ignoreUnreachable;
    private boolean checkConnected= true;

    public QueryHSM () {
        super();
    }
    public int prepare (long id, Serializable ser)  {
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        Context ctx = (Context) ser;

        Result result = ctx.getResult();
        String ds = ctx.getString(destination);
        ds = "jPOS-HSM";
        if (ds == null) {
            return result.fail(
              CMF.MISCONFIGURED_ENDPOINT, Caller.info(), "'%s' not present in Context", destination
            ).FAIL();
        }
        String muxName = cfg.get ("mux." + ds , "mux." + ds);
        Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " muxName " + muxName );
        MUX mux =  NameRegistrar.getIfExists (muxName);
        if (mux == null)
            return result.fail(CMF.MISCONFIGURED_ENDPOINT, Caller.info(), "MUX '%s' not found", muxName).FAIL();

        ISOMsg m = ctx.get ("hsm-request");
        if (m == null)
            return result.fail(CMF.INVALID_REQUEST, Caller.info(), "'%s' is null", requestName).FAIL();

        Chronometer chronometer = new Chronometer();
        if (isConnected(mux)) {
            long t = Math.max(timeout - chronometer.elapsed(), 1000L); // give at least a second to catch a response
            try {
                if (continuations) {
                    mux.request(m, t, this, ctx);
                    return PREPARED | READONLY | PAUSE | NO_JOIN;
                } else {
                    ISOMsg resp = mux.request(m, t);
                    if (resp != null) {
                        ctx.put(responseName, resp);
                        return PREPARED | READONLY | NO_JOIN;
                    } else if (ignoreUnreachable) {
                        ctx.log(String.format ("MUX '%s' no response", muxName));
                    } else {
                        return result.fail(CMF.HOST_UNREACHABLE, Caller.info(), "'%s' does not respond", muxName).FAIL();
                    }
                }
            } catch (ISOException e) {
                return result.fail(CMF.SYSTEM_ERROR, Caller.info(), e.getMessage()).FAIL();
            }
        } else if (ignoreUnreachable) {
            ctx.log(String.format ("MUX '%s' not connected", muxName));
        } else {
            return result.fail(CMF.HOST_UNREACHABLE, Caller.info(), "'%s' is not connected", muxName).FAIL();
        }
        return PREPARED | NO_JOIN | READONLY;
    }

    public void responseReceived (ISOMsg resp, Object handBack) {
        Context ctx = (Context) handBack;
        ctx.put (responseName, resp);
        ctx.resume();
    }
    public void expired (Object handBack) {
        Context ctx = (Context) handBack;
        String ds = ctx.getString(destination);
        String muxName = cfg.get ("mux." + ds , "mux." + ds);
        ctx.getResult().fail(CMF.HOST_UNREACHABLE, Caller.info(), "'%s' does not respond", muxName).FAIL();
        ctx.resume();
    }

    public void setConfiguration (Configuration cfg) throws ConfigurationException {
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

    protected boolean isConnected (MUX mux) {
        if (!checkConnected || mux.isConnected())
            return true;
        long timeout = System.currentTimeMillis() + waitTimeout;
        while (System.currentTimeMillis() < timeout) {
            if (mux.isConnected())
                return true;
            ISOUtil.sleep (500);
        }
        return false;
    }
}