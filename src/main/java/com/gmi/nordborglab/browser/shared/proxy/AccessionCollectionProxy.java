package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.germplasm.AccessionCollection;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=AccessionCollection.class, locator=SpringEntitiyLocator.class)
public interface AccessionCollectionProxy extends EntityProxy {

	public Long getId();
	
	public LocalityProxy getLocality();
	public void setLocality(LocalityProxy locality);
	
	public String getCollector();
	public void setCollector(String collector);
	
	public String getCollNumb();
	public void setCollNumb(String collNumb);
	
	public String getCollSrc();
	public void setCollSrc(String collsrc);
	
	public String getCollCode();
	public void setCollCode(String collcode);
	
	public String getCollDate();
	public void setCollDate(String collDate);
}
