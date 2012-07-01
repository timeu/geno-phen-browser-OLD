package com.gmi.nordborglab.browser.client;


import com.gmi.nordborglab.browser.client.gin.DefaultPlace;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class ClientPlaceManager extends PlaceManagerImpl {
	
	private final PlaceRequest defaultPlaceRequest;

	@Inject
	public ClientPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken) {
		super(eventBus, tokenFormatter);
		this.defaultPlaceRequest = new PlaceRequest(defaultNameToken);
	}

	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}
	
	

}
