package com.gmi.nordborglab.browser.shared.proxy;

import org.springframework.security.acls.model.Permission;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=Permission.class)
public interface PermissionProxy extends ValueProxy {
	
	
	
	int getMask();
}
