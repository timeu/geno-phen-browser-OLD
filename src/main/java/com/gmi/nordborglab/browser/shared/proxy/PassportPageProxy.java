package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.pages.PassportPage;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(PassportPage.class)
public interface PassportPageProxy extends ValueProxy {
	List<PassportProxy> getContent();
	int getNumber();
	long getTotalElements();
	int getTotalPages();
}
