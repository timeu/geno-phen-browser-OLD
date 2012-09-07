package com.gmi.nordborglab.browser.client;


import at.gmi.nordborglab.widgets.geochart.client.GeoChart;

import com.gmi.nordborglab.browser.client.gin.DefaultPlace;
import com.gmi.nordborglab.browser.shared.proxy.AppDataProxy;
import com.gmi.nordborglab.browser.shared.service.CustomRequestFactory;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class ClientPlaceManager extends PlaceManagerImpl {
	
	private final PlaceRequest defaultPlaceRequest;
	private final CurrentUser currentUser;
	private final CustomRequestFactory rf;
	
	@Inject
	public ClientPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, 
			@DefaultPlace String defaultNameToken,CurrentUser currentUser, CustomRequestFactory rf) {
		super(eventBus, tokenFormatter);
		this.defaultPlaceRequest = new PlaceRequest(defaultNameToken);
		this.currentUser = currentUser;
		this.rf = rf;
	}

	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}

	@Override
	public void revealCurrentPlace() {
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			
			@Override
			public void run() {
				
			}
		}, CoreChart.PACKAGE, MotionChart.PACKAGE,GeoChart.PACKAGE);
		
		if (currentUser.getAppData() == null) {
			rf.helperRequest().getAppData().fire(new Receiver<AppDataProxy>() {

				@Override
				public void onSuccess(AppDataProxy response) {
					currentUser.setAppData(response);
					ClientPlaceManager.super.revealCurrentPlace();
				}
			});
		}
		else { 
			ClientPlaceManager.super.revealCurrentPlace();
		}
	}
}
