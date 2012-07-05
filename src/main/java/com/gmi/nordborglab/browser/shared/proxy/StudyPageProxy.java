package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.pages.StudyPage;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(StudyPage.class)
public interface StudyPageProxy extends ValueProxy {
	List<StudyProxy> getContent();
	int getNumber();
	long getTotalElements();
	int getTotalPages();
}
