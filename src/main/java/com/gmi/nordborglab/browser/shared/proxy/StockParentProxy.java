package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.germplasm.StockParent;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=StockParent.class, locator=SpringEntitiyLocator.class)
public interface StockParentProxy extends EntityProxy {
	public Long getId();
	
	public StockProxy getParent();
	public void setParent(StockProxy parent);
	
	public StockProxy getChild();
	public void setChild(StockProxy child);
	
	public String getRole();
	public void setRole(String role);
	
	public Integer getRecurrent();
	public void setRecurrent(Integer recurrent);

}
