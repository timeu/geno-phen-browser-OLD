package com.gmi.nordborglab.browser.shared.service;

import java.util.List;

import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.server.service.TaxonomyService;
import com.gmi.nordborglab.browser.shared.proxy.TaxonomyProxy;
import com.gmi.nordborglab.browser.shared.proxy.TaxonomyStatsProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=TaxonomyService.class,locator=SpringServiceLocator.class)
public interface TaxonomyRequest extends RequestContext {
	
	Request<List<TaxonomyProxy>> findAll();

	Request<TaxonomyProxy> findOne(Long id);

	Request<TaxonomyProxy> save(TaxonomyProxy taxonomy);

	Request<TaxonomyStatsProxy> findStats(Long taxonomyId);

}
