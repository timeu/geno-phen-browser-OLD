package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.acl.AclSid;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=AclSid.class, locator=SpringEntitiyLocator.class)
public interface AclSidProxy extends EntityProxy {
	Long getId();
	boolean getPrincipal();
	String getSid();
}
