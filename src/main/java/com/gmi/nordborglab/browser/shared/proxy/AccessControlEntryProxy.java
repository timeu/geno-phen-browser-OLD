package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.security.CustomAccessControlEntry;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=CustomAccessControlEntry.class)
public interface AccessControlEntryProxy extends ValueProxy {
	
	public static final int READ = 1 << 0;
	 public static final int WRITE = 1 << 1;
	 public static final int CREATE = 1 << 2;
	 public static final int DELETE = 1 << 3;
	 public static final int ADMINISTRATION = 1 << 4;
	
	int getMask();
	boolean isGranting();
	
}
