package com.gmi.nordborglab.browser.client;

import com.gmi.nordborglab.browser.shared.proxy.AccessControlEntryProxy;
import com.gmi.nordborglab.browser.shared.proxy.AppUserProxy;


public class CurrentUser{
	
	private AppUserProxy appUser = null;
	
	public CurrentUser() {}
	
	public void setAppUser(AppUserProxy appuser) {
		this.appUser = appuser;
	}
	
	public boolean isLoggedIn() {
		return appUser != null;
	}
	
	public AppUserProxy getAppUser()  {
		return appUser;
	}
	
	public int getPermissionMask(AccessControlEntryProxy ace) {
		int permission = 0;
		if (isLoggedIn()) {
			if (ace != null ) {
				permission = ace.getMask();
			}
		}
		return permission;
	}
}
