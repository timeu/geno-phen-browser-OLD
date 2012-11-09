package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.germplasm.Sampstat;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=Sampstat.class, locator=SpringEntitiyLocator.class)
public interface SampStatProxy extends EntityProxy {
	public Long getId();
	public String getSampstat();
	public String getGermplasmType();
}
