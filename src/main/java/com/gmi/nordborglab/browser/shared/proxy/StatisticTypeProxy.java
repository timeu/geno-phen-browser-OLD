package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.phenotype.StatisticType;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=StatisticType.class, locator=SpringEntitiyLocator.class)
public interface StatisticTypeProxy extends EntityProxy {

	public Long getId();

	public String getStatType();
	public void setStatType(String statType);
}
