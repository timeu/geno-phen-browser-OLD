package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.pages.ObsUnitPage;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(ObsUnitPage.class)
public interface ObsUnitPageProxy extends ValueProxy {
	List<ObsUnitProxy> getContent();
	int getNumber();
	long getTotalElements();
	int getTotalPages();
}
