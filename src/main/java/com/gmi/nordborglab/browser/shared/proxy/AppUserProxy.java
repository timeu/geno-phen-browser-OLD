package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;



public interface AppUserProxy {
	String getFirstname();
	void setFirstname(String firstname);
	String getLastname();
	void setLastname(String lastname);
	String getEmail();
	void setEmail(String email);
	List<AuthorityProxy> getAuthorities();
	void setAuthorities(List<AuthorityProxy> authorities);
}
