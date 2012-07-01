package com.gmi.nordborglab.browser.shared.service;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface CustomRequestFactory extends RequestFactory{
	
	ExperimentRequest experimentRequest();
	PhenotypeRequest  phenotypeRequest();
	HelperRequest helperRequest();

}
