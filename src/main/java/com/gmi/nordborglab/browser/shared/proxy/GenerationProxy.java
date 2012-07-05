package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.germplasm.Generation;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=Generation.class, locator=SpringEntitiyLocator.class)
public interface GenerationProxy extends EntityProxy {

	public Long getId();
	
	public String getIcisId();
	public void setIcisId(String icisId);
	
	public String getComments();
	public void setComments(String comments);
	
	
	public Integer getSelfingNumber();
	public void setSelfingNumber(Integer selfingNumber);
	
	public Integer getSibbingNumber();
	public void setSibbingNumber(Integer sibbingNumber);
}
