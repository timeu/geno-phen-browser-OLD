package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.pages.TraitUomPage;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(TraitUomPage.class)
public interface PhenotypePageProxy extends ValueProxy{
	List<PhenotypeProxy> getContent();
	int getNumber();
	long getTotalElements();
	int getTotalPages();
}
