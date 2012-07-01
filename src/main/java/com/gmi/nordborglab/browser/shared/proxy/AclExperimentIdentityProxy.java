package com.gmi.nordborglab.browser.shared.proxy;

import java.util.Set;

import com.gmi.nordborglab.browser.server.domain.acl.AclExperimentIdentity;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=AclExperimentIdentity.class, locator=SpringEntitiyLocator.class)
public interface AclExperimentIdentityProxy extends EntityProxy{
	Long getId();
	
	Set<AclExperimentEntryProxy> getEntries();
}
