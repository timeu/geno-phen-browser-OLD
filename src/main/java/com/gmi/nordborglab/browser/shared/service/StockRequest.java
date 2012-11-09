package com.gmi.nordborglab.browser.shared.service;

import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.server.service.StockService;
import com.gmi.nordborglab.browser.shared.proxy.StockProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=StockService.class,locator=SpringServiceLocator.class)
public interface StockRequest extends RequestContext {

	Request<StockProxy> findOne(Long stockId);

}
