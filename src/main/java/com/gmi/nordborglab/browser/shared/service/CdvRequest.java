package com.gmi.nordborglab.browser.shared.service;

import com.gmi.nordborglab.browser.server.service.CdvService;
import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.shared.proxy.StudyPageProxy;
import com.gmi.nordborglab.browser.shared.proxy.StudyProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=CdvService.class,locator=SpringServiceLocator.class)
public interface CdvRequest extends RequestContext {
	Request<StudyPageProxy> findStudiesByPhenotypeId(Long id,int start,int size);

	Request<StudyProxy> findStudy(Long id);
}
