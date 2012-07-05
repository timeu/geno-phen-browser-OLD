package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.cdv.StudyProtocol;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=StudyProtocol.class, locator=SpringEntitiyLocator.class)
public interface StudyProtocolProxy extends EntityProxy {

	public Long getId();
	
	public String getAnalysisMethod();
	public void setAnalysisMethod(String analysisMethod);
}
