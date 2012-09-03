package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=TraitUom.class, locator=SpringEntitiyLocator.class)
public interface PhenotypeProxy extends EntityProxy{

	Long getId();
	
	UnitOfMeasureProxy getUnitOfMeasure();
	
	void setUnitOfMeasure(UnitOfMeasureProxy unitOfMeasure);

	String getLocalTraitName();
	
	void setLocalTraitName(String localTraitName);

	String getTraitProtocol();
	void setTraitProtocol(String traitProtocol);

	String getToAccession();
	void setToAccession(String toAccession);
	
	String getEoAccession();
	void setEoAccession(String eoAccession);

	boolean isOwner();
	
	AccessControlEntryProxy getUserPermission();

	Long getNumberOfObsUnits();

	Long getNumberOfStudies();
	
}
