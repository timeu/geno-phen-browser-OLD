package com.gmi.nordborglab.browser.shared.service;

import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.server.service.TraitUomService;
import com.gmi.nordborglab.browser.shared.proxy.PhenotypePageProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=TraitUomService.class,locator=SpringServiceLocator.class)
public interface PhenotypeRequest extends RequestContext{
	Request<PhenotypePageProxy> findPhenotypesByExperiment(Long id,int start,int size);
}
