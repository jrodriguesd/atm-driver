package org.jfrd.webapp;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
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
        baseURL  = cfg.get("baseURL");
        src      = cfg.get("src");

		String url = baseURL + "/" + src;

		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " baseURL " + baseURL );
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " src " + src );
    	Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " url " + url );

    	BrowserFrame bf = new BrowserFrame("", new ExternalResource(url));

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
