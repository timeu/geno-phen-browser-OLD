package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.acl.Authority;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=Authority.class)
public interface AuthorityProxy extends ValueProxy{
	String getAuthority();
	void setAuthority(String authority);
}
