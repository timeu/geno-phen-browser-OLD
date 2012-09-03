package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.acl.AppUser;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyFor(value=AppUser.class)
public interface AppUserProxy extends ValueProxy{
	
	//String getUsername();
	String getFirstname();
	void setFirstname(String firstname);
	String getLastname();
	void setLastname(String lastname);
	String getEmail();
	void setEmail(String email);
	List<AuthorityProxy> getAuthorities();
	void setAuthorities(List<AuthorityProxy> authorities);
}
