package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=Experiment.class, locator=SpringEntitiyLocator.class)
public interface ExperimentProxy extends EntityProxy{
	
	Long getId();
	
	AclExperimentIdentityProxy getAcl();
	
	//Set<ObsUnitProxy> getObsUnits();
	
	
	String getName();
	void setName(String name);
	
	String getDesign();
	void setDesign(String design);
	
	void setOriginator(String originator);
	String getOriginator();
	
	void setComments(String comments);
	String getComments();
	
	boolean isOwner();
	
	AccessControlEntryProxy getUserPermission();
	
	int getNumberOfPhenotypes();
}
