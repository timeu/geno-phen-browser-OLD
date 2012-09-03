package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.security.CustomAcl;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=CustomAcl.class)
public interface CustomAclProxy extends ValueProxy {

	public List<AccessControlEntryProxy> getEntries();
	public boolean getIsEntriesInheriting();
}
