package com.gmi.nordborglab.browser.client;

import com.gmi.nordborglab.browser.shared.proxy.AccessControlEntryProxy;
import com.gmi.nordborglab.browser.shared.proxy.AppDataProxy;
import com.gmi.nordborglab.browser.shared.proxy.AppUserProxy;


public class CurrentUser{
	
	private AppUserProxy appUser = null;
	private AppDataProxy appData = null;
	
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

	public void setAppData(AppDataProxy appData) {
		this.appData = appData;
	    addNullValues();
	}
	
	
	public AppDataProxy getAppData() {
		return appData;
	}
	
	private void addNullValues() {
		if (appData == null)
			return;
		appData.getUnitOfMeasureList().add(0, null);
		appData.getStatisticTypeList().add(0, null);
		appData.getStudyProtocolList().add(0,null);
		appData.getAlleleAssayList().add(0,null);
	}
}
