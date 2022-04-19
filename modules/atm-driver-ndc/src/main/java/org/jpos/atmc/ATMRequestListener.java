/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * atm-driver is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jpos.atmc;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.Sequencer;
import org.jpos.core.VolatileSequencer;

import org.jpos.iso.*;

import org.jpos.util.FSDMsg;
import org.jpos.util.Log;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;
import org.jpos.iso.packager.ISO87APackager;

import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;

import org.jpos.space.LocalSpace;
import org.jpos.space.SpaceSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;

import org.jpos.ee.DB;

import org.jpos.atmc.model.ATM;
import org.jpos.atmc.model.TrnDefinition;

import org.jpos.atmc.dao.ATMManager;
import org.jpos.atmc.dao.TrnDefinitionManager;
import org.jpos.atmc.util.FlatLogListener;
import org.jpos.atmc.util.Util;

public class ATMRequestListener extends org.jpos.util.Log implements ISORequestListener, Configurable
{
    long timeout;

    private final String name = "ATMRequestListener"; 
    private Space<String,Context> sp;
    private String queue;
    private String source;
    private String request;
    private String timestamp;
    private Map<String,String> additionalContextEntries = null;
    private boolean remote = false;

    public boolean process(ISOSource src, ISOMsg m)
	{
    	final Context ctx  = new Context ();
        if (remote)
            src = new SpaceSource((LocalSpace<String, Context>)sp, src, timeout);
        ctx.put (timestamp, new Date(), remote);
        ctx.put (source, src, remote);
        ctx.put (request, m, remote);
        if (additionalContextEntries != null) 
		{
            additionalContextEntries.entrySet().forEach
			(
                e -> ctx.put(e.getKey(), e.getValue(), remote)
            );
        }
        sp.out(queue, ctx, timeout);
        return true;
    }

    public void setConfiguration (Configuration cfg) throws ConfigurationException
    {
    	debug("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        timeout  = cfg.getLong ("timeout", 15000L);
        sp = (Space<String,Context>) SpaceFactory.getSpace (cfg.get ("space"));
        queue = cfg.get ("queue", null);
        if (queue == null)
            throw new ConfigurationException ("queue property not specified");
        source = cfg.get ("source", ContextConstants.SOURCE.toString());
        request = cfg.get ("request", ContextConstants.REQUEST.toString());
        timestamp = cfg.get ("timestamp", ContextConstants.TIMESTAMP.toString());
        remote = cfg.getBoolean("remote") || cfg.get("space").startsWith("rspace:");
        Map<String,String> m = new HashMap<>();
        cfg.keySet()
           .stream()
           .filter (s -> s.startsWith("ctx."))
           .forEach(s -> m.put(s.substring(4), cfg.get(s)));
        if (m.size() > 0)
            additionalContextEntries = m;
    }

    public String getName() 
	{ 
        return this.name; 
    }  	
}
