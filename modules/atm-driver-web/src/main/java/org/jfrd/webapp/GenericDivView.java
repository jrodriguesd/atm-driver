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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Dependency;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

import java.net.*;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

public class GenericDivView extends Label implements View, Configurable
{
	private static final long serialVersionUID = 1L;

	private Configuration cfg;

    public GenericDivView()
    {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
    	
        setContentMode(ContentMode.HTML);
        setSizeFull();
        setStyleName("divview-style");      // Some basic style
        setId(uuidStr);                     // the default DOM id, may be changed by configuration

    }

    public void setConfiguration(Configuration cfg) throws ConfigurationException
    {
        this.cfg= cfg;

        setId( cfg.get("id", getId() ) );  // we may change the DOM id, or leave it as the default (FQCN)

        String baseURL = cfg.get("baseURL");
        String src     = cfg.get("src");

    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " baseURL " + baseURL );
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " src " + src );
		
        try
        {
			String url = baseURL + "/" + src;
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader fr = new BufferedReader(
                                    new InputStreamReader(
                                        connection.getInputStream()));
		    	
			
			
            String inner = fr.lines().collect(Collectors.joining("\n"));
            
            Date currentDate = GregorianCalendar.getInstance().getTime();
            String output = new DateTime( currentDate ).toString("yyyy-MM-dd HH:mm:ss");
            
            String post = "<script>\n"
            		      + "$(function()\n"
            		      + "{\n"
            		      + "    console.log(\" Prueba GenericDivViewer JFRD \");\n"
            		      + "    $(\"#atmconfigs_wrap\").trigger(\"change\");\n"
            		      + "})\n"
            		      + "atmconfigs_wrap.dispatchEvent(new CustomEvent(\"hello\", {\r\n"
            		      + "    detail: { name: \"John\" }\r\n"
            		      + "  }));"
                          + "</script>\n";
            
            setValue(inner);
            markAsDirtyRecursive();          // needed?
            // Page.getCurrent().getJavaScript().execute("alert('JFRD setConfiguration')");
        }
        catch (IOException ex)
        {
            String msg= "Can't read src file '"+src+"' for "+getClass().getSimpleName()+" '"+getId()+"'";

            Notification n = new Notification(msg, Notification.Type.ERROR_MESSAGE);
            n.setHtmlContentAllowed(false);
            n.setPosition(Position.TOP_CENTER);
            n.show(Page.getCurrent());

            throw new ConfigurationException(msg, ex);
        }
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
        attach();       // needed?

        // add dependencies
        Page page = getUI().getPage();

        String[] styles= cfg.getAll("css");
        for (String s : styles)
            page.addDependency(new Dependency(Dependency.Type.STYLESHEET, s));

        String[] scripts= cfg.getAll("script");
        for (String s : scripts)
            page.addDependency(new Dependency(Dependency.Type.JAVASCRIPT, s));

        // BBB This could be a function externalized in a file, and added as dependency with a JavaScript annotation
        // Is it necessary to do all this with access() in order to run it?
        // The idea is to evaluate/execute all `<script>` elements that descend from this element
        //(that has just been dynamically loaded with src content, so the scripts don't automaticly execute)
        getUI().access(()->{
            JavaScript js = JavaScript.getCurrent();
            String jsstr=
                "var thisEl= document.getElementById('"+getId()+"');" +
                "if (thisEl != null) {" +
                "   var scripts= thisEl.getElementsByTagName('script');"+
                "   for (var i= 0; i < scripts.length; i++)"+
                "       try { eval(scripts[i].text); }"+
                "       catch(e) { alert(e); }"+
                "}";

            js.execute(jsstr);
            getUI().push();
        });
    }
    
    @Override
    public void attach() 
    {
        super.attach();
        // Page.getCurrent().getJavaScript().execute("alert('JFRD attach')");
        // Page.getCurrent().getJavaScript().execute("$(\"#atmconfigs_wrap\").trigger(\"load\");");
    }
}
