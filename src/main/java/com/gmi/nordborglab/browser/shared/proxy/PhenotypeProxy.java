package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=TraitUom.class, locator=SpringEntitiyLocator.class)
public interface PhenotypeProxy extends EntityProxy{

	Long getId();

	String getLocalTraitName();

	String getTraitProtocol();

	//String getTraitOntology();
	
	
}
