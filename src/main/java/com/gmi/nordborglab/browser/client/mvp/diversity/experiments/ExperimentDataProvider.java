package com.gmi.nordborglab.browser.client.mvp.diversity.experiments;

import com.gmi.nordborglab.browser.client.manager.ExperimentManager;
import com.gmi.nordborglab.browser.shared.proxy.ExperimentPageProxy;
import com.gmi.nordborglab.browser.shared.proxy.ExperimentProxy;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class ExperimentDataProvider extends AsyncDataProvider<ExperimentProxy>{
	
	
	final ExperimentManager experimentManager;
	
	
	@Inject
	public ExperimentDataProvider(final ExperimentManager experimentManager) {
		super();
		this.experimentManager = experimentManager;
	}
	
	@Override
	protected void onRangeChanged(HasData<ExperimentProxy> display) {
		
	}
	
	

}
