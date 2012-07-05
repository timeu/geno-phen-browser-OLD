package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.genotype.PolyType;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=PolyType.class, locator=SpringEntitiyLocator.class)
public interface PolyTypeProxy extends EntityProxy {

	public Long getId();
	
	public String getPolyType();

	public void setPolyType(String polyType);
}
