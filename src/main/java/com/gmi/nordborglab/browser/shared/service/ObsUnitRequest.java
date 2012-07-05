package com.gmi.nordborglab.browser.shared.service;

import com.gmi.nordborglab.browser.server.service.ObsUnitService;
import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.shared.proxy.ObsUnitPageProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=ObsUnitService.class,locator=SpringServiceLocator.class)
public interface ObsUnitRequest extends RequestContext {
	Request<ObsUnitPageProxy> findObsUnits(Long id,int start,int size);

}
