package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.genotype.ScoringTechType;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=ScoringTechType.class, locator=SpringEntitiyLocator.class)
public interface ScoringTechTypeProxy extends EntityProxy {
	
	public Long getId();
	
	public String getScoringTechGroup();
	
	public void setScoringTechGroup(String scoringTechGroup);
	
	public String getScoringTechType();
	
	public void setScoringTechType(String scoringTechType);

}
