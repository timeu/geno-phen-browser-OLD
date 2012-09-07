package com.gmi.nordborglab.browser.shared.service;

import java.util.List;

import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.server.service.TraitService;
import com.gmi.nordborglab.browser.shared.proxy.TraitProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=TraitService.class,locator=SpringServiceLocator.class)
public interface TraitRequest extends RequestContext {
	
	Request<List<TraitProxy>> findAllTraitValues(Long phenotypeId,Long alleleAssayId,Long statisticTypeId);

}
