package com.gmi.nordborglab.browser.shared.service;

import java.util.List;

import com.gmi.nordborglab.browser.server.service.HelperService;
import com.gmi.nordborglab.browser.server.service.SpringServiceLocator;
import com.gmi.nordborglab.browser.shared.proxy.BreadcrumbItemProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value=HelperService.class,locator=SpringServiceLocator.class)
public interface HelperRequest extends RequestContext{

	Request<List<BreadcrumbItemProxy>> getBreadcrumbs(Long id,String object);
}
