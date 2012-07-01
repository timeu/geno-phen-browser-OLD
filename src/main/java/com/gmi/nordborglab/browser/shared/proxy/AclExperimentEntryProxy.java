package com.gmi.nordborglab.browser.shared.proxy;


import com.gmi.nordborglab.browser.server.domain.acl.AclExperimentEntry;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=AclExperimentEntry.class, locator=SpringEntitiyLocator.class)
public interface AclExperimentEntryProxy extends EntityProxy{
	
	    
	Long getId();
	
	Integer getMask();
	
	AclSidProxy getSid();
}
