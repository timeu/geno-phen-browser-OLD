package com.gmi.nordborglab.browser.client.entrypoints;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gmi.nordborglab.browser.client.gin.ClientGinjector;
import com.gmi.nordborglab.browser.client.resources.MainResources;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class Browser implements EntryPoint {
	
	private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

	public void onModuleLoad() {
		DelayedBindRegistry.bind(ginjector);
		ginjector.getPlaceManager().revealCurrentPlace();
		MainResources mainResources = ginjector.getMainResources();
		mainResources.style().ensureInjected();
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			
			@Override
			public void onUncaughtException(Throwable e) {
				Logger logger = Logger.getLogger("uncaught");
				logger.log(Level.SEVERE, "Uncaught Exception", e);
			}
		});
	}

}
