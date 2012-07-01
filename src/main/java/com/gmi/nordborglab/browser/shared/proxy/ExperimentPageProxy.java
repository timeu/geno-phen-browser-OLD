package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.pages.ExperimentPage;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(ExperimentPage.class)
public interface ExperimentPageProxy extends ValueProxy{
	List<ExperimentProxy> getContent();
	int getNumber();
	long getTotalElements();
	int getTotalPages();
}
