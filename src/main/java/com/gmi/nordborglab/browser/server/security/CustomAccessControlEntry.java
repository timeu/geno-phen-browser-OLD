package com.gmi.nordborglab.browser.server.security;

public class CustomAccessControlEntry {

	private int mask; 
	private boolean granting; 
	
	public CustomAccessControlEntry() {}
	
	public CustomAccessControlEntry(int mask,boolean granting) {
		this.mask = mask;
		this.granting = granting;
	}

	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	public boolean isGranting() {
		return granting;
	}

	public void setGranting(boolean granting) {
		this.granting = granting;
	}
	
	
}
