package com.gmi.nordborglab.browser.client.manager;

import com.gmi.nordborglab.browser.shared.proxy.PhenotypePageProxy;
import com.gmi.nordborglab.browser.shared.service.CustomRequestFactory;
import com.gmi.nordborglab.browser.shared.service.PhenotypeRequest;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;

public class PhenotypeManager extends RequestFactoryManager<PhenotypeRequest> {

	@Inject
	public PhenotypeManager(CustomRequestFactory rf) {
		super(rf);
	}
	
	public void findAll(Receiver<PhenotypePageProxy> receiver,Long id,int start,int size) {
		rf.phenotypeRequest().findPhenotypesByExperiment(id, start, size).fire(receiver);
	}

	@Override
	public PhenotypeRequest getContext() {
		return rf.phenotypeRequest();
	}

}
