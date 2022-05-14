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
package org.jfrd.webapp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

import javax.ws.rs.core.UriInfo;

import org.jfrd.jwt.util.TokenUtils;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

import org.jpos.qi.QI;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.VerticalLayout;

public class GenericIframeView extends VerticalLayout implements View, Configurable 
{
	private static final long serialVersionUID = 1L;

	private Configuration cfg;
    private String baseURL;
    private String src;

	public GenericIframeView() 
	{
		super();

		setWidth("100%");
		setHeight("100%");
	}

	@Override
	public void setConfiguration(Configuration cfg) throws ConfigurationException
	{
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
        this.cfg = cfg;
        baseURL  = cfg.get("baseURL", "");
        src      = cfg.get("src");

        String user = QI.getQI().getUser().getNick();
        String token = TokenUtils.issueToken(user);

		String url = baseURL + "/" + src;

		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " token " + token );
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " user " + user );
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " baseURL " + baseURL );
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " src " + src );
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " url " + url );

    	String str = null;
        Path srcPath = Path.of(src);
        try
        {
            str = Files.readString(srcPath);
        }
        catch (IOException e)
        {
			e.printStackTrace(Log.out);
        }

        if (str != null)
        {
        	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " str " + str );
            str = str.replaceAll("qwerty-dummy-jwt", token);
        	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " str " + str );
        }
        else
        	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );

        final String strFinal = str;
        StreamResource.StreamSource source = new StreamResource.StreamSource() 
        { 
			private static final long serialVersionUID = 1L;

			public InputStream getStream() 
        	{
	        	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " strFinal " + strFinal );
        		return new ByteArrayInputStream(strFinal.getBytes());
        	}        
        };

        SecureRandom random = new SecureRandom();
	    String htmlName = new BigInteger(20, random).toString(20) + ".html";        

	    StreamResource streamResource = new StreamResource(source, htmlName);
	    streamResource.setCacheTime(0); 

	    BrowserFrame bf = new BrowserFrame(null, streamResource);

        setMargin(new MarginInfo(false, false, false, false));
        setSpacing(false);
		bf.setWidth("100%");
		bf.setHeight("100%");
		addComponent(bf);
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
    }
}
